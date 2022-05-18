package spring.framework.labs.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.exceptions.UserAlreadyExistException;
import spring.framework.labs.services.security.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public String showRegistrationForm(Model model) {
//        UserData userData = new UserData();
//        model.addAttribute("userData", userData);
//        return "registration";
//    }

    @PostMapping("/register")
    public String userRegistration(final @Valid UserDTO userDTO, final BindingResult bindingResult, final Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
            return "index";
        } else {
            try {
                userService.save(userDTO);
                try {
                    request.login(userDTO.getUsername(), userDTO.getPassword());
                } catch (ServletException e) {
                    e.printStackTrace();
                }
                return "index";

            } catch (UserAlreadyExistException e) {
                bindingResult.rejectValue("email", "userData.email", "An account already exists for this email.");
                model.addAttribute("userDTO", userDTO);
                return "index";
            }
        }
    }
}
