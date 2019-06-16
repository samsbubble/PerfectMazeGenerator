package app.logic.algorithms;

import app.logic.Algorithm;
import app.logic.tracking.KnockDownWall;
import app.logic.tracking.OperationTracker;
import app.logic.domain.Cell;
import app.logic.domain.Maze;
import app.logic.domain.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class RandomAlgo extends Algorithm {
    private OperationTracker opTracker;
    private Maze maze;
    private int currentX, currentY;
    private ArrayList<Cell> cells;
    private Stack<Cell> path;
    private ArrayList<Cell> frontiers;

    // Constructor of Randcom Choice algorithm. It initialises the maze, frontiers (Prim), path (RB), and the list of operations.
    public RandomAlgo(Maze maze) {
        opTracker = new OperationTracker();
        this.maze = maze;
        path = new Stack<>();
        frontiers = new ArrayList<>();
    }

    // Method returning the list of operations.
    public OperationTracker getOpTracker() {
        return opTracker;
    }

    // Puts all cells of the maze in a list
    private void makeListOfCells() {
        cells = new ArrayList<>();
        for (int i = 0; i < maze.getDimX(); i++) {
            for (int j = 0; j < maze.getDimY(); j++) {
                cells.add(maze.getCell(i, j));
            }
        }
    }

    // Method running Random Choice algorithm
    public void runRandomAlgo() {
        int choice;
        makeListOfCells();
        currentX = (int) (Math.random() * maze.getDimX());
        currentY = (int) (Math.random() * maze.getDimY());

        // Mark the first cell as visited and add the frontiers to the cell
        frontiers.add(maze.getCell(currentX, currentY));
        maze.setVisited(currentX, currentY);
        addFrontiers(currentX, currentY);

        // Remove from cells left and add to path
        path.push(maze.getCell(currentX, currentY));
        cells.remove(maze.getCell(currentX, currentY));

        // Loop continues until all cell are visited.
        while (!cells.isEmpty()) {
            // Randomly select the next algorithm
            choice = (int) (Math.random()*3);
            switch (choice){
                case 0:
                    for(int i = 0; i < maze.getDimX()/2; i++) {
                        stepRecursiveBacktracking();
                    }
                    break;
                case 1:
                    for(int i = 0; i < maze.getDimX()/2; i++) {
                        stepPrim();
                    }
                    break;
                case 2:
                    stepWilson();
                    break;
            }
        }
        // Remove the current cell from the frontier list.
        frontiers.remove(maze.getCell(currentX, currentY));
    }

    // Perform one iteration of the Recursive Backtracking algorithm.
    private void stepRecursiveBacktracking() {
        // Remove cell from frontier list
        frontiers.remove(maze.getCell(currentX, currentY));

        // Find a neighbouring cell
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY);

        // If there aren't any neighbours, pop the last cell, and set the current cell to the pop off cell.
        if (neighbours.isEmpty()) {
            Cell nextCell = path.pop();
            currentX = nextCell.getXCoordinate();
            currentY = nextCell.getYCoordinate();
            return;
        }
        // Get a random cell from the neighbours and set it as the next cell.
        Cell nextCell = Method.getRandom(neighbours);

        // Break down wall between the current and the next cell and track the operation.
        maze.breakDownWall(maze.getCell(currentX, currentY), nextCell);
        opTracker.add(new KnockDownWall(currentX, currentY, nextCell.getXCoordinate(), nextCell.getYCoordinate()));
        maze.setVisited(nextCell.getXCoordinate(), nextCell.getYCoordinate());

        // Add neighbours to the frontiers
        addFrontiers(nextCell.getXCoordinate(), nextCell.getYCoordinate());
        path.push(nextCell);
        cells.remove(nextCell);

        // Set next cell to be the current cell
        currentX = nextCell.getXCoordinate();
        currentY = nextCell.getYCoordinate();
    }

    // Perform one iteration of Prim's algorithm
    private void stepPrim() {
        // Remove the current cell from the list of frontiers.
        frontiers.remove(maze.getCell(currentX, currentY));

        // If there is no frontiers, return
        if(frontiers.isEmpty()) {
            return;
        }

        // Choose next cell and remove it from frontiers
        Cell nextCell = Method.getRandom(frontiers);
        frontiers.remove(nextCell);

        // Get a random cell, which is visited, and a neighbour to the chosen frontier
        ArrayList<Cell> neighbourCells = maze.getPossibleNeighboursToFrontier(nextCell.getXCoordinate(), nextCell.getYCoordinate());
        Cell neighbour = Method.getRandom(neighbourCells);

        // Break down wall and track the operation
        maze.breakDownWall(maze.getCell(neighbour.getXCoordinate(), neighbour.getYCoordinate()), nextCell);
        opTracker.add(new KnockDownWall(neighbour.getXCoordinate(), neighbour.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));
        maze.setVisited(nextCell.getXCoordinate(), nextCell.getYCoordinate());

        // Add neighbouring cells to frontiers
        addFrontiers(nextCell.getXCoordinate(), nextCell.getYCoordinate());
        path.push(nextCell);
        cells.remove(nextCell);

        // Set next cell to current cell
        currentX = nextCell.getXCoordinate();
        currentY = nextCell.getYCoordinate();

    }

    // Method for adding the legal neighbour of the given cell to the list of frontiers.
    private void addFrontiers(int currentX, int currentY){
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY);
        for (Cell cell : neighbours){
            if (!frontiers.contains(cell)) {
                frontiers.add(cell);
            }
        }
    }

    // Perform one iteration of Wilson's algorithm.
    private void stepWilson(){
        // Remove the current cell from the list of frontiers.
        frontiers.remove(maze.getCell(currentX, currentY));

        // If there are no more unvisited cells in the maze, return.
        if(cells.isEmpty())
            return;

        // Get a random starting cell in the maze.
        Cell startingCell = Method.getRandom(cells);

        // Perform the random walk
        HashMap<Cell, Cell> walk = Method.performRandomWalk(startingCell, maze);

        // Set the starting cell to visited and add its legal neighbours to the list of frontiers.
        maze.setVisited(startingCell.getXCoordinate(), startingCell.getYCoordinate());
        path.push(startingCell);
        cells.remove(startingCell);
        addFrontiers(startingCell.getXCoordinate(), startingCell.getYCoordinate());

        // Add the walk to the maze.
        addWalkToMaze(walk, startingCell);
    }

    // Method for adding the walk to the maze.
    private void addWalkToMaze(HashMap<Cell, Cell> walk, Cell startingCell) {
        Cell nextCell, currentCell = startingCell;
        do {
            // Remove the current cell from the list of frontiers.
            frontiers.remove(currentCell);

            // Get the next cell from the walk
            nextCell = walk.get(currentCell);

            // Break down the wall between the current and next cell, track the operation and add the frontiers to the next cell.
            maze.breakDownWall(currentCell, nextCell);
            opTracker.add(new KnockDownWall(currentCell.getXCoordinate(), currentCell.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));
            addFrontiers(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            // If the next cell is already visited, break the loop.
            if (nextCell.isVisited())
                break;

            // Set the cell to visited
            maze.setVisited(nextCell.getXCoordinate(), nextCell.getYCoordinate());
            path.push(nextCell);
            cells.remove(nextCell);

            // Set the current cell equal to the next cell.
            currentCell = nextCell;
        } while (true);
        // Set the current x and y coordinates equal to the next cells coordinates.
        currentX = nextCell.getXCoordinate();
        currentY = nextCell.getYCoordinate();
    }
}