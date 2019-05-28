package app.logic;

import app.logic.Tracking.*;
import app.logic.domain.Cell;
import app.logic.domain.Maze;

import java.util.ArrayList;

class Prim extends Algorithm{
    private OperationTracker opTracker;
    private Maze maze;
    private ArrayList<Cell> frontiers;

    Prim(Maze maze){
        this.maze = maze;
        frontiers = new ArrayList<>();
        opTracker = new OperationTracker();
    }

    OperationTracker getOpTracker(){
        return opTracker;
    }

    void runPrim(int currentX, int currentY){
        Cell nextCell, neighbour;
        ArrayList<Cell> neighbourCells;
        do{
            // Track visited
            maze.setVisited(currentX, currentY);
            //opTracker.add(new Visited());

            // Add neighbours of the current cell to the frontiers.
            addFrontiers(currentX, currentY);

            if(frontiers.isEmpty())
                break;

            // Choose next cell and remove it from frontiers
            nextCell = Method.getRandom(frontiers);
            frontiers.remove(nextCell);

            // Get a random cell which is visited and a neighbour to the chosen frontier
            neighbourCells = maze.getPossibleNeighboursToFrontier(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            neighbour = Method.getRandom(neighbourCells);

            // Break down wall and track the operation
            maze.breakDownWall(maze.getTile(neighbour.getXCoordinate(), neighbour.getYCoordinate()), nextCell);
            opTracker.add(new KnockDownWall(neighbour.getXCoordinate(), neighbour.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // Set next cell to current cell
            currentX = nextCell.getXCoordinate();
            currentY = nextCell.getYCoordinate();

            // Unmark the chosen cell
            opTracker.add(new UnMark(currentX, currentY));
            } while(true);
        frontiers = null;
        maze = null;
        System.gc();
    }

    private void addFrontiers(int currentX, int currentY){
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY);

        for (Cell cell : neighbours){
            if (!frontiers.contains(cell)) {
                frontiers.add(cell);
                // Track the frontiers
                opTracker.add(new AddFrontier(cell.getXCoordinate(), cell.getYCoordinate()));
            }
        }
    }
}
