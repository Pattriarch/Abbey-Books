package spring.framework.labs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.BookDTO;
import spring.framework.labs.domain.dtos.PublisherDTO;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.mappers.PublisherMapper;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;
import spring.framework.labs.services.PublisherService;

import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublisherController extends Paginated {

    private final PublisherMapper publisherMapper;
    private final PublisherService publisherService;
    private final BookService bookService;

    public PublisherController(CategoryService categoryService, PublisherMapper publisherMapper, PublisherService publisherService, BookService bookService) {
        super(categoryService);
        this.publisherMapper = publisherMapper;
        this.publisherService = publisherService;
        this.bookService = bookService;
    }

    @GetMapping({"/{publisherId}/page-{pageNo}"})
    public String findPaginated(@PathVariable Long publisherId,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField", defaultValue = "id", required = false) String sortField,
                                @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir,
                                Model model) {

        model = addAttributesToModel(pageNo, sortField, sortDir, model);

        PublisherDTO publisher = publisherService.findById(publisherId);
        model.addAttribute("publisher", publisher);

        Page<BookDTO> page = bookService.findAllByPublishersContains(publisherMapper.publisherDTOToPublisher(publisher), pageNo, Paginated.PAGE_SIZE, sortField, sortDir);
        List<BookDTO> listBooks = page.getContent();
        model.addAttribute("listBooks", listBooks);

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        return "publisherform";
    }
}
