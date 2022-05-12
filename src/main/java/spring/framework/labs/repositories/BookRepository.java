package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Book;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findFirstByName(String article);
}
