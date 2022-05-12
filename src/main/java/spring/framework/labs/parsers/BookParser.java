package spring.framework.labs.parsers;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Author;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.Publisher;
import spring.framework.labs.domain.dtos.AuthorDTO;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.dtos.PublisherDTO;
import spring.framework.labs.mappers.AuthorMapper;
import spring.framework.labs.mappers.BookMapper;
import spring.framework.labs.mappers.PublisherMapper;
import spring.framework.labs.services.AuthorService;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.PublisherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class BookParser implements CommandLineRunner {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final PublisherMapper publisherMapper;

    public BookParser(BookService bookService, AuthorService authorService,
                      PublisherService publisherService, BookMapper bookMapper,
                      AuthorMapper authorMapper, PublisherMapper publisherMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
        this.publisherMapper = publisherMapper;
    }

    @Transactional
    public void cycle(Element postTitleElement) throws IOException, InterruptedException {
        Book book = new Book();

        String SMALL_IMAGE = postTitleElement.getElementsByClass("product-card__image lazyload").attr("data-src");
        String NAME = postTitleElement.getElementsByClass("product-card__name smartLink").attr("title");
        book.setName(NAME);
        String HREF = postTitleElement.getElementsByClass("product-card__image-link smartLink").attr("href");

        log.debug("Переходим на страницу с книгой..." + HREF);
        Document postDetailsDoc = Jsoup.connect("https://book24.ru" + HREF).get();
        String DESCRIPTION = postDetailsDoc.getElementsByAttributeValue("itemprop", "description").attr("content");
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
            log.error("Добавлена новая книга под названием: " + NAME);
        } else {
            bookService.update(bookMapper.bookToBookDTO(book), bookDTO.getId());
        }
    }


//    @Scheduled(fixedDelay = 10000000)
    public void parseNewNews() {
        for (int page = 1; page < 2; page++) {
            String url = "https://book24.ru/catalog/informatsionnye-tekhnologii-1357/page-" + page + "/";
            try {
                Document document = Jsoup.connect(url)
                        .userAgent("Mozilla")
                        .timeout(5000)
                        .referrer("https://google.com")
                        .get();
                log.debug("Подключение к главной странице...");
                Elements postTitleElements = document.getElementsByClass("product-list__item");

                for (Element postTitleElement : postTitleElements) {
                    cycle(postTitleElement);
                }
            } catch (IOException | InterruptedException e) {
                log.error("Произошла ошибка при парсинге книг " + e.getMessage());
            }
        }
    }

    public void setAuthors(Book book, Element characteristic) throws IOException {
        log.debug("Переход на страницы авторов...");
        Elements authors = characteristic.getElementsByClass("product-characteristic-link smartLink");
        Hibernate.initialize(book.getAuthors());
        book.setAuthors(null);
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
                    AuthorDTO auth = new AuthorDTO();
                    if (newAuthor == null) {
                        newAuthor = Author.builder().description(DESCRIPTION).name(NAME).authorBooks(new HashSet<>()).image(IMAGE).build();
                        log.error("Был создан новый автор с именем " + NAME);
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
                    AuthorDTO auth = new AuthorDTO();
                    if (newAuthor == null) {
                        newAuthor = Author.builder().description("").name(NAME).authorBooks(new HashSet<>()).image("").build();
                        log.error("Был создан новый автор с именем " + NAME);
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

    private void setCategory(Book book, Element characteristic) {
        String category = characteristic.getElementsByClass("product-characteristic-link smartLink").attr("title");
        book.setCategory(category);
    }

    public void setPublishers(Book book, Element characteristic) throws InterruptedException {
        log.debug("Переход на страницы издателей...");
        book.setPublishers(null);
        Set<Publisher> pp = new HashSet<>();
        Elements publishers = characteristic.getElementsByClass("product-characteristic-link smartLink");
        for (Element publisher : publishers) {
            String NAME = publisher.getElementsByAttribute("title").text();
            Publisher newPublisher = publisherMapper.publisherDTOToPublisher(publisherService.findByName(NAME));
            PublisherDTO pub = new PublisherDTO();
            if (newPublisher == null) {
                newPublisher = Publisher.builder().name(NAME).publisherBooks(new HashSet<>()).build();
                log.error("Был создан новый издатель с названием " + NAME);
                newPublisher.getPublisherBooks().add(book);
                pub = publisherService.save(publisherMapper.publisherToPublisherDTO(newPublisher));
            } else {
                newPublisher.getPublisherBooks().add(book);
                publisherService.save(publisherMapper.publisherToPublisherDTO(newPublisher));
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

//    @Transactional
    @Override
    public void run(String... args) throws Exception {
        parseNewNews();
        System.out.println(123);
    }
}
