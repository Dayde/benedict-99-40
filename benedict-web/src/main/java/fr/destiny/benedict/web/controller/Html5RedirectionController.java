package fr.destiny.benedict.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Html5RedirectionController {

    @RequestMapping("{path:(?:(?!api|\\.).)*}/**")
    public String redirect(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAllAttributes(request.getParameterMap());
        return "forward:/";
    }
}
