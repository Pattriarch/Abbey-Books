package spring.framework.labs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InfoController {
    @RequestMapping({"/info.html", "/info"})
    public String info() {
        return "info";
    }
}
