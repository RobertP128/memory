package net.poppinger.memory;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebGameController {

    @RequestMapping("/game.html")
    public ModelAndView index(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("game.html");
        modelAndView.addObject("currentUser", authentication.getName());
        return modelAndView;
    }
}
