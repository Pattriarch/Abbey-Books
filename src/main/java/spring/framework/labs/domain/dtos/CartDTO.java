package spring.framework.labs.domain.dtos;

import lombok.Data;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.security.User;

import java.util.Set;

@Data
public class CartDTO {
    private Long id;
    private User user;
    private Set<Book> book_carts;
}
