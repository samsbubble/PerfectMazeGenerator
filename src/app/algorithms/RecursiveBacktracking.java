package app.algorithms;

import java.util.ArrayList;
import java.util.Collections;

public class RecursiveBacktracking {

    private int dimX, dimY;
    private Maze maze;

    public RecursiveBacktracking(int dimX, int dimY){
        maze = new Maze(dimX, dimY);
    }

    public void generateMaze() {
        int currentX = (int) Math.random() * dimX;
        int currentY = (int) Math.random() * dimY;
        generateRecursiveBacktracking(currentX, currentY);
    }

    public Maze generateRecursiveBacktracking(int currentX, int currentY){
        ArrayList<Direction> direction = Direction.getDirections();
        // Shuffle the directions
        Collections.shuffle(direction);
        for (Direction dir : direction) {
            // Get the next cell based on the new direction
            int newX = currentX + dir.directionX;
            int newY = currentY + dir.directionY;
            // Check if the cell is valid
            if ( isValidTile(newX, newY) ){
                // Store the directions, which needs to be carved.
                maze.setTile(currentX, currentY, dir);
                maze.setTile(newX, newY, dir.getOpposite());
                maze.setVisited(currentX, currentY);
                generateRecursiveBacktracking(newX, newY);
            }
        }
       return maze;
    }

    private Boolean isValidTile(int newX, int newY){
        return newX >= 0 && newX < dimX && newY >= 0 && newY < dimY && maze.isVisited(newX, newY).equals(false);
    }

}
