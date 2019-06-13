package app.logic.tracking;

import app.logic.domain.Cell;
import app.logic.domain.Direction;

// Class for saving knocking down a wall as an operation.
public class KnockDownWall extends Operation {

    private Direction wall;
    private Cell current, next;

    public KnockDownWall(int x1, int y1, int x2, int y2){
        current = new Cell(x1, y1);
        next = new Cell(x2, y2);
        if (y2 == y1 + 1)
            wall = Direction.SOUTH;
        if (y2 == y1 - 1)
            wall = Direction.NORTH;
        if (x2 == x1 + 1)
            wall = Direction.EAST;
        if (x2 == x1 - 1)
            wall = Direction.WEST;
    }

    public Direction getWall() {
        return wall;
    }

    public int getCurrentX(){
        return current.getXCoordinate();
    }

    public int getCurrentY(){
        return current.getYCoordinate();
    }

    @Override
    public String toString(){
        return "Knock down " + wall + " wall (" + current.getXCoordinate()+"," + current.getYCoordinate()+") to ("+next.getYCoordinate()+","+next.getYCoordinate()+")";
    }
}
