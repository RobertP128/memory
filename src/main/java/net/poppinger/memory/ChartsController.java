package net.poppinger.memory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/charts/")
public class ChartsController {

    @RequestMapping("index.html")
    public ModelAndView index() throws JsonProcessingException, FileNotFoundException,IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("charts/index.html");

        // read data1.txt from resources folder
        // get Resources Folder
        File f=new File(ChartsController.class.getClassLoader().getResource("data1.txt").getFile());
        InputStream in = new FileInputStream(f);
        BufferedReader br=new BufferedReader(new InputStreamReader(in));

        List<List<Float>> chartData=new ArrayList<>();

        String line=br.readLine();
        while((line=br.readLine())!=null) {
            String[] parts=line.split("\t");
            if (parts.length==2){
                var x=Float.parseFloat(parts[0])/1024;
                var y=Float.parseFloat(parts[1]);
                chartData.add(new ArrayList<>(List.of(x,y)));
            }

        }


        ObjectMapper mapper = new ObjectMapper();
        modelAndView.addObject("chartData",mapper.writeValueAsString(chartData));
        return modelAndView;
    }
}
