package net.poppinger.memory.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coord {
    private int x;
    private int y;

    public Coord(int x,int y){
        this.x = x;
        this.y = y;
    }

}
