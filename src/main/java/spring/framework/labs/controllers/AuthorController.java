package spring.framework.labs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.Category;
import spring.framework.labs.domain.dtos.AuthorDTO;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.mappers.AuthorMapper;
import spring.framework.labs.services.AuthorService;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorMapper authorMapper;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    public static final int PAGE_SIZE = 30;

    @GetMapping({"/{authorId}/page-{pageNo}"})
    public String findPaginated(@PathVariable Long authorId,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField", required = false) String sortField,
                                @RequestParam(value = "sortDir", required = false) String sortDir,
                                Model model) {

        if (sortField == null) {
            sortField = "id";
        }

        if (sortDir == null) {
            sortDir = "desc";
        }

        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != "anonymousUser") {
            model.addAttribute("user", principal);
        }

        model.addAttribute("categories", categoryService.findAll().stream().limit(5).toList());

        AuthorDTO author = authorService.findById(authorId);
        model.addAttribute("author", author);

        Page<BookDTO> page = bookService.findAllByAuthorsContains(authorMapper.authorDTOToAuthor(author), pageNo, PAGE_SIZE, sortField, sortDir);
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
