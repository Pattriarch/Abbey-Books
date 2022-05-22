package spring.framework.labs.services.security;

import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.exceptions.NotEnoughMoneyException;
import spring.framework.labs.services.CrudService;

public interface UserService extends CrudService<UserDTO, Long> {
    UserDTO findByUsername(String name);

    UserDTO payBooks(UserDTO userDTO) throws NotEnoughMoneyException;

    void addToCart(UserDTO userDTO, Long aLong);

    void changePassword(String currentPassword, String newPassword, Long userId);

    UserDTO deleteBookFromCart(UserDTO userDTO, Long bookId);

    UserDTO deleteAllBooksFromCart(UserDTO userDTO);

    UserDTO addBalance(Long userId, Long value);
}
