package spring.framework.labs.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.Cart;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.domain.security.User;
import spring.framework.labs.mappers.UserMapper;
import spring.framework.labs.services.security.UserService;

@Controller
@RequestMapping
public class CartController {

    private final UserMapper userMapper;
    private final UserService userService;

    public CartController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String loadUserCart(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);
        model.addAttribute("listBooks", user.getCart().getBook_carts());

        return "cartform";
    }

    @PostMapping("/addToCart/{bookId}")
    public String addToCart(@PathVariable Long bookId, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.addToCart(userMapper.userToUserDTO(user), bookId);

        model.addAttribute("user", user);

        return "catalog";
    }

    @PostMapping("/cart/pay")
    public String payBooks(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userDTO = userService.payBooks(userMapper.userToUserDTO(user));

        user.setBalance(userDTO.getBalance());
        user.setBooks(userDTO.getBooks());

        model.addAttribute("user", user);

        return "cartform";
    }
}
