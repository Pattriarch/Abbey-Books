package spring.framework.labs.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import spring.framework.labs.domain.Author;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.Publisher;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Book findFirstByName(String article);

    Page<Book> findAllByAuthorsContains(Author author, Pageable pageable);

    Page<Book> findAllByPublishersContains(Publisher publisher, Pageable pageable);

    Page<Book> findAllByCategory(Category category, Pageable pageable);

    Page<Book> findAllByNameStartsWithIgnoreCase(String name, Pageable pageable);

    List<Book> findAllByOrderByRatingValueDesc();
}
