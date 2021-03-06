package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.framework.labs.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findFirstByName(String name);
}
