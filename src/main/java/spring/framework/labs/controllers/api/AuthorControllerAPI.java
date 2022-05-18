package spring.framework.labs.controllers.api;

import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.dtos.AuthorDTO;
import spring.framework.labs.services.AuthorService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequestMapping("/api/authors")
public class AuthorControllerAPI {

    private final AuthorService authorService;

    public AuthorControllerAPI(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Set<AuthorDTO> getAuthors() {
        return authorService.findAll();
    }

    @PostMapping
    public AuthorDTO addAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        return authorService.save(authorDTO);
    }

    @PutMapping("/{authorId}")
    public AuthorDTO putAuthor(@Valid @RequestBody AuthorDTO newAuthor, @PathVariable @Min(0) Long authorId) {
        return authorService.update(newAuthor, authorId);
    }

    @GetMapping("/{authorId}")
    public AuthorDTO getAuthor(@PathVariable @Min(0) Long authorId) {
        return authorService.findById(authorId);
    }

    @DeleteMapping("/{authorId}")
    public void deleteAuthor(@PathVariable @Min(0) Long authorId) {
        authorService.deleteById(authorId);
    }
}
