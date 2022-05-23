package spring.framework.labs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.services.CategoryService;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequiredArgsConstructor
public class InfoController {

    private final CategoryService categoryService;

    @RequestMapping({"/info.html", "/info"})
    public String info(Model model) {

        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != "anonymousUser") {
            model.addAttribute("user", principal);
        }

        model.addAttribute("categories", categoryService.findAllLimitedFive());

        return "info";
    }
}
