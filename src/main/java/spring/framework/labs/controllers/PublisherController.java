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
import spring.framework.labs.domain.dtos.PublisherDTO;
import spring.framework.labs.mappers.AuthorMapper;
import spring.framework.labs.mappers.PublisherMapper;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.PublisherService;

import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherMapper publisherMapper;
    private final PublisherService publisherService;
    private final BookService bookService;

    public PublisherController(PublisherMapper publisherMapper, PublisherService publisherService, BookService bookService) {
        this.publisherMapper = publisherMapper;
        this.publisherService = publisherService;
        this.bookService = bookService;
    }

    @GetMapping({"/{publisherId}/page-{pageNo}"})
    public String findPaginated(@PathVariable Long publisherId,
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

        PublisherDTO publisher = publisherService.findById(publisherId);

        model.addAttribute("publisher", publisher);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<BookDTO> page = bookService.findAllByPublishersContains(publisherMapper.publisherDTOToPublisher(publisher), pageable);

        List<BookDTO> listBooks = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listBooks", listBooks);
        return "publisherform";
    }
}
