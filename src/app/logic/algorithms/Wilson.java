package app.logic.algorithms;

import app.logic.tracking.KnockDownWall;
import app.logic.tracking.OperationTracker;
import app.logic.domain.Cell;
import app.logic.domain.Maze;
import app.logic.domain.Method;

import java.util.HashMap;


public class Wilson {

    private OperationTracker opTracker;
    private Maze maze;
    private int remainingCells;

    // Constructor for Wilson's algorithm, which initialises the maze, the number of remaining cells and the list of operations.
    public Wilson(Maze maze){
        this.maze = maze;
        opTracker = new OperationTracker();
        remainingCells = maze.getDimX()*maze.getDimY();
    }

    // Method, which return the list of operations.
    public OperationTracker getOpTracker(){
        return opTracker;
    }

    // Method for running Wilson's algorithm on the maze.
    public void runWilson(int currentX, int currentY){
        Cell startingCell;
        HashMap<Cell, Cell> walk;

        // Set the current cell to visited and count down the number of remaining cells.
        maze.setVisited(currentX, currentY);
        remainingCells--;

        // While there are still remaining cells left in the maze.
        while (remainingCells > 0) {
            // Find a random legal cell in the maze.
            startingCell = Method.getRandomPossibleCellInMaze(maze);

            // Perform a random walk from the cell until reaching a already visited cell.
            walk = Method.performRandomWalk(startingCell, maze);

            // Add the walk to the maze.
            addWalkToMaze(walk, startingCell);
        }
        maze = null;
    }

    // Method for adding the walk to the maze.
    private void addWalkToMaze(HashMap<Cell, Cell> walk, Cell startingCell) {
        Cell nextCell, currentCell;
        currentCell = startingCell;
        do {
            // Count down the remaining cells and set the current cell to visited.
            remainingCells--;
            maze.setVisited(currentCell.getXCoordinate(), currentCell.getYCoordinate());

            // Get the next cell from the walk.
            nextCell = walk.get(currentCell);

            // Break down the wall between the current and next cell, and track the operation.
            maze.breakDownWall(currentCell, nextCell);
            opTracker.add(new KnockDownWall(currentCell.getXCoordinate(), currentCell.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // If the next cell is visited, then break the loop.
            if (nextCell.isVisited())
                break;

            // Set the current cell equal to the next cell.
            currentCell = nextCell;
        } while (true);
    }
}
