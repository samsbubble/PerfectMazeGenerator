package app.algorithms.Tracking;

import app.algorithms.Direction;

public class KnockDownWall extends Operation {

    Direction wall;

    public KnockDownWall(int x1, int y1, int x2, int y2){
        if (y2 == y1 + 1)
            wall = Direction.SOUTH;
        if (y2 == y1 - 1)
            wall = Direction.NORTH;
        if (x2 == x1 + 1)
            wall = Direction.EAST;
        if (x2 == x1 - 1)
            wall = Direction.WEST;
    }

    @Override
    public String toString(){
        return "Knock down " + wall + " wall";
    }

}
