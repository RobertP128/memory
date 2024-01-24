package net.poppinger.memory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/charts/")
public class ChartsController {

    @RequestMapping("index.html")
    public ModelAndView index() throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("charts/index.html");

        int[][] chartData=new int[10][2];

        for (int x=0;x<chartData.length;x++){
            for (int y=0;y<chartData[x].length;y++){
                chartData[x][y]=x+y;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        modelAndView.addObject("chartData",mapper.writeValueAsString(chartData));
        return modelAndView;
    }
}
