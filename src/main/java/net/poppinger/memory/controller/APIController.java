package net.poppinger.memory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/")
public class APIController {

    final AppModel appModel;

    public APIController(AppModel appModel) {
        this.appModel = appModel;
    }

    @RequestMapping("test")
    @ResponseBody
    public String test(){
        return "test";
    }


    @RequestMapping(value = "resetBoard",produces = "application/json")
    @ResponseBody
    public String resetBoard() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        appModel.reset();
        return mapper.writeValueAsString(appModel.getGame());

    }


    @RequestMapping(value = "initBoard",produces = "application/json")
    @ResponseBody
    public String initBoard() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(appModel.getGame());

    }


    @RequestMapping(value = "getBoard",produces = "application/json")
    @ResponseBody
    public String getBoard() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(appModel.getGame());

    }

    @RequestMapping(value = "swapPlayerResponse",produces = "application/json")
    @ResponseBody
    public String swapPlayerResponse() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var game=appModel.getGame();
        game.swapPlayerResponse();
        return mapper.writeValueAsString(game);

    }


    @RequestMapping(value = "clickCard",produces = "application/json")
    @ResponseBody
    public String clickCard(@RequestParam Integer x,@RequestParam Integer y) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        var game=appModel.getGame();
        game.toggleCard(x,y);

        var json = mapper.writeValueAsString(game);
        game.getActions().clear();
        return json;
    }


}
