package net.poppinger.memory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {

    private int value;
    private String url;
    private CardState state;


    public Card(){
    }
    public Card(int value,String url){
        this.value = value;
        this.state = CardState.HIDDEN;
        this.url = url;

    }


    public String getRawUrl(){
        return url;
    }
    public void setRawUrl(String value){
        url=value;
    }

    public String getUrl(){
        if (state == CardState.HIDDEN){
            return "images/hiddenCard.webp";
        }
        return url;
    }

    private void setUrl(String value){
       url=value;
    }


    public int getValue(){
        if (state == CardState.HIDDEN){
            return 0;
        }
        return value;
    }

    public void toggle(){
        if (state == CardState.HIDDEN){
            state = CardState.REVEALED;
        }else{
            state = CardState.HIDDEN;
        }
    }

}
