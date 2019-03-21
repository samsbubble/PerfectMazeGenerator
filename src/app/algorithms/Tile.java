package app.algorithms;

import java.util.HashMap;
import java.util.Map;

public class Tile {

    private Map<Direction, Boolean> walls;  // {NORTH, EAST, SOUTH, WEST}
    private Boolean visited;

    // List of the four walls
    public Tile(){
        this.walls = new HashMap<>();
        this.walls.put(Direction.NORTH, false);
        this.walls.put(Direction.SOUTH, false);
        this.walls.put(Direction.EAST, false);
        this.walls.put(Direction.WEST, false);

        this.visited = false;
    }

    public Boolean isVisited(){
        return this.visited;
    }

    public void setVisited(){
        this.visited = true;
    }


    public void breakDownWall(Direction dir){
       this.walls.put(dir, true);
    }



}
