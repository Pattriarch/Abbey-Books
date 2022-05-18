package spring.framework.labs.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.dtos.CategoryDTO;
import spring.framework.labs.domain.dtos.PublisherDTO;
import spring.framework.labs.mappers.CategoryMapper;
import spring.framework.labs.mappers.PublisherMapper;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;
import spring.framework.labs.services.PublisherService;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;
    private final BookService bookService;

    public CategoryController(CategoryMapper categoryMapper, CategoryService categoryService, BookService bookService) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @GetMapping({"/{categoryId}/page-{pageNo}"})
    public String findPaginated(@PathVariable Long categoryId,
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

        CategoryDTO category = categoryService.findById(categoryId);

        model.addAttribute("category", category);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<BookDTO> page = bookService.findAllByCategory(categoryMapper.categoryDTOToCategory(category), pageable);

        List<BookDTO> listBooks = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listBooks", listBooks);
        return "categoryform";
    }
}
