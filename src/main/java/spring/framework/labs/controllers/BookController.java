package spring.framework.labs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.framework.labs.services.BookService;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/catalog", "/catalog.html"})
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "catalog";
    }

    @GetMapping({"/books/{bookId}"})
    public String viewBook(@PathVariable String bookId, Model model) {
        model.addAttribute("book", bookService.findById(Long.valueOf(bookId)));
        return "bookform";
    }
}
