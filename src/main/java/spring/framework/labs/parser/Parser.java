package spring.framework.labs.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.*;
import spring.framework.labs.domain.dtos.*;
import spring.framework.labs.mappers.*;
import spring.framework.labs.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class Parser implements CommandLineRunner {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;
    private final RatingService ratingService;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final RatingMapper ratingMapper;
    private final PublisherMapper publisherMapper;
    private final CategoryMapper categoryMapper;

    public void parseNewNews() {
        for (int page = 4; page < 5; page++) {

            final String URL_TO_PARSE = "https://book24.ru/catalog/fiction-1592/page-" + page + "/";

            try {
                Document document = Jsoup.connect(URL_TO_PARSE)
                        .userAgent("Mozilla")
                        .timeout(5000)
                        .referrer("https://google.com")
                        .get();

                log.debug("Подключение к главной странице...");

                Elements postTitleElements = document.getElementsByClass("product-list__item");

                for (Element postTitleElement : postTitleElements) {
                    Book book = new Book();

                    Rating rating = Rating.builder().value(0.0).build();
                    rating.setBook(book);
                    RatingDTO savedRating = ratingService.save(ratingMapper.ratingRatingToDTO(rating));
                    book.setRating(ratingMapper.ratingDTOToRating(savedRating));

                    String NAME = postTitleElement.getElementsByClass("product-card__name smartLink").attr("title");
                    book.setName(NAME);

                    String HREF = postTitleElement.getElementsByClass("product-card__image-link smartLink").attr("href");
                    log.debug("Переходим на страницу с книгой..." + HREF);

                    Document postDetailsDoc = Jsoup.connect("https://book24.ru" + HREF).get();

                    String DESCRIPTION = postDetailsDoc.getElementsByAttributeValue("itemprop", "description").attr("content").replaceAll("\uFEFF", " ").replaceAll("&nbsp;", " ");
                    book.setDescription(DESCRIPTION);

                    String PRICE = postDetailsDoc.getElementsByAttributeValue("itemprop", "price").attr("content");
                    book.setPrice(Long.valueOf(PRICE));

                    List<String> images = new ArrayList<>();
                    String MAIN_IMAGE = postDetailsDoc.getElementsByClass("product-poster__main-image").attr("src");
                    images.add(MAIN_IMAGE);

                    Elements imgs = postDetailsDoc.getElementsByClass("product-poster__main-slide _lazy");
                    for (Element image : imgs) {
                        images.add(image.getElementsByClass("product-poster__main-image").attr("data-src"));
                    }
                    book.setImages(images.toArray(String[]::new));

                    String ARTICLE = postDetailsDoc.getElementsByClass("product-detail-page__article").text();
                    book.setArticle(ARTICLE.split(" ")[1]);

                    Elements characteristics = postDetailsDoc.getElementsByClass("product-characteristic__item-holder");
                    for (Element characteristic : characteristics) {
                        switch (characteristic.getElementsByClass("product-characteristic__label").text()) {
                            case ("Автор:") -> setAuthors(book, characteristic);
                            case ("Раздел:") -> setCategory(book, characteristic);
                            case ("Издательство:"), ("Издательский бренд:") -> setPublishers(book, characteristic);
                            case ("ISBN:") -> setISBN(book, characteristic);
                            case ("Год издания:") -> setYearOfPublishing(book, characteristic);
                            case ("Количество страниц:") -> setNumberOfPages(book, characteristic);
                            case ("Формат:") -> setFormat(book, characteristic);
                            case ("Вес:") -> setWeight(book, characteristic);
                        }
                    }

                    BookDTO bookDTO = bookService.findByName(NAME);

                    if (bookDTO == null) {
                        bookService.save(bookMapper.bookToBookDTO(book));
                    } else {
                        bookService.update(bookMapper.bookToBookDTO(book), bookDTO.getId());
                    }

                    log.debug("Добавлена новая книга под названием \"" + NAME + "\"");
                }
            } catch (IOException | InterruptedException e) {
                log.error("Произошла ошибка при парсинге книг " + e.getMessage());
            }
        }
    }

    public void setAuthors(Book book, Element characteristic) throws IOException {
        log.debug("Переход на страницы авторов...");

        Elements authors = characteristic.getElementsByClass("product-characteristic-link smartLink");

        Set<Author> pp = new HashSet<>();

        if (authors.size() != 0) {
            for (Element author : authors) {

                String AuthorHREF = author.attr("href");
                Document postDetailsDoc = Jsoup.connect("https://book24.ru" + AuthorHREF).get();

                Elements info = postDetailsDoc.getElementsByClass("author__info");
                for (Element element : info) {

                    String IMAGE = element.getElementsByClass("author-info__img lazyload").attr("data-src");
                    String NAME = element.getElementsByClass("author-info__title").text().replaceAll("(ред.)", "").trim();
                    String DESCRIPTION = element.getElementsByClass("typography").text();

                    Author newAuthor = authorMapper.authorDTOToAuthor(authorService.findByName(NAME));

                    AuthorDTO auth;
                    if (newAuthor == null) {
                        newAuthor = Author.builder().description(DESCRIPTION).name(NAME).authorBooks(new HashSet<>()).image(IMAGE).build();

                        log.debug("Был создан новый автор с именем \"" + NAME + "\"");

                        newAuthor.getAuthorBooks().add(book);

                        auth = authorService.save(authorMapper.authorToAuthorDTO(newAuthor));
                    } else {
                        newAuthor.getAuthorBooks().add(book);

                        auth = authorService.update(authorMapper.authorToAuthorDTO(newAuthor), newAuthor.getId());
                    }
                    pp.add(authorMapper.authorDTOToAuthor(auth));
                }
            }
        } else {
            authors = characteristic.getElementsByClass("product-characteristic__value");

            for (Element author : authors) {

                String authorNames = author.text();
                String[] names = authorNames.split(",");

                for (String NAME : names) {
                    NAME = NAME.replaceAll("(\\(ред.\\))", "").trim();

                    Author newAuthor = authorMapper.authorDTOToAuthor(authorService.findByName(NAME));

                    AuthorDTO auth;
                    if (newAuthor == null) {
                        newAuthor = Author.builder().description("").name(NAME).authorBooks(new HashSet<>()).image("").build();

                        log.debug("Был создан новый автор с именем \"" + NAME + "\"");

                        newAuthor.getAuthorBooks().add(book);

                        auth = authorService.save(authorMapper.authorToAuthorDTO(newAuthor));
                    } else {
                        newAuthor.getAuthorBooks().add(book);

                        auth = authorService.update(authorMapper.authorToAuthorDTO(newAuthor), newAuthor.getId());
                    }
                    pp.add(authorMapper.authorDTOToAuthor(auth));
                }
            }
        }

        book.setAuthors(pp);
    }

    public void setCategory(Book book, Element characteristic) {
        String NAME = characteristic.getElementsByClass("product-characteristic-link smartLink").attr("title");

        Category newCategory = categoryMapper.categoryDTOToCategory(categoryService.findByName(NAME));
        CategoryDTO cat;
        if (newCategory == null) {
            newCategory = Category.builder().name(NAME).books(new HashSet<>()).build();

            log.debug("Была создана новая категория под названием \"" + NAME + "\"");

            newCategory.getBooks().add(book);

            cat = categoryService.save(categoryMapper.categoryToCategoryDTO(newCategory));
        } else {
            newCategory.getBooks().add(book);

            cat = categoryService.update(categoryMapper.categoryToCategoryDTO(newCategory), newCategory.getId());
        }

        book.setCategory(categoryMapper.categoryDTOToCategory(cat));
    }

    public void setPublishers(Book book, Element characteristic) throws InterruptedException {
        log.debug("Переход на страницы издателей...");

        Set<Publisher> pp = new HashSet<>();

        Elements publishers = characteristic.getElementsByClass("product-characteristic-link smartLink");
        for (Element publisher : publishers) {

            String NAME = publisher.getElementsByAttribute("title").text();

            Publisher newPublisher = publisherMapper.publisherDTOToPublisher(publisherService.findByName(NAME));

            PublisherDTO pub;
            if (newPublisher == null) {
                newPublisher = Publisher.builder().name(NAME).publisherBooks(new HashSet<>()).build();

                log.debug("Был создан новый издатель с названием \"" + NAME + "\"");

                newPublisher.getPublisherBooks().add(book);

                pub = publisherService.save(publisherMapper.publisherToPublisherDTO(newPublisher));
            } else {
                newPublisher.getPublisherBooks().add(book);

                pub = publisherService.update(publisherMapper.publisherToPublisherDTO(newPublisher), newPublisher.getId());
            }
            pp.add(publisherMapper.publisherDTOToPublisher(pub));
        }

        book.setPublishers(pp);
    }

    private void setISBN(Book book, Element characteristic) {
        String ISBN = characteristic.getElementsByClass("app-copy-button isbn-product _right").text();

        book.setIsbn(ISBN);
    }

    private void setYearOfPublishing(Book book, Element characteristic) {
        String yearOfPublishing = characteristic.getElementsByClass("product-characteristic__value").text();

        if (!yearOfPublishing.isEmpty()) {
            book.setYearOfPublishing(Integer.valueOf(yearOfPublishing));
        }
    }

    private void setNumberOfPages(Book book, Element characteristic) {
        String value = characteristic.getElementsByClass("product-characteristic__value").text();

        if (!value.isEmpty()) {
            Integer numberOfPages = Integer.valueOf(value);
            book.setNumberOfPages(numberOfPages);
        }
    }

    private void setFormat(Book book, Element characteristic) {
        String format = characteristic.getElementsByClass("product-characteristic__value").text();

        book.setFormat(format);
    }

    private void setWeight(Book book, Element characteristic) {
        String weight = characteristic.getElementsByClass("product-characteristic__value").text().split(" ")[0];

        if (!weight.isEmpty()) {
            book.setWeight(Double.valueOf(weight));
        }
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (bookService.findAll().size() == 0) {
            parseNewNews();
            log.debug("Успешно было добавлено " + bookService.findAll().size() + " книг");
        }
    }
}
