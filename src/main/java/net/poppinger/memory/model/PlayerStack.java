package net.poppinger.memory.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerStack {
    private List<Card> cards;

    public PlayerStack(){
        cards=new ArrayList<>();
    }

    public void addCard(Card card){
        cards.add(card);
    }
}
