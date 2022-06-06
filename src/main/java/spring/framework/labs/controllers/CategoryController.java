package spring.framework.labs.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.dtos.CategoryDTO;
import spring.framework.labs.mappers.CategoryMapper;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController extends Paginated {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;
    private final BookService bookService;

    public CategoryController(CategoryMapper categoryMapper, CategoryService categoryService, BookService bookService) {
        super(categoryService);
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @GetMapping({"/{categoryId}/page-{pageNo}"})
    public String findPaginated(@PathVariable Long categoryId,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField", defaultValue = "id", required = false) String sortField,
                                @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir,
                                Model model) {

        model = addAttributesToModel(pageNo, sortField, sortDir, model);

        CategoryDTO category = categoryService.findById(categoryId);
        model.addAttribute("category", category);

        Page<BookDTO> page = bookService.findAllByCategory(categoryMapper.categoryDTOToCategory(category), pageNo, Paginated.PAGE_SIZE, sortField, sortDir);
        List<BookDTO> listBooks = page.getContent();
        model.addAttribute("listBooks", listBooks);

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        return "categoryform";
    }
}
