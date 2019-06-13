package app.logic.algorithms;

import app.logic.Algorithm;
import app.logic.tracking.KnockDownWall;
import app.logic.tracking.OperationTracker;
import app.logic.domain.Cell;
import app.logic.domain.Maze;
import app.logic.domain.Method;

import java.util.ArrayList;

public class RBBottomUp extends Algorithm {
    private Maze maze;
    private OperationTracker opTracker;
    private ArrayList<Cell> frontiers;
    private int currentX, currentY;
    private ArrayList<Cell> rbSolution;
    private Cell endingCell, startingCell;

    // Constructor for the RB Bottom Up algorithm. It initialises the memory it needs.
    public RBBottomUp(Maze maze){
        this.maze = maze;
        opTracker = new OperationTracker();
        frontiers = new ArrayList<>();
        rbSolution = new ArrayList<>();
        currentX = (int) (Math.random()*maze.getDimX());
        currentY = (int) (Math.random()*maze.getDimY());
        startingCell = maze.getCell(0,0);
        endingCell = maze.getCell(maze.getDimX()-1, maze.getDimY()-1);
    }

    // Returns the list of operations.
    public OperationTracker getOperationTracker() {
        return opTracker;
    }

    // Runs the RB Bottom Up algorithm on the maze.
    public void runRBBottomUp(){
        ArrayList<Cell> neighbourCells;
        Cell nextCell, neighbour;

        // Gets the solution path
        ArrayList<Cell> solutionPath = getSolutionPath();
        if(solutionPath == null){
            return;
        }

        // Adds the solution to the maze.
        addSolutionToMaze(solutionPath);

        // Run Prim's algorithm to finish the maze.
        do{
            // Set cell to visited if it is not already visited.
            if(!maze.getCell(currentX, currentY).isVisited())
                maze.setVisited(currentX, currentY);

            // Add neighbours of the current cell to the frontiers.
            addFrontiers(currentX, currentY);

            // If there aren't any frontiers left, break the loop
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
        maze = null;
        frontiers = null;
        rbSolution = null;
    }

    // Generate a maze using the Recursive Backtracking algorithm and get the solution
    private ArrayList<Cell> getSolutionPath() {
        Maze mazeSolution = new Maze(maze.getDimX(), maze.getDimY());
        RecursiveBacktracking recursiveBacktracking = new RecursiveBacktracking(mazeSolution);
        recursiveBacktracking.runRBT(currentX, currentY);

        if(calculateSolution(null, startingCell, mazeSolution))
            return rbSolution;

        return null;
    }

    // Find the solution to the maze recursively using depth first search.
    private boolean calculateSolution(Cell prevCell, Cell curCell, Maze maze){
        // Add the current cell to the solution
        rbSolution.add(maze.getCell(curCell.getXCoordinate(), curCell.getYCoordinate()));

        // Check if we are done
        if(curCell.getXCoordinate() == endingCell.getXCoordinate() && curCell.getYCoordinate() == endingCell.getYCoordinate()){
            return true;
        }
        // Find all the neighbouring cells to which it is possible to go.
        for (Cell next : maze.getPossiblePaths
                (
                        prevCell == null ? null : maze.getCell(prevCell.getXCoordinate(), prevCell.getYCoordinate()),
                        maze.getCell(curCell.getXCoordinate(), curCell.getYCoordinate())
                )
        )
        {   // Call the method recursively.
            if(calculateSolution(maze.getCell(curCell.getXCoordinate(), curCell.getYCoordinate()), next, maze))
                return true;
        }
        // Backtrack by removing the last cell in the solution.
        rbSolution.remove(rbSolution.size()-1);
        return false;
    }

    // Add solution to the maze
    private void addSolutionToMaze(ArrayList<Cell> solutionPath) {
        // Get the first cell, add the frontiers of the cell and set it to visited.
        Cell currentCell = solutionPath.get(0), nextCell;
        addFrontiers(currentCell.getXCoordinate(), currentCell.getYCoordinate());
        maze.setVisited(currentCell.getXCoordinate(), currentCell.getYCoordinate());

        int index = 1;
        // For all cells in the solution
        while(index < solutionPath.size()){
            // Remove the cell from the frontier list
            frontiers.remove(maze.getCell(currentCell.getXCoordinate(), currentCell.getYCoordinate()));

            // Get the next cell
            nextCell = solutionPath.get(index);

            // Break down the wall between the current cell and the next cell, and track the operation.
            maze.breakDownWall(maze.getCell(currentCell.getXCoordinate(), currentCell.getYCoordinate()), maze.getCell(nextCell.getXCoordinate(), nextCell.getYCoordinate()));
            opTracker.add(new KnockDownWall(currentCell.getXCoordinate(), currentCell.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // Add the legal neighbours of the next cell to the list of frontiers and set it to visited.
            addFrontiers(nextCell.getXCoordinate(), nextCell.getYCoordinate());
            maze.setVisited(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            // Set the current cell equal to the next cell and update the index.
            currentCell = nextCell;
            index++;
        }
        // Remove the current cell from the frontier list and update the coordinates for the current cell.
        frontiers.remove(maze.getCell(currentCell.getXCoordinate(), currentCell.getYCoordinate()));
        currentX = currentCell.getXCoordinate();
        currentY = currentCell.getYCoordinate();
    }

    // Add the legal neighbours to the list of frontiers.
    private void addFrontiers(int currentX, int currentY){
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY);
        for (Cell cell : neighbours){
            if (!frontiers.contains(cell)) {
                frontiers.add(cell);
            }
        }
    }
}
