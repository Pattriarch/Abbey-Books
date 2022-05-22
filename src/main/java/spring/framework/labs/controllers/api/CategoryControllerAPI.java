package spring.framework.labs.controllers.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.dtos.CategoryDTO;
import spring.framework.labs.security.perms.categoryAPI.CategoryAPICreatePermission;
import spring.framework.labs.security.perms.categoryAPI.CategoryAPIDeletePermission;
import spring.framework.labs.security.perms.categoryAPI.CategoryAPIReadPermission;
import spring.framework.labs.security.perms.categoryAPI.CategoryAPIUpdatePermission;
import spring.framework.labs.services.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryControllerAPI {

    private final CategoryService categoryService;

    @CategoryAPIReadPermission
    @GetMapping
    public Set<CategoryDTO> getAuthors() {
        return categoryService.findAll();
    }

    @CategoryAPICreatePermission
    @PostMapping
    public CategoryDTO addAuthor(@Valid @RequestBody CategoryDTO publisherDTO) {
        return categoryService.save(publisherDTO);
    }

    @CategoryAPIUpdatePermission
    @PutMapping("/{publisherId}")
    public CategoryDTO putAuthor(@Valid @RequestBody CategoryDTO newPublisher, @PathVariable @Min(0) Long publisherId) {
        return categoryService.update(newPublisher, publisherId);
    }

    @CategoryAPIReadPermission
    @GetMapping("/{publisherId}")
    public CategoryDTO getAuthor(@PathVariable @Min(0) Long publisherId) {
        return categoryService.findById(publisherId);
    }

    @CategoryAPIDeletePermission
    @DeleteMapping("/{publisherId}")
    public void deleteAuthor(@PathVariable @Min(0) Long publisherId) {
        categoryService.deleteById(publisherId);
    }
}