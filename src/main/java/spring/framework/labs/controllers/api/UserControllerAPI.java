package spring.framework.labs.controllers.api;

import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.dtos.AuthorDTO;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.services.AuthorService;
import spring.framework.labs.services.security.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserControllerAPI {
    private final UserService userService;

    public UserControllerAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Set<UserDTO> getUsers() {
        return userService.findAll();
    }

    @PostMapping
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PutMapping("/{userId}")
    public UserDTO putUser(@Valid @RequestBody UserDTO newUser, @PathVariable @Min(0) Long userId) {
        return userService.update(newUser, userId);
    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable @Min(0) Long userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Min(0) Long userId) {
        userService.deleteById(userId);
    }
}
