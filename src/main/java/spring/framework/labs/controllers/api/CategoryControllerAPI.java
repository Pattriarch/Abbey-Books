package spring.framework.labs.controllers.api;

import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.dtos.CategoryDTO;
import spring.framework.labs.domain.dtos.PublisherDTO;
import spring.framework.labs.services.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
public class CategoryControllerAPI {

    private final CategoryService categoryService;

    public CategoryControllerAPI(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Set<CategoryDTO> getAuthors() {
        return categoryService.findAll();
    }

    @PostMapping
    public CategoryDTO addAuthor(@Valid @RequestBody CategoryDTO publisherDTO) {
        return categoryService.save(publisherDTO);
    }

    @PutMapping("/{publisherId}")
    public CategoryDTO putAuthor(@Valid @RequestBody CategoryDTO newPublisher, @PathVariable @Min(0) Long publisherId) {
        return categoryService.update(newPublisher, publisherId);
    }

    @GetMapping("/{publisherId}")
    public CategoryDTO getAuthor(@PathVariable @Min(0) Long publisherId) {
        return categoryService.findById(publisherId);
    }

    @DeleteMapping("/{publisherId}")
    public void deleteAuthor(@PathVariable @Min(0) Long publisherId) {
        categoryService.deleteById(publisherId);
    }
}