package net.poppinger.memory.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class MainController {

    final APIController apiController;

    public MainController(APIController apiController) {
        this.apiController = apiController;
    }

    @PostConstruct
    public void init(){
        // Read current Folder and find all Files matching the pattern game_<id>.json
        File directoryPath = new File("./");
        File files[] = directoryPath.listFiles();
        for(var file : files){
            if (file.isFile() && file.getName().startsWith("game_")){
                try {
                    AppModel appModel = new AppModel();
                    appModel.loadFromFile(file.getPath());
                    var rndStr = UUID.randomUUID().toString();
                    apiController.getRawSessions().put(rndStr,appModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @RequestMapping("")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

}
