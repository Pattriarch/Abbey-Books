package spring.framework.labs.services.security;

import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.services.CrudService;

public interface UserService extends CrudService<UserDTO, Long> {
    UserDTO findByUsername(String name);

    UserDTO payBooks(UserDTO userDTO);

    void addToCart(UserDTO userDTO, Long aLong);

    void changePassword(String currentPassword, String newPassword, Long userId);
}
