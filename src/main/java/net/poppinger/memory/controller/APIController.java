package net.poppinger.memory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/api/")
public class APIController {


    private Map<String, AppModel> sessions = new HashMap<>();

    public APIController() {
    }


    public Map<String, AppModel> getRawSessions() {
        return sessions;
    }


    private AppModel createAppModel() {
        var appModel= new AppModel();
        appModel.reset();
        appModel.getGame().getBoard().initBoard();
        return appModel;
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String generateAppModelKey(HttpSession session) throws NoSuchAlgorithmException{
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(
                session.getId().getBytes(StandardCharsets.UTF_8));
        String sha3Hex = bytesToHex(hashbytes);
        return sha3Hex;
    }


    private AppModel getAppModel(HttpSession session) {

        var joinedSessionKey=(String)session.getAttribute("sessionKey");
        if (joinedSessionKey!=null){
            if (sessions.containsKey(joinedSessionKey)){
                return sessions.get(joinedSessionKey);
            }
        }

        var appmodelKey=(String)session.getAttribute("appModel");
        if (appmodelKey==null){
            try {
                appmodelKey = generateAppModelKey(session);
                var appmodel = createAppModel();
                setAppModel(session,appmodel);
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
        var appmodel = sessions.get(appmodelKey);
        if (appmodel==null){
            appmodel = createAppModel();
            try {
                setAppModel(session, appmodel);
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
        return appmodel;
    }
    private void setAppModel(HttpSession session,AppModel appModel) throws NoSuchAlgorithmException {
        String key="";
        // search for the same instance first
        // if not present the add as this key
        for(Map.Entry<String, AppModel> entry : sessions.entrySet()) {
            if (entry.getValue().equals(appModel)){
                key=entry.getKey();
                break;
            }
        }
        if (key==""){
            key=generateAppModelKey(session);
            session.setAttribute("appModel",key);
        }
        sessions.put(key,appModel);
    }


    @RequestMapping("test")
    @ResponseBody
    public String test(HttpSession session){
        session.setAttribute("test","test");
        return "test";
    }


    @RequestMapping("getSessions")
    @ResponseBody
    public Set<String> getSessions(){
        var list=sessions.keySet();
        return list;
    }



    @RequestMapping(value = "resetBoard",produces = "application/json")
    @ResponseBody
    public String resetBoard(HttpSession session) throws JsonProcessingException {
        var appModel=getAppModel(session);
        if (appModel!=null) {
            appModel.reset();
            try {
                setAppModel(session, appModel);
                return appModel.serlialize(true);
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new RuntimeException("No such algorithm exception");
            }
        }
        throw new RuntimeException("appModel is null");

    }


    @RequestMapping(value = "initBoard",produces = "application/json")
    @ResponseBody
    public String initBoard(HttpSession session) throws JsonProcessingException {
        var appModel=getAppModel(session);
        return appModel.serlialize(true);

    }


    @RequestMapping(value = "getBoard",produces = "application/json")
    @ResponseBody
    public String getBoard(HttpSession session) throws JsonProcessingException {
        var appModel=getAppModel(session);

        return appModel.serlialize(true);

    }

    @RequestMapping(value = "swapPlayerResponse",produces = "application/json")
    @ResponseBody
    public String swapPlayerResponse(HttpSession session) throws JsonProcessingException,NoSuchAlgorithmException {
        var appModel=getAppModel(session);

        var game=appModel.getGame();
        game.swapPlayerResponse();
        setAppModel(session,appModel);

        return appModel.serlialize(true);

    }


    @RequestMapping(value = "clickCard",produces = "application/json")
    @ResponseBody
    public String clickCard(@RequestParam Integer x,@RequestParam Integer y,HttpSession session) throws JsonProcessingException,NoSuchAlgorithmException {
        var appModel=getAppModel(session);

        var game=appModel.getGame();
        game.toggleCard(x,y);

        var json = appModel.serlialize(true);
        game.getActions().clear();

        setAppModel(session,appModel);

        return json;
    }


}
