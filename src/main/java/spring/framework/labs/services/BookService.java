package spring.framework.labs.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.framework.labs.domain.Author;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.Publisher;
import spring.framework.labs.domain.dtos.BookDTO;

public interface BookService extends CrudService<BookDTO, Long> {
    BookDTO findByName(String name);

    Page<BookDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<BookDTO> findAllByAuthorsContains(Author author, Pageable pageable);

    Page<BookDTO> findAllByPublishersContains(Publisher publisher, Pageable pageable);

    Page<BookDTO> findAllByCategory(Category category, Pageable pageable);

    Page<BookDTO> findAllByNameStartsWithIgnoreCase(String name, Pageable pageable);
}
