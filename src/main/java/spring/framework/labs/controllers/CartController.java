package spring.framework.labs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.domain.security.User;
import spring.framework.labs.exceptions.NotEnoughMoneyException;
import spring.framework.labs.mappers.UserMapper;
import spring.framework.labs.security.perms.cart.CartReadPermission;
import spring.framework.labs.security.perms.cart.CartUpdatePermission;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.security.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class CartController {

    private final UserMapper userMapper;
    private final UserService userService;

    @CartReadPermission
    @GetMapping("/cart")
    public String loadUserCart(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);
        model.addAttribute("listBooks", user.getCart().getBook_carts());

        return "cartform";
    }

    @CartUpdatePermission
    @PostMapping("/addToCart/{bookId}")
    public String addToCart(@PathVariable Long bookId, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.addToCart(userMapper.userToUserDTO(user), bookId);

        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);

        model.addAttribute("user", user);

        return "catalog";
    }

    @CartUpdatePermission
    @PostMapping("/cart/pay")
    public String payBooks(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);

        try {
            UserDTO userDTO = userService.payBooks(userMapper.userToUserDTO(user));

            user.setBalance(userDTO.getBalance());
            user.setBooks(userDTO.getBooks());

            return "redirect:/books/myBooks";
        } catch (NotEnoughMoneyException e) {
            model.addAttribute("error", e);
            return "redirect:/cart";
        }
    }

    @PostMapping("/cart/delete/{bookId}")
    public String deleteBookFromCart(Model model, @PathVariable Long bookId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);

        UserDTO userDTO = userService.deleteBookFromCart(userMapper.userToUserDTO(user), bookId);

        user.setCart(userDTO.getCart());

        return "redirect:/cart";
    }

    @PostMapping("/cart/deleteAll")
    public String deleteAllBooksFromCart(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);

        UserDTO userDTO = userService.deleteAllBooksFromCart(userMapper.userToUserDTO(user));

        user.setCart(userDTO.getCart());

        return "redirect:/cart";
    }
}
