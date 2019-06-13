package app.logic.domain;

import app.logic.domain.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cell {

    private int xCoordinate, yCoordinate;
    private Map<Direction, Boolean> walls;  // {NORTH, EAST, SOUTH, WEST}
    private Boolean visited;

    // Constructor for the Cell object, initialising its coordinates, walls and visited flag.
    public Cell(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        this.walls = new HashMap<>();
        this.walls.put(Direction.NORTH, true);
        this.walls.put(Direction.SOUTH, true);
        this.walls.put(Direction.EAST, true);
        this.walls.put(Direction.WEST, true);

        this.visited = false;
    }
    // Method returning the value of the visited flag.
    public Boolean isVisited(){
        return this.visited;
    }

    // Method setting the visited flag to the opposite value.
    public void setVisited(){
        this.visited = !this.visited;
    }

    // Get x coordinate
    public int getXCoordinate() {
        return xCoordinate;
    }

    // Get y coordinate
    public int getYCoordinate() {
        return yCoordinate;
    }

    // Break down the wall by setting the value of the wall to false
    public void breakDownWall(Direction dir) {
        this.walls.put(dir, false);
    }

    // Get the number of wall by counting how many are still true.
    public int getNumberOfWalls() {
        int numberOfWalls = 0;
        if(walls.get(Direction.NORTH))
            numberOfWalls++;
        if(walls.get(Direction.EAST))
            numberOfWalls++;
        if(walls.get(Direction.SOUTH))
            numberOfWalls++;
        if(walls.get(Direction.WEST))
            numberOfWalls++;

        return numberOfWalls;
    }

    // Gets the value of the wall with the given direction.
    public boolean getValueOfWall(Direction dir) {
        return walls.get(dir);
    }

    // Methods for being able to compare cell elements.
    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Cell)) {
            return super.equals(obj);
        }

        Cell comp = (Cell)obj;
        return this.getXCoordinate() == comp.getXCoordinate() && this.getYCoordinate() == comp.getYCoordinate();
    }

    // Method for making the same hashcode if two cells have the same coordinates.
    @Override
    public int hashCode() {
        return Objects.hash(this.getXCoordinate(), this.getYCoordinate());
    }
}
