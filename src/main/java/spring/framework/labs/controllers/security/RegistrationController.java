package spring.framework.labs.controllers.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.file.Matcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.exceptions.UserAlreadyExistException;
import spring.framework.labs.services.security.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.regex.Pattern;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    public String userRegistration(final @Valid UserDTO userDTO, final BindingResult bindingResult, final Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
        } else {
            try {
                userService.save(userDTO);
                try {
                    request.login(userDTO.getUsername(), userDTO.getPassword());
                } catch (ServletException e) {
                    log.error("Произошла ошибка при входе в аккаунт: " + e.getMessage());
                }
            } catch (UserAlreadyExistException e) {
                bindingResult.rejectValue("username", "userData.username", "Аккаунт с таким именем уже существует.");
                model.addAttribute("userDTO", userDTO);
                log.error("Произошла ошибка при попытке регистрации уже существующего пользователя: " + e.getMessage());
            }
        }

        return "index";
    }
}
