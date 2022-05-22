package spring.framework.labs.controllers.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.Book;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.mappers.BookMapper;
import spring.framework.labs.security.perms.bookAPI.BookAPICreatePermission;
import spring.framework.labs.security.perms.bookAPI.BookAPIDeletePermission;
import spring.framework.labs.security.perms.bookAPI.BookAPIReadPermission;
import spring.framework.labs.security.perms.bookAPI.BookAPIUpdatePermission;
import spring.framework.labs.services.BookService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookControllerAPI {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @BookAPIReadPermission
    @GetMapping
    public Set<BookDTO> getBooks() {
        return bookService.findAll();
    }

    @BookAPICreatePermission
    @PostMapping
    public BookDTO addBook(@Valid @RequestBody BookDTO bookDTO) {
        return bookService.save(bookDTO);
    }

    @BookAPIUpdatePermission
    @PutMapping("/{bookId}")
    public BookDTO putBook(@Valid @RequestBody BookDTO newBook, @PathVariable @Min(0) Long bookId) {
        return bookService.update(newBook, bookId);
    }

    @BookAPIReadPermission
    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable @Min(0) Long bookId) {
        return bookMapper.bookDTOToBook(bookService.findById(bookId));
    }

    @BookAPIDeletePermission
    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable @Min(0) Long bookId) {
        bookService.deleteById(bookId);
    }
}
