package net.poppinger.memory.cron;

import net.poppinger.memory.controller.APIController;
import net.poppinger.memory.controller.AppModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@EnableScheduling
public class PersistanceCron {


    final APIController apiController;

    public PersistanceCron(APIController apiController) {
        this.apiController = apiController;
    }


    @Scheduled(fixedDelay = 10000)
    public void persist(){
        try {
            System.out.println("Persisting Game...");
            var namesIterator = apiController.getRawSessions().entrySet().iterator();
            while(namesIterator.hasNext()){
                var entry=namesIterator.next();
                var appModel=entry.getValue();
                //var sessionKey=entry.getKey();
                appModel.persist();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
