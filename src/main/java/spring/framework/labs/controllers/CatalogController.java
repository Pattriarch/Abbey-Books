package spring.framework.labs.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.Book;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;

import java.util.List;

@Controller
@RequestMapping({"/catalog", "/catalog.html"})
public class CatalogController extends Paginated {

    private final BookService bookService;
    private final CategoryService categoryService;

    public CatalogController(CategoryService categoryService, BookService bookService, CategoryService categoryService1) {
        super(categoryService);
        this.bookService = bookService;
        this.categoryService = categoryService1;
    }

    @GetMapping({"/{pageNo}"})
public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                            @RequestParam(value = "sortField", defaultValue = "id", required = false) String sortField,
                            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir,
                            Model model) {

        model = addAttributesToModel(pageNo, sortField, sortDir, model);

        model.addAttribute("allCategories", categoryService.findAll());

        Page<Book> page = bookService.findPaginated(pageNo, PAGE_SIZE, sortField, sortDir);
        List<Book> listBooks = page.getContent();
        model.addAttribute("listBooks", listBooks);

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        return "catalog";
    }
}
