package net.poppinger.memory;

import jakarta.servlet.http.HttpSession;
import net.poppinger.memory.controller.APIController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebGameController {

    final APIController apiController;

    public WebGameController(APIController apiController) {
        this.apiController = apiController;
    }


    @RequestMapping("/joingame.html")
    public ModelAndView joingame(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("joingame.html");
        modelAndView.addObject("sessions", apiController.getSessions());
        return modelAndView;
    }


    @RequestMapping("/game.html")
    public ModelAndView index(Authentication authentication,
                              @RequestParam(name = "sessionid", required = false) String sessionid,
                              HttpSession session) {

        if (sessionid!=null){
            // set the sessionKey as active for this player and sdave it to the current session
            session.setAttribute("sessionKey", sessionid);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("game.html");
        modelAndView.addObject("currentUser", authentication.getName());
        return modelAndView;
    }
}
