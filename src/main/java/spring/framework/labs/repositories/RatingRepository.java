package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.framework.labs.domain.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
