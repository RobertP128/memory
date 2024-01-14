package net.poppinger.memory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/")
public class APIController {


    private Map<String, AppModel> sessions = new HashMap<>();

    public APIController() {
    }


    private AppModel getAppModel(HttpSession session) {
        var appmodelKey=(String)session.getAttribute("appModel");
        if (appmodelKey==null){
            appmodelKey=session.getId();
            var appmodel = new AppModel();
            setAppModel(session,appmodel);
        }
        var appmodel = sessions.get(appmodelKey);
        return appmodel;
    }
    private void setAppModel(HttpSession session,AppModel appModel) {
        session.setAttribute("appModel",session.getId());
        sessions.put(session.getId(),appModel);
    }


    @RequestMapping("test")
    @ResponseBody
    public String test(HttpSession session){
        session.setAttribute("test","test");
        return "test";
    }


    @RequestMapping(value = "resetBoard",produces = "application/json")
    @ResponseBody
    public String resetBoard(HttpSession session) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var appModel=getAppModel(session);
        appModel.reset();
        setAppModel(session,appModel);
        return mapper.writeValueAsString(appModel.getGame());

    }


    @RequestMapping(value = "initBoard",produces = "application/json")
    @ResponseBody
    public String initBoard(HttpSession session) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var appModel=getAppModel(session);
        return mapper.writeValueAsString(appModel.getGame());

    }


    @RequestMapping(value = "getBoard",produces = "application/json")
    @ResponseBody
    public String getBoard(HttpSession session) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var appModel=getAppModel(session);

        return mapper.writeValueAsString(appModel.getGame());

    }

    @RequestMapping(value = "swapPlayerResponse",produces = "application/json")
    @ResponseBody
    public String swapPlayerResponse(HttpSession session) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var appModel=getAppModel(session);

        var game=appModel.getGame();
        game.swapPlayerResponse();
        setAppModel(session,appModel);

        return mapper.writeValueAsString(game);

    }


    @RequestMapping(value = "clickCard",produces = "application/json")
    @ResponseBody
    public String clickCard(@RequestParam Integer x,@RequestParam Integer y,HttpSession session) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var appModel=getAppModel(session);

        var game=appModel.getGame();
        game.toggleCard(x,y);

        var json = mapper.writeValueAsString(game);
        game.getActions().clear();

        setAppModel(session,appModel);

        return json;
    }


}
