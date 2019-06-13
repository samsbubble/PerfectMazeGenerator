package app.logic.algorithms;

import app.logic.Algorithm;
import app.logic.tracking.*;
import app.logic.domain.Cell;
import app.logic.domain.Maze;
import app.logic.domain.Method;

import java.util.ArrayList;

public class Prim extends Algorithm {
    private OperationTracker opTracker;
    private Maze maze;
    private ArrayList<Cell> frontiers;

    // Constructor of Prim, which initialises the maze, list of frontiers and the list of operations.
    public Prim(Maze maze){
        this.maze = maze;
        frontiers = new ArrayList<>();
        opTracker = new OperationTracker();
    }

    // Method returning the list of operations.
    public OperationTracker getOpTracker(){
        return opTracker;
    }

    // Method running Prim's algorithm on the maze.
    public void runPrim(int currentX, int currentY){
        Cell nextCell, neighbour;
        ArrayList<Cell> neighbourCells;
        do{
            // Track visited
            maze.setVisited(currentX, currentY);

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
            maze.breakDownWall(maze.getCell(neighbour.getXCoordinate(), neighbour.getYCoordinate()), nextCell);
            opTracker.add(new KnockDownWall(neighbour.getXCoordinate(), neighbour.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // Set next cell to current cell
            currentX = nextCell.getXCoordinate();
            currentY = nextCell.getYCoordinate();

            // Unmark the chosen cell
            opTracker.add(new UnMark(currentX, currentY));
            } while(true);
        frontiers = null;
        maze = null;
    }

    private void addFrontiers(int currentX, int currentY){
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY); // Get legal neighbours
        for (Cell cell : neighbours){
            if (!frontiers.contains(cell)) {
                // Add and track the frontiers
                frontiers.add(cell);
                opTracker.add(new AddFrontier(cell.getXCoordinate(), cell.getYCoordinate()));
            }
        }
    }
}
