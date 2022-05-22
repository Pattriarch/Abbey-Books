package spring.framework.labs.controllers.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.dtos.PublisherDTO;
import spring.framework.labs.security.perms.publisherAPI.PublisherAPICreatePermission;
import spring.framework.labs.security.perms.publisherAPI.PublisherAPIDeletePermission;
import spring.framework.labs.security.perms.publisherAPI.PublisherAPIReadPermission;
import spring.framework.labs.security.perms.publisherAPI.PublisherAPIUpdatePermission;
import spring.framework.labs.services.PublisherService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/publishers")
public class PublisherControllerAPI {

    private final PublisherService publisherService;

    @PublisherAPIReadPermission
    @GetMapping
    public Set<PublisherDTO> getAuthors() {
        return publisherService.findAll();
    }

    @PublisherAPICreatePermission
    @PostMapping
    public PublisherDTO addAuthor(@Valid @RequestBody PublisherDTO publisherDTO) {
        return publisherService.save(publisherDTO);
    }

    @PublisherAPIUpdatePermission
    @PutMapping("/{publisherId}")
    public PublisherDTO putAuthor(@Valid @RequestBody PublisherDTO newPublisher, @PathVariable @Min(0) Long publisherId) {
        return publisherService.update(newPublisher, publisherId);
    }

    @PublisherAPIReadPermission
    @GetMapping("/{publisherId}")
    public PublisherDTO getAuthor(@PathVariable @Min(0) Long publisherId) {
        return publisherService.findById(publisherId);
    }

    @PublisherAPIDeletePermission
    @DeleteMapping("/{publisherId}")
    public void deleteAuthor(@PathVariable @Min(0) Long publisherId) {
        publisherService.deleteById(publisherId);
    }
}
