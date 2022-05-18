package spring.framework.labs.controllers.api;

import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.services.BookService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
// Убрать ResponseBody
@RequestMapping("/api/books")
public class BookControllerAPI {

    private final BookService bookService;

    public BookControllerAPI(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Set<BookDTO> getBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO addBook(@Valid @RequestBody BookDTO bookDTO) {
        return bookService.save(bookDTO);
    }

    @PutMapping("/{bookId}")
    public BookDTO putBook(@Valid @RequestBody BookDTO newBook, @PathVariable @Min(0) Long bookId) {
        return bookService.update(newBook, bookId);
    }

    @GetMapping("/{bookId}")
    public BookDTO getBook(@PathVariable @Min(0) Long bookId) {
        return bookService.findById(bookId);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable @Min(0) Long bookId) {
        bookService.deleteById(bookId);
    }
}
