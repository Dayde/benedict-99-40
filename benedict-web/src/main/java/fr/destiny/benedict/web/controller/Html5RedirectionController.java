package fr.destiny.benedict.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Html5RedirectionController {

    @RequestMapping("{path:(?:(?!api|\\.).)*}/**")
    public String redirect() {
        return "forward:/";
    }
}
