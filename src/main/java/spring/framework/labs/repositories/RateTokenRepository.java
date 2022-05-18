package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.framework.labs.domain.RateToken;
import spring.framework.labs.domain.dtos.RateTokenDTO;

public interface RateTokenRepository extends JpaRepository<RateToken, Long> {
}
