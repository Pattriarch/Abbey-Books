package spring.framework.labs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.RateToken;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.domain.security.User;
import spring.framework.labs.mappers.BookMapper;
import spring.framework.labs.mappers.RateTokenMapper;
import spring.framework.labs.security.perms.book.BookDeletePermission;
import spring.framework.labs.security.perms.book.BookReadPermission;
import spring.framework.labs.security.perms.book.BookUpdatePermission;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;
import spring.framework.labs.services.RateTokenService;
import spring.framework.labs.services.security.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final RateTokenMapper rateTokenMapper;
    private final BookMapper bookMapper;
    private final RateTokenService rateTokenService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final BookService bookService;

    @GetMapping("/{bookId}")
    public String showBook(@PathVariable Long bookId, Model model) {

        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != "anonymousUser") {
            model.addAttribute("user", principal);
        }
        model.addAttribute("book", bookService.findById(bookId));

        model.addAttribute("categories", categoryService.findAllLimitedFive());

        return "bookform";
    }

    @BookReadPermission
    @GetMapping("/myBooks")
    public String showUserBooks(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);

        model.addAttribute("categories", categoryService.findAllLimitedFive());

        model.addAttribute("listBooks", user.getBooks());

        return "userbooksform";
    }

    @BookDeletePermission
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Long bookId, Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);

        bookService.deleteById(bookId);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != "anonymousUser") {
            model.addAttribute("user", principal);
            ((User)principal).setBooks(userService.findById(Long.valueOf(((User)principal).getId())).getBooks());
        }

        model.addAttribute("lastBooks", bookService.findFiveLastBooks());
        model.addAttribute("bestBooks", bookService.findAllByOrderByRatingValueDescLimitFive());


        model.addAttribute("categories", categoryService.findAllLimitedFive());

        return "index";
    }

    @BookUpdatePermission
    @GetMapping("/update/{bookId}")
    public String updateBook(@PathVariable Long bookId, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);

        model.addAttribute("book", bookService.findById(bookId));
        BookDTO bookDTO = new BookDTO();
        model.addAttribute("bookDTO", bookDTO);

        model.addAttribute("lastBooks", bookService.findFiveLastBooks());
        model.addAttribute("bestBooks", bookService.findAllByOrderByRatingValueDescLimitFive());

        model.addAttribute("categories", categoryService.findAllLimitedFive());

        return "bookupdateform";
    }

    @BookUpdatePermission
    @PostMapping("/update/{bookId}")
    public String updateBookForm(final @Valid BookDTO bookDTO, final BindingResult bindingResult, @PathVariable Long bookId, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("bookDTO", bookDTO);

            return "bookupdateform";
        } else {
            bookService.update(bookDTO, bookId);

            return "redirect:/books/" + bookId;
        }
    }

    @BookReadPermission
    @PostMapping("/{bookId}/rate")
    public String sendRate(@RequestParam Long rate, @PathVariable Long bookId) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Book book = bookMapper.bookDTOToBook(bookService.findById(bookId));

        System.out.println(rate);
        System.out.println(bookId);
        RateToken rateToken = RateToken.builder().rate(Math.toIntExact(rate)).rating(book.getRating()).user(user).build();
        rateTokenService.save(rateTokenMapper.rateTokenToRateTokenDTO(rateToken), rate, user.getId(), bookMapper.bookToBookDTO(book));

        return "redirect:/books/" + bookId;
    }
}
