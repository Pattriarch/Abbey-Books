package spring.framework.labs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.framework.labs.domain.dtos.security.UserDTO;

@Controller
public class IndexController {
    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "index";
    }
}