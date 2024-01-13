package net.poppinger.memory.controller;

import lombok.Getter;
import net.poppinger.memory.model.Board;
import net.poppinger.memory.model.Game;
import org.springframework.stereotype.Component;

@Component
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
}
