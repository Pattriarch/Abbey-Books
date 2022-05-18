package spring.framework.labs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import spring.framework.labs.exceptions.UserAlreadyExistException;
import spring.framework.labs.mappers.BookMapper;
import spring.framework.labs.mappers.RateTokenMapper;
import spring.framework.labs.security.perms.BookReadPermission;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.RateTokenService;
import spring.framework.labs.services.security.UserService;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final RateTokenMapper rateTokenMapper;
    private final BookMapper bookMapper;
    private final RateTokenService rateTokenService;
    private final BookService bookService;

//    @BookReadPermission
    @GetMapping("/{bookId}")
    public String showBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("book", bookService.findById(bookId));
        return "bookform";
    }

    @GetMapping("/myBooks")
    public String showUserBooks(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);
        model.addAttribute("listBooks", user.getBooks());

        return "userbooksform";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.deleteById(bookId);
        return "redirect:/info";
    }

    @GetMapping("/update/{bookId}")
    public String updateBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("book", bookService.findById(bookId));
        BookDTO bookDTO = new BookDTO();
        model.addAttribute("bookDTO", bookDTO);

        return "bookupdateform";
    }

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
