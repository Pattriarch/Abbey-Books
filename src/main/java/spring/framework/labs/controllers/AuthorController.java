package spring.framework.labs.controllers;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.AuthorDTO;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.mappers.AuthorMapper;
import spring.framework.labs.services.AuthorService;
import spring.framework.labs.services.BookService;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorMapper authorMapper;
    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorMapper authorMapper, AuthorService authorService, BookService bookService) {
        this.authorMapper = authorMapper;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping({"/{authorId}/page-{pageNo}"})
    public String findPaginated(@PathVariable Long authorId,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField", required = false) String sortField,
                                @RequestParam(value = "sortDir", required = false) String sortDir,
                                Model model) {
        int pageSize = 30;

        if (sortField == null) {
            sortField = "id";
        }

        if (sortDir == null) {
            sortDir = "desc";
        }

        AuthorDTO author = authorService.findById(authorId);

        model.addAttribute("author", author);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<BookDTO> page = bookService.findAllByAuthorsContains(authorMapper.authorDTOToAuthor(author), pageable);

        List<BookDTO> listBooks = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listBooks", listBooks);
        return "authorform";
    }
}
