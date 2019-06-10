package app.logic;

import app.logic.Tracking.*;
import app.logic.domain.Maze;
import app.logic.domain.Cell;

import java.util.ArrayList;


public class RecursiveBacktracking extends Algorithm {
    private OperationTracker opTracker;
    private Maze maze;

    RecursiveBacktracking(Maze maze){
        opTracker = new OperationTracker();
        this.maze = maze;
    }

    OperationTracker getOpTracker(){
        return opTracker;
    }

    void runRBT(int currentX, int currentY){
        // Track move
        opTracker.add(new Move(currentX, currentY));

        // Track visited
        maze.setVisited(currentX, currentY);
        //opTracker.add(new Visited());

        Cell nextCell;
        ArrayList<Cell> neighbours;
        do{
            neighbours = maze.getPossibleNeighbours(currentX, currentY);
            if(neighbours.isEmpty())
                break;
            if(neighbours.size() == 0)
                break;

            nextCell = Method.getRandom(neighbours);
            // Break down wall and track the operation
            maze.breakDownWall(maze.getCell(currentX, currentY), nextCell);
            opTracker.add(new KnockDownWall(currentX, currentY, nextCell.getXCoordinate(), nextCell.getYCoordinate()));
            // Make recursive call
            runRBT(nextCell.getXCoordinate(), nextCell.getYCoordinate());
            // Track backtracking operation
            opTracker.add(new BackTrack(currentX, currentY));
        } while(true);
        // System.gc();
    }
}
