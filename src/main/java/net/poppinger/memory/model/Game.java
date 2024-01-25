package net.poppinger.memory.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Game {

    public static final String SWAP_PLAYER="SWAP_PLAYER";

    private Board board;

    private static Integer id_seq=0;
    private Integer id=0;

    private int activePlayer;
    private List<String> actions = new ArrayList<>();

    public Game(){

        activePlayer = 1;
        id=id_seq++;
    }

    public Game(Board board){
        this.board=board;
        activePlayer = 1;
        id=id_seq++;
    }

    private void swapActivePlayer(){
        if (activePlayer == 1){
            activePlayer = 2;
        }else{
            activePlayer = 1;
        }

    }

    public void toggleCard(int x,int y){

        actions.clear();

        var selctedCard=board.getCards()[y][x];
        if (selctedCard == null){
            return;
        }

        if (selctedCard.getState() == CardState.REVEALED){
            // Cannot flip a flipped Card
            return;
        }

        var openedCards=board.getReveledCardsCoordinates();
        if (openedCards.size()==0){
            // Open Card
            selctedCard.toggle();
            return;
        }
        if (openedCards.size()==1){
            // Open Card
            selctedCard.toggle();
            var prevCoord=openedCards.get(0);
            var prevOpenenedCard=board.getCardByCoordinates(prevCoord);
            // check if they are the same
            if (prevOpenenedCard.getValue() == selctedCard.getValue()){
                // add Both cards to activePlayers stack
                // set both cards to null
                // add Both cards to activePlayers stack
                if (activePlayer == 1){
                    board.getPlayer1Stack().addCard(prevOpenenedCard);
                    board.getPlayer1Stack().addCard(selctedCard);
                }
                else {
                    board.getPlayer2Stack().addCard(prevOpenenedCard);
                    board.getPlayer2Stack().addCard(selctedCard);
                }

                // set both cards to null
                board.getCards()[y][x] = null;
                board.getCards()[prevCoord.getY()][prevCoord.getX()] = null;
            }
            else{
                actions.add(SWAP_PLAYER);
            }



        }

    }


    public void swapPlayerResponse(){

        if (board.getReveledCardsCoordinates().size()==2){
            board.hideAllCards();

            swapActivePlayer();
        }
    }
}

