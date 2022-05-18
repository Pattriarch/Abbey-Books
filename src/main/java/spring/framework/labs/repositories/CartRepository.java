package spring.framework.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.framework.labs.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
