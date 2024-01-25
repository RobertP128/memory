package net.poppinger.memory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {
    private Card[][] cards;
    private PlayerStack player1Stack;
    private PlayerStack player2Stack;

    public Board(){

    }
    public void initBoard(){
        player1Stack = new PlayerStack();
        player2Stack = new PlayerStack();

        cards = new Card[Config.BOARD_HEIGHT][Config.BOARD_WIDTH];
        int numCards=Config.BOARD_HEIGHT*Config.BOARD_WIDTH;
        for (int i = 0; i < numCards; i++) {
            cards[i / Config.BOARD_WIDTH][i % Config.BOARD_WIDTH] = new Card(i/2,"images/pic_"+i/2+".webp");
        }
        //shuffleCards();

    }

    public void shuffleCards(){
        int numCards=Config.BOARD_HEIGHT*Config.BOARD_WIDTH/2;
        for (int i = 0; i < numCards; i++) {
            int card1 = (int) (Math.random() * Config.BOARD_WIDTH * Config.BOARD_HEIGHT);
            int card2 = (int) (Math.random() * Config.BOARD_WIDTH * Config.BOARD_HEIGHT);
            Card temp = cards[card1 / Config.BOARD_WIDTH][card1 % Config.BOARD_WIDTH];
            cards[card1 / Config.BOARD_WIDTH][card1 % Config.BOARD_WIDTH] = cards[card2 / Config.BOARD_WIDTH][card2 % Config.BOARD_WIDTH];
            cards[card2 / Config.BOARD_WIDTH][card2 % Config.BOARD_WIDTH] = temp;
        }

    }

    public void hideAllCards(){
        for (int i = 0; i < Config.BOARD_HEIGHT; i++) {
            for (int j = 0; j < Config.BOARD_WIDTH; j++) {
                if (cards[i][j]!=null) {
                    cards[i][j].setState(CardState.HIDDEN);
                }
            }
        }
    }

    @JsonIgnore
    public List<Coord> getReveledCardsCoordinates(){
        var reveledCards=new ArrayList<Coord>();
        for (int i = 0; i < Config.BOARD_HEIGHT; i++) {
            for (int j = 0; j < Config.BOARD_WIDTH; j++) {
                if (cards[i][j]!=null && cards[i][j].getState() == CardState.REVEALED){
                    reveledCards.add(new Coord(j,i));
                }
            }
        }
        return reveledCards;
    }

    @JsonIgnore
    public Card getCardByCoordinates(Coord coord){
        return cards[coord.getY()][coord.getX()];
    }

}
