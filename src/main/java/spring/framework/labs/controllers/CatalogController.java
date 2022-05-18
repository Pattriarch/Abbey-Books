package spring.framework.labs.controllers;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.security.User;
import spring.framework.labs.security.perms.CatalogReadPermission;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping({"/catalog", "/catalog.html"})
public class CatalogController {

    private final BookService bookService;
    private final CategoryService categoryService;

    public CatalogController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping({"/{pageNo}"})
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
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

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        if (principal != "anonymousUser") {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        Page<BookDTO> page = bookService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<BookDTO> listBooks = page.getContent();

        model.addAttribute("user", user);
        model.addAttribute("categories", categoryService.findAll().stream().limit(5).toList());

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listBooks", listBooks);
        return "catalog";
    }

}
