package spring.framework.labs.controllers.api;

import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.dtos.AuthorDTO;
import spring.framework.labs.domain.dtos.PublisherDTO;
import spring.framework.labs.services.AuthorService;
import spring.framework.labs.services.PublisherService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequestMapping("/api/publishers")
public class PublisherControllerAPI {

    private final PublisherService publisherService;

    public PublisherControllerAPI(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public Set<PublisherDTO> getAuthors() {
        return publisherService.findAll();
    }

    @PostMapping
    public PublisherDTO addAuthor(@Valid @RequestBody PublisherDTO publisherDTO) {
        return publisherService.save(publisherDTO);
    }

    @PutMapping("/{publisherId}")
    public PublisherDTO putAuthor(@Valid @RequestBody PublisherDTO newPublisher, @PathVariable @Min(0) Long publisherId) {
        return publisherService.update(newPublisher, publisherId);
    }

    @GetMapping("/{publisherId}")
    public PublisherDTO getAuthor(@PathVariable @Min(0) Long publisherId) {
        return publisherService.findById(publisherId);
    }

    @DeleteMapping("/{publisherId}")
    public void deleteAuthor(@PathVariable @Min(0) Long publisherId) {
        publisherService.deleteById(publisherId);
    }
}
