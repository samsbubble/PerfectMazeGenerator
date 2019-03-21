package app.algorithms;

import javafx.scene.Scene;

import java.util.ArrayList;

public enum Direction {
    EAST(1, 0), WEST(-1, 0), NORTH(0, -1), SOUTH(0, 1);

    final int directionX, directionY;

    Direction(int directionX, int directionY){
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public int getDirectionX(Direction dir){
        return dir.directionX;
    }

    public int getDirectionY(Direction dir){
        return dir.directionY;
    }

    public Direction getOpposite(Direction dir){
        switch(dir) {
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            default:
                return EAST;
        }
    }

    public static ArrayList<Direction> getDirections(){
        ArrayList<Direction> list = new ArrayList<Direction>(){};
        list.add(EAST);
        list.add(WEST);
        list.add(SOUTH);
        list.add(NORTH);
        return list;
    }

}
