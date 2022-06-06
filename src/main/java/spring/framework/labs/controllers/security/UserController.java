package spring.framework.labs.controllers.security;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.domain.security.User;
import spring.framework.labs.mappers.UserMapper;
import spring.framework.labs.repositories.security.UserRepository;
import spring.framework.labs.services.BookService;
import spring.framework.labs.services.CategoryService;
import spring.framework.labs.services.security.UserService;

@Slf4j
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final GoogleAuthenticator googleAuthenticator;

    @GetMapping("/register2fa")
    public String register2fa(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        String url = GoogleAuthenticatorQRGenerator.getOtpAuthURL("SFG", user.getUsername(),
                googleAuthenticator.createCredentials(user.getUsername()));

        log.debug("Google QR URL: " + url);

        model.addAttribute("googleurl", url);

        return "user/registration2fa";
    }

    @PostMapping("/register2fa")
    public String confirm2Fa(@RequestParam(required = false) Integer verifyCode, Model model){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        log.debug("Введённый код:" + verifyCode);

        if (verifyCode != null && googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)) {
            User savedUser = userRepository.findById(user.getId()).orElseThrow();
            savedUser.setUseGoogle2fa(true);
            userRepository.save(savedUser);

            User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userContext.setUseGoogle2fa(true);

            model.addAttribute("lastBooks", bookService.findFiveLastBooks());
            model.addAttribute("bestBooks", bookService.findAllByOrderByRatingValueDescLimitFive());

            model.addAttribute("categories", categoryService.findAll());

            return "user/profile";
        } else {
            String url = GoogleAuthenticatorQRGenerator.getOtpAuthURL("SFG", user.getUsername(),
                    googleAuthenticator.createCredentials(user.getUsername()));

            log.debug("Google QR URL: " + url);

            model.addAttribute("googleurl", url);

            return "user/registration2fa";
        }
    }

    @GetMapping("/verify2fa")
    public String verify2fa(){
        return "user/verify2fa";
    }

    @PostMapping("/verify2fa")
    public String verifyPostOf2Fa(@RequestParam Integer verifyCode, Model model){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)) {
            ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setGoogle2faRequired(false);

            model.addAttribute("userDTO", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            model.addAttribute("lastBooks", bookService.findFiveLastBooks());
            model.addAttribute("bestBooks", bookService.findAllByOrderByRatingValueDescLimitFive());

            model.addAttribute("categories", categoryService.findAll());

            return "redirect:/";
        } else {
            return "user/verify2fa";
        }
    }

    @GetMapping("/disable2fa")
    public String disable2fa() {
        return "user/disable2fa";
    }

    @PostMapping("/disable2fa")
    public String disablePostOf2Fa(@RequestParam Long verifyCode, Model model){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (googleAuthenticator.authorizeUser(user.getUsername(), Math.toIntExact(verifyCode))) {
            User user123 = userRepository.findById(user.getId()).get();
            user123.setGoogle2faRequired(false);
            user123.setUseGoogle2fa(false);
            user123.setGoogle2faSecret(null);

            user.setGoogle2faRequired(false);
            user.setUseGoogle2fa(false);
            user.setGoogle2faSecret(null);

            userRepository.save(user123);

            model.addAttribute("userDTO", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            model.addAttribute("lastBooks", bookService.findFiveLastBooks());
            model.addAttribute("bestBooks", bookService.findAllByOrderByRatingValueDescLimitFive());

            model.addAttribute("categories", categoryService.findAll());

            return "redirect:/";
        } else {

            User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", userContext);
            String badCode = "bad_code";
            model.addAttribute("mistake", badCode);

            return "user/profile";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);
        model.addAttribute("categories", categoryService.findAllLimitedFive());

        return "user/profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String againNewPassword,
                                 Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (newPassword.equals(againNewPassword)) {
            userService.changePassword(currentPassword, newPassword, Long.valueOf(user.getId()));
        } else {
            String badPass = "bad_pass";
            model.addAttribute("mistake", badPass);
        }

        model.addAttribute("user", user);

        return "user/profile";
    }

    @PostMapping("addBalance")
    public String addBalance(@RequestParam Long value, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userWithUpdatedBalance =  userService.addBalance(Long.valueOf(user.getId()), value);

        user.setBalance(userWithUpdatedBalance.getBalance());

        model.addAttribute("user", user);

        return "redirect:/user/profile";
    }
}