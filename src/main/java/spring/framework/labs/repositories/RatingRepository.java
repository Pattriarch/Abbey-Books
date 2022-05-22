package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.framework.labs.domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
