package spring.framework.labs.controllers.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.dtos.AuthorDTO;
import spring.framework.labs.security.perms.authorAPI.AuthorAPICreatePermission;
import spring.framework.labs.security.perms.authorAPI.AuthorAPIDeletePermission;
import spring.framework.labs.security.perms.authorAPI.AuthorAPIReadPermission;
import spring.framework.labs.security.perms.authorAPI.AuthorAPIUpdatePermission;
import spring.framework.labs.services.AuthorService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorControllerAPI {

    private final AuthorService authorService;

    @AuthorAPIReadPermission
    @GetMapping
    public Set<AuthorDTO> getAuthors() {
        return authorService.findAll();
    }

    @AuthorAPICreatePermission
    @PostMapping
    public AuthorDTO addAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        return authorService.save(authorDTO);
    }

    @AuthorAPIUpdatePermission
    @PutMapping("/{authorId}")
    public AuthorDTO putAuthor(@Valid @RequestBody AuthorDTO newAuthor, @PathVariable @Min(0) Long authorId) {
        return authorService.update(newAuthor, authorId);
    }

    @AuthorAPIReadPermission
    @GetMapping("/{authorId}")
    public AuthorDTO getAuthor(@PathVariable @Min(0) Long authorId) {
        return authorService.findById(authorId);
    }

    @AuthorAPIDeletePermission
    @DeleteMapping("/{authorId}")
    public void deleteAuthor(@PathVariable @Min(0) Long authorId) {
        authorService.deleteById(authorId);
    }
}
