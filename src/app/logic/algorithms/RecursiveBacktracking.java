package app.logic.algorithms;

import app.logic.Algorithm;
import app.logic.tracking.*;
import app.logic.domain.Maze;
import app.logic.domain.Cell;
import app.logic.domain.Method;

import java.util.ArrayList;


public class RecursiveBacktracking extends Algorithm {
    private OperationTracker opTracker;
    private Maze maze;

    // Constructor of the Recursive Backtracking algorithm, which initialises the maze and the list of operations.
    public RecursiveBacktracking(Maze maze){
        opTracker = new OperationTracker();
        this.maze = maze;
    }

    // Method for returning the list of operations.
    public OperationTracker getOpTracker(){
        return opTracker;
    }

    // Method to run the Recursive Backtracking algorithm.
    public void runRBT(int currentX, int currentY){
        // Track move
        opTracker.add(new Move(currentX, currentY));

        // Set cell to visited.
        maze.setVisited(currentX, currentY);

        Cell nextCell;
        ArrayList<Cell> neighbours;
        do{
            // Get the legal neighbours to the current cell
            neighbours = maze.getPossibleNeighbours(currentX, currentY);

            // If there aren't any neighbours, the maze is complete.
            if(neighbours.isEmpty())
                break;

            // Get the next cell from the list of neighbours.
            nextCell = Method.getRandom(neighbours);

            // Break down the wall between the current and next cell and track the operation.
            maze.breakDownWall(maze.getCell(currentX, currentY), nextCell);
            opTracker.add(new KnockDownWall(currentX, currentY, nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // Make recursive call
            runRBT(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            // Track backtracking operation
            opTracker.add(new BackTrack(currentX, currentY));
        } while(true);
    }
}
