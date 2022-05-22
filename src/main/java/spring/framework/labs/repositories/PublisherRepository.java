package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.framework.labs.domain.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher findFirstByName(String name);
}
