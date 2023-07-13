package se.systementor.oidcdemo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpStatusCodeException;

@Controller
public class HomeController {

    @GetMapping(path="/")
    String empty(Model model)
    {
        model.addAttribute("activeFunction", "home");

        model.addAttribute("user_userName", getUserName(model));
        return "home";
    }

    @GetMapping(path="/admin")
    String admin(Model model)
    {
        if(!getUserName(model).endsWith("systementor.se")) return "redirect:/noaccesstofunction";
        model.addAttribute("activeFunction", "admin");

        model.addAttribute("user_userName", getUserName(model));
        return "admin";
    }

    private String getUserName(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object ud = auth.getPrincipal();
        if(ud instanceof OidcUser principal){
            return principal.getEmail();
        }
        return "inget";

    }


}
