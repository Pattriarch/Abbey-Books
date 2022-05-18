package spring.framework.labs.controllers.security;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.framework.labs.domain.dtos.security.UserDTO;
import spring.framework.labs.domain.security.User;
import spring.framework.labs.repositories.security.UserRepository;
import spring.framework.labs.services.security.UserService;

import java.util.Optional;

@Slf4j
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final GoogleAuthenticator googleAuthenticator;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register2fa")
    public String register2fa(Model model){

        User user = getUser();

        String url = GoogleAuthenticatorQRGenerator.getOtpAuthURL("SFG", user.getUsername(),
                googleAuthenticator.createCredentials(user.getUsername()));

        log.debug("Google QR URL: " + url);

        model.addAttribute("googleurl", url);

        return "user/register2fa";
    }

    @PostMapping("/register2fa")
    public String confirm2Fa(@RequestParam Integer verifyCode, Model model){

        User user = getUser();

        log.debug("Entered Code is:" + verifyCode);

        if (googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)) {
            User savedUser = userRepository.findById(user.getId()).orElseThrow();
            savedUser.setUseGoogle2fa(true);
            userRepository.save(savedUser);

            User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userContext.setUseGoogle2fa(true);

            model.addAttribute("userDTO", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            return "index";
        } else {
            String url = GoogleAuthenticatorQRGenerator.getOtpAuthURL("SFG", user.getUsername(),
                    googleAuthenticator.createCredentials(user.getUsername()));

            log.debug("Google QR URL: " + url);

            model.addAttribute("googleurl", url);

            // bad code
            return "user/register2fa";
        }
    }

    @GetMapping("/verify2fa")
    public String verify2fa(){
        return "user/verify2fa";
    }

    @PostMapping("/verify2fa")
    public String verifyPostOf2Fa(@RequestParam Integer verifyCode, Model model){

        User user = getUser();

        if (googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)) {
            ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setGoogle2faRequired(false);

            model.addAttribute("userDTO", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            return "index";
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

        User user = getUser();

        if (googleAuthenticator.authorizeUser(user.getUsername(), Math.toIntExact(verifyCode))) {
            User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user123 = userRepository.findById(userContext.getId()).get();
            user123.setGoogle2faRequired(false);
            user123.setUseGoogle2fa(false);
            user123.setGoogle2faSecret(null);

            userContext.setGoogle2faRequired(false);
            userContext.setUseGoogle2fa(false);
            userContext.setGoogle2faSecret(null);

            userRepository.save(user123);

            return "index";
        } else {

            User userContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", userContext);
            String vl = "aaaassss";
            model.addAttribute("mistake", vl);

            return "user/profile";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);

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
            String vl = "asdasdasas";
            model.addAttribute("mistake", vl);

        }

        model.addAttribute("user", user);

        return "user/profile";
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}