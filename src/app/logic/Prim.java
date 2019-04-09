package app.logic;

import app.logic.Tracking.BackTrack;
import app.logic.Tracking.KnockDownWall;
import app.logic.Tracking.Move;
import app.logic.Tracking.OperationTracker;
import app.logic.domain.Cell;
import app.logic.domain.Maze;

import java.util.ArrayList;

public class Prim extends Algorithm{
    private OperationTracker opTracker;
    private Maze maze;
    private  ArrayList<Cell> frontiers;


    Prim(Maze maze){
        this.maze = maze;
        frontiers = new ArrayList<>();
        opTracker = new OperationTracker();
    }

    OperationTracker getOpTracker(){
        return opTracker;
    }

    void runPrim(int currentX, int currentY){
        Cell nextCell;
        opTracker.add(new Move(currentX, currentY));
        do{
            // Track move, using "backtracking" operation to remove marking of the cell
            opTracker.add(new BackTrack(currentX, currentY));
            // Track visited
            maze.setVisited(currentX, currentY);
            //opTracker.add(new Visited());

            // Add neighbours of the current cell to the frontiers.
            addFrontiers(currentX, currentY);

            if(frontiers.isEmpty())
                break;

            // Choose next cell and remove it from frontiers
            nextCell = getRandom(frontiers);
            frontiers.remove(nextCell);

            // Break down wall and track the operation
            maze.breakDownWall(maze.getTile(currentX, currentY), nextCell);
            opTracker.add(new KnockDownWall(currentX, currentY, nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // Set next cell to current cell
            currentX = nextCell.getXCoordinate();
            currentY = nextCell.getYCoordinate();
            } while(true);
    }


    private void addFrontiers(int currentX, int currentY){
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY);

        for (Cell cell : neighbours){
            if (!frontiers.contains(cell)) {
                frontiers.add(cell);
                // Track the frontiers
                opTracker.add(new Move(cell.getXCoordinate(), cell.getYCoordinate()));
            }
        }
    }

    private Cell getRandom(ArrayList<Cell> neighbours){
        if(neighbours.size() == 1){
            return neighbours.get(0);
        }
        int index = (int) (Math.random() * neighbours.size());
        return neighbours.get(index);
    }


}
