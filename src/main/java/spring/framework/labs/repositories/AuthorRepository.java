package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.labs.domain.Author;

@Transactional
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findFirstByName(String name);
}
