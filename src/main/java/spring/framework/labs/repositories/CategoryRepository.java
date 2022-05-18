package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.Publisher;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findFirstByName(String name);
}
