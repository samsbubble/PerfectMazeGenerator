package app.algorithms;

import java.util.ArrayList;
import java.util.Collections;

public class RecursiveBacktracking implements MazeGenerator {

    private int dimX, dimY;
    private int[][] maze;

    public RecursiveBacktracking(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;
        maze = new int[this.dimX][this.dimY];
    }


    @Override
    public void generateMaze() {
        int currentX = (int) Math.random() * dimX;
        int currentY = (int) Math.random() * dimY;
        generateRecursiveBacktracking(currentX, currentY);
    }

    public int[][] generateRecursiveBacktracking(int currentX, int currentY){
        ArrayList<Direction> direction = Direction.getDirections();
        // Shuffle the directions
        Collections.shuffle(direction);
        for (Direction dir : direction) {
            // Get the next cell based on the new direction
            int newX = currentX + dir.directionX;
            int newY = currentY + dir.directionY;
            // Check if the cell is valid
            if ( newX >= 0 && newX < dimX && newY >= 0 && newY < dimY && maze[newX][newY] == 0 ){
                // Store the directions, which needs to be carved.

                generateRecursiveBacktracking(newX, newY);
            }
        }
       return maze;
    }

}
