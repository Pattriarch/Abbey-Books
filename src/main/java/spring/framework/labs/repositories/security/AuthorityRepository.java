package spring.framework.labs.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.framework.labs.domain.security.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}