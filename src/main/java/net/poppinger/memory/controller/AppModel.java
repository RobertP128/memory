package net.poppinger.memory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import net.poppinger.memory.model.Board;
import net.poppinger.memory.model.Game;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

@Getter
public class AppModel {
    private Game game;

    public AppModel(){
        reset();
    }

    public void reset(){
        game = new Game(new Board());
    }

    public void shuffleCards(){
        game.getBoard().shuffleCards();
    }

    public void persist() throws JsonProcessingException, IOException {
        // Serialize Game
        ObjectMapper mapper = new ObjectMapper();
        try {
            String serializedGame = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
            // Write the string to a file
            BufferedWriter bw=new BufferedWriter(new java.io.FileWriter("game_"+game.getId()+".json"));
            bw.write(serializedGame);
            bw.close();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle serialization error
            // ...
        }

    }

    public void loadFromFile(String filename) {

        // Deserialize Game
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(filename);
            Game loadedGame = mapper.readValue(file, Game.class);
            game = loadedGame;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle deserialization error
            // ...
        }

    }
}
