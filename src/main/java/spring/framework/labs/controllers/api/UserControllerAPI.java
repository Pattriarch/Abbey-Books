package spring.framework.labs.controllers.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.security.perms.userAPI.UserAPICreatePermission;
import spring.framework.labs.security.perms.userAPI.UserAPIDeletePermission;
import spring.framework.labs.security.perms.userAPI.UserAPIReadPermission;
import spring.framework.labs.security.perms.userAPI.UserAPIUpdatePermission;
import spring.framework.labs.services.security.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserControllerAPI {
    private final UserService userService;

    @UserAPIReadPermission
    @GetMapping
    public Set<UserDTO> getUsers() {
        return userService.findAll();
    }

    @UserAPICreatePermission
    @PostMapping
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @UserAPIUpdatePermission
    @PutMapping("/{userId}")
    public UserDTO putUser(@Valid @RequestBody UserDTO newUser, @PathVariable @Min(0) Long userId) {
        return userService.update(newUser, userId);
    }

    @UserAPIReadPermission
    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable @Min(0) Long userId) {
        return userService.findById(userId);
    }

    @UserAPIDeletePermission
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Min(0) Long userId) {
        userService.deleteById(userId);
    }
}
