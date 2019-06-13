package app.logic.algorithms;

import app.logic.Algorithm;
import app.logic.tracking.KnockDownWall;
import app.logic.tracking.OperationTracker;
import app.logic.domain.Cell;
import app.logic.domain.Maze;
import app.logic.domain.Method;

import java.util.ArrayList;
import java.util.HashMap;

public class BottomUpAlgo extends Algorithm {
    // This algorithm takes a random walk from the starting point to the ending point of the maze to create the solution,
    // and afterwards build the maze around the solution with Prim's algorithm.
    private OperationTracker opTracker;
    private Maze maze;
    private int currentX, currentY;
    private ArrayList<Cell> frontiers;

    // Constructor setting up the maze, the operation tracker and the list of frontiers.
    public BottomUpAlgo(Maze maze){
        this.maze = maze;
        opTracker = new OperationTracker();
        frontiers = new ArrayList<>();
        currentX = 0;
        currentY = 0;
    }

    // Method to run the algorithm
    public void runBottomUpAlgo(){
        ArrayList<Cell> neighbourCells;
        // Set the ending cell as visited.
        Cell start = new Cell(currentX, currentY);
        Cell nextCell, neighbour;
        maze.setVisited(maze.getDimX()-1, maze.getDimY()-1);

        // Do random walk to create the solution.
        HashMap<Cell, Cell> walk = Method.performRandomWalk(start, maze);

        // Add it too the maze and save all the frontiers of the walk.
        addWalkToMaze(walk, start);

        // Run Prim's algorithm to finish the maze.
        do{
            // Track visited
            if(!maze.getCell(currentX, currentY).isVisited())
                maze.setVisited(currentX, currentY);

            // Add neighbours of the current cell to the frontiers.
            addFrontiers(currentX, currentY);

            // Break the loop, when there aren't any more frontiers, since the maze is then complete.
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
        } while(true);
        frontiers = null;
    }

    // Method returning the list of operations.
    public OperationTracker getOpTracker() {
        return opTracker;
    }

    // Add the walk to the maze while adding the legal neighbours of the walk to the list of frontiers
    private void addWalkToMaze(HashMap<Cell, Cell> walk, Cell startingCell) {
        Cell nextCell;
        Cell currentCell = startingCell;
        // Visit the first cell and add frontiers
        maze.setVisited(currentCell.getXCoordinate(), currentCell.getYCoordinate());
        addFrontiers(currentCell.getXCoordinate(), currentCell.getYCoordinate());
        do {
            // Remove the cell from the frontier list.
            frontiers.remove(currentCell);

            // Get the next cell from the walk
            nextCell = walk.get(currentCell);

            // Break down the wall between the current and next cell and track the operation.
            maze.breakDownWall(currentCell, nextCell);
            opTracker.add(new KnockDownWall(currentCell.getXCoordinate(), currentCell.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // Add the frontiers of the next cell.
            addFrontiers(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            // If the next cell is already visited, break the loop.
            if (nextCell.isVisited())
                break;

            // Set the cell to visited and set the current cell equal to the next cell.
            maze.setVisited(nextCell.getXCoordinate(), nextCell.getYCoordinate());
            currentCell = nextCell;
        } while (true);
        // Set the current x and y coordinate to the last found cell in the walk.
        currentX = nextCell.getXCoordinate();
        currentY = nextCell.getYCoordinate();
    }

    // Method for adding the frontiers of a cell
    private void addFrontiers(int currentX, int currentY){
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY); // Get only the legal neighbours.
        for (Cell cell : neighbours){
            // Add only cells, which are not already in the list of frontiers.
            if (!frontiers.contains(maze.getCell(cell.getXCoordinate(), cell.getYCoordinate()))){
                frontiers.add(maze.getCell(cell.getXCoordinate(), cell.getYCoordinate()));
            }
        }
    }
}
