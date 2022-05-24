package spring.framework.labs.services;

import org.springframework.data.domain.Page;
import spring.framework.labs.domain.Author;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.Publisher;
import spring.framework.labs.domain.dtos.BookDTO;

import java.util.List;

public interface BookService extends CrudService<BookDTO, Long> {
    BookDTO findByName(String name);

    Page<Book> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<BookDTO> findAllByAuthorsContains(Author author, int pageNo, int pageSize, String sortField, String sortDirection);

    Page<BookDTO> findAllByPublishersContains(Publisher publisher, int pageNo, int pageSize, String sortField, String sortDirection);

    Page<BookDTO> findAllByCategory(Category category, int pageNo, int pageSize, String sortField, String sortDirection);

    Page<BookDTO> findAllByNameStartsWithIgnoreCase(String name, int pageNo, int pageSize, String sortField, String sortDirection);

    List<Book> findAllByOrderByRatingValueDescLimitFive();

    List<Book> findFiveLastBooks();

    Book findByNameAndArticle(String name, String article);

    List<Book> findAllByCategoryLimitedFive(Category category, Book book);
}
