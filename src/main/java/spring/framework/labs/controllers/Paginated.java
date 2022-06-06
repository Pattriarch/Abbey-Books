package spring.framework.labs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.services.CategoryService;

@RequiredArgsConstructor
public class Paginated {
    private final CategoryService categoryService;
    public static final int PAGE_SIZE = 30;

    public Model addAttributesToModel(@PathVariable(value = "pageNo") int pageNo,
                                      @RequestParam(value = "sortField", defaultValue = "id", required = false) String sortField,
                                      @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir,
                                      Model model) {

        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != "anonymousUser") {
            model.addAttribute("user", principal);
        }

        model.addAttribute("categories", categoryService.findAllLimitedFive());

        model.addAttribute("currentPage", pageNo);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return model;
    }

}
