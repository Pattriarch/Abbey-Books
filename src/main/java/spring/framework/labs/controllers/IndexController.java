package spring.framework.labs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BookService bookService;
    private final CategoryService categoryService;

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != "anonymousUser") {
            model.addAttribute("user", principal);
        }

        model.addAttribute("lastBooks", bookService.findFiveLastBooks());
        model.addAttribute("bestBooks", bookService.findAllByOrderByRatingValueDescLimitFive());

        model.addAttribute("categories", categoryService.findAll());

        return "index";
    }
}