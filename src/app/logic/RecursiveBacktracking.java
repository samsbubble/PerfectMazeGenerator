package app.logic;

import app.logic.Tracking.*;
import app.logic.domain.Maze;
import app.logic.domain.Cell;

import java.util.ArrayList;


public class RecursiveBacktracking extends Algorithm {
    int dimX, dimY;
    public OperationTracker opTracker;
    Maze maze;

    public RecursiveBacktracking(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;
        opTracker = new OperationTracker();
        maze = new Maze(dimX, dimY);
    }

    public void generateMaze() {
        int currentX = (int) (Math.random() * dimX);
        int currentY = (int) (Math.random() * dimY);
        runRBT(currentX, currentY);
    }

    public OperationTracker getOpTracker(){
        return opTracker;
    }

    public void runRBT(int currentX, int currentY){
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
            nextCell = getRandom(neighbours);
            // Break down wall and track the operation
            maze.breakDownWall(maze.getTile(currentX, currentY), nextCell);
            opTracker.add(new KnockDownWall(currentX, currentY, nextCell.getXCoordinate(), nextCell.getYCoordinate()));
            // Make recursive call
            runRBT(nextCell.getXCoordinate(), nextCell.getYCoordinate());
            // Track backtracking operation
            opTracker.add(new BackTrack(currentX, currentY));
        } while(true);
    }

    Cell getRandom(ArrayList<Cell> neighbours){
        if(neighbours.size() == 1){
            return neighbours.get(0);
        }
        int index = (int) (Math.random() * neighbours.size());
        return neighbours.get(index);
    }

}
