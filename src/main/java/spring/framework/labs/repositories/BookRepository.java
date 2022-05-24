package spring.framework.labs.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Author;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.Publisher;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Book findFirstByName(String article);

    Page<Book> findAllByAuthorsContains(Author author, Pageable pageable);

    Page<Book> findAllByPublishersContains(Publisher publisher, Pageable pageable);

    Page<Book> findAllByCategory(Category category, Pageable pageable);

    Page<Book> findAllByNameStartsWithIgnoreCase(String name, Pageable pageable);

    List<Book> findAllByOrderByRatingValueDesc();

    List<Book> findAllByCategory(Category category);

    @Modifying
    @Query("DELETE FROM Book uru WHERE uru.id in ?1")
    @Transactional
    void delete(Long id);

    Book findByNameAndArticle(String name, String article);
}
