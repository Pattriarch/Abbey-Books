package spring.framework.labs.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController extends Paginated {

    private final BookService bookService;

    public SearchController(CategoryService categoryService, BookService bookService) {
        super(categoryService);
        this.bookService = bookService;
    }

    @GetMapping("/page-{pageNo}")
    public String findPaginated(@RequestParam String bookName,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField", defaultValue = "id", required = false) String sortField,
                                @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir,
                                Model model) {

        model = addAttributesToModel(pageNo, sortField, sortDir, model);

        model.addAttribute("bookName", bookName);

        Page<BookDTO> page = bookService.findAllByNameStartsWithIgnoreCase(bookName, pageNo, Paginated.PAGE_SIZE, sortField, sortDir);
        List<BookDTO> listBooks = page.getContent();
        model.addAttribute("listBooks", listBooks);

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        return "searchform";
    }
}
