package app.algorithms;

public class Solution {

    // Coordinates for the Tile
    // The direction, for which wall is broken down

    private int xCoordinate, yCoordinate;
    private Direction dir;

    public Solution(int xCoordinate, int yCoordinate, Direction dir){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.dir = dir;
    }

    public int getxCoordinate(){
        return this.xCoordinate;
    }

    public int getyCoordinate(){
        return this.yCoordinate;
    }

    public Direction getDirection(){
        return this.dir;
    }
}
