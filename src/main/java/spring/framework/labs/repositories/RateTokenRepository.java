package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.framework.labs.domain.RateToken;

@Repository
public interface RateTokenRepository extends JpaRepository<RateToken, Long> {
}
