package spring.framework.labs.controllers;

import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.AuthorDTO;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.mappers.AuthorMapper;
import spring.framework.labs.services.AuthorService;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController extends Paginated {

    private final AuthorMapper authorMapper;
    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(CategoryService categoryService, AuthorMapper authorMapper, AuthorService authorService, BookService bookService) {
        super(categoryService);
        this.authorMapper = authorMapper;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping({"/{authorId}/page-{pageNo}"})
    public String findPaginated(@PathVariable Long authorId,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField", defaultValue = "id", required = false) String sortField,
                                @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir,
                                Model model) {

        model = addAttributesToModel(pageNo, sortField, sortDir, model);

        AuthorDTO author = authorService.findById(authorId);
        model.addAttribute("author", author);

        Page<BookDTO> page = bookService.findAllByAuthorsContains(authorMapper.authorDTOToAuthor(author), pageNo, Paginated.PAGE_SIZE, sortField, sortDir);
        List<BookDTO> listBooks = page.getContent();
        model.addAttribute("listBooks", listBooks);

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        return "authorform";
    }
}
