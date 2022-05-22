package spring.framework.labs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Author;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.Publisher;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.exceptions.ResourceNotFoundException;
import spring.framework.labs.mappers.BookMapper;
import spring.framework.labs.repositories.BookRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    public Set<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public BookDTO findById(Long aLong) {
        return bookRepository.findById(aLong)
                .map(bookMapper::bookToBookDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        return saveAndReturnDTO(bookMapper.bookDTOToBook(bookDTO));
    }

    BookDTO saveAndReturnDTO(Book book) {
        Book savedBook = bookRepository.save(book);

        return bookMapper.bookToBookDTO(savedBook);
    }

    private Pageable getPageable(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        return PageRequest.of(pageNo - 1, pageSize, sort);
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {

        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);

        return this.bookRepository
                .findAll(pageable);
    }

    @Override
    public Page<BookDTO> findAllByAuthorsContains(Author author, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);

        return bookRepository.findAllByAuthorsContains(author, pageable).map(bookMapper::bookToBookDTO);
    }

    @Override
    public Page<BookDTO> findAllByPublishersContains(Publisher publisher, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);

        return bookRepository.findAllByPublishersContains(publisher, pageable).map(bookMapper::bookToBookDTO);
    }

    @Override
    public Page<BookDTO> findAllByCategory(Category category, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);

        return bookRepository.findAllByCategory(category, pageable).map(bookMapper::bookToBookDTO);
    }

    @Override
    public Page<BookDTO> findAllByNameStartsWithIgnoreCase(String name, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);

        return bookRepository.findAllByNameStartsWithIgnoreCase(name, pageable).map(bookMapper::bookToBookDTO);
    }

    @Override
    public List<Book> findAllByOrderByRatingValueDescLimitFive() {
        return bookRepository.findAllByOrderByRatingValueDesc().stream().limit(5).toList();
    }

    @Override
    public List<Book> findFiveLastBooks() {
        return bookRepository.findAll().stream().limit(5).toList();
    }

    @Override
    public BookDTO update(BookDTO bookDTO, Long aLong) {
        return bookRepository.findById(aLong).map(book -> {

            if (bookDTO.getDescription() != null) {
                book.setDescription(bookDTO.getDescription());
            }

            if (bookDTO.getArticle() != null) {
                book.setArticle(bookDTO.getArticle());
            }

            if (bookDTO.getAuthors() != null) {
                book.setAuthors(bookDTO.getAuthors());
            }

            if (bookDTO.getRating() != null) {
                book.setRating(book.getRating());
            }

            if (bookDTO.getUsers() != null) {
                book.setUsers(book.getUsers());
            }

            if (bookDTO.getCarts() != null) {
                book.setCarts(book.getCarts());
            }

            if (bookDTO.getCategory() != null) {
                book.setCategory(bookDTO.getCategory());
            }

            if (bookDTO.getFormat() != null) {
                book.setFormat(bookDTO.getFormat());
            }

            if (bookDTO.getImages() != null) {
                book.setImages(bookDTO.getImages());
            }

            if (bookDTO.getIsbn() != null) {
                book.setIsbn(bookDTO.getIsbn());
            }

            if (bookDTO.getName() != null) {
                book.setName(bookDTO.getName());
            }

            if (bookDTO.getNumberOfPages() != null) {
                book.setNumberOfPages(bookDTO.getNumberOfPages());
            }

            if (bookDTO.getPrice() != null) {
                book.setPrice(bookDTO.getPrice());
            }

            if (bookDTO.getPublishers() != null) {
                book.setPublishers(bookDTO.getPublishers());
            }

            if (bookDTO.getRating() != null) {
                book.setRating(bookDTO.getRating());
            }

            if (bookDTO.getWeight() != null) {
                book.setWeight(bookDTO.getWeight());
            }

            if (bookDTO.getYearOfPublishing() != null) {
                book.setYearOfPublishing(bookDTO.getYearOfPublishing());
            }

            return bookMapper.bookToBookDTO(bookRepository.save(book));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void delete(BookDTO bookDTO) {
        bookRepository.delete(bookMapper.bookDTOToBook(bookDTO));
    }

    @Override
    public void deleteById(Long aLong) {
        bookRepository.deleteById(aLong);
    }

    @Override
    public BookDTO findByName(String name) {
        return bookMapper.bookToBookDTO(bookRepository.findFirstByName(name));
    }
}
