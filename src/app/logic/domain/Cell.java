package app.logic.domain;

import app.logic.domain.Direction;

import java.util.HashMap;
import java.util.Map;

public class Cell {

    private int xCoordinate, yCoordinate;
    private Map<Direction, Boolean> walls;  // {NORTH, EAST, SOUTH, WEST}
    private Boolean visited;

    // List of the four walls
    Cell(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        this.walls = new HashMap<>();
        this.walls.put(Direction.NORTH, true);
        this.walls.put(Direction.SOUTH, true);
        this.walls.put(Direction.EAST, true);
        this.walls.put(Direction.WEST, true);

        this.visited = false;
    }

    public Boolean isVisited(){
        return this.visited;
    }

    public void setVisited(){
        this.visited = true;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void breakDownWall(Direction dir) {
        this.walls.put(dir, false);
    }
}