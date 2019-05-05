package app.logic;

import app.logic.Tracking.AddFrontier;
import app.logic.Tracking.KnockDownWall;
import app.logic.Tracking.OperationTracker;
import app.logic.domain.Cell;
import app.logic.domain.Maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class RandomAlgo {
    private OperationTracker opTracker;
    private Maze maze;
    private int currentX, currentY;
    private ArrayList<Cell> cells;
    private Stack<Cell> path;
    private ArrayList<Cell> frontiers;

    RandomAlgo(Maze maze) {
        opTracker = new OperationTracker();
        this.maze = maze;
        path = new Stack<>();
        frontiers = new ArrayList<>();
    }

    OperationTracker getOpTracker() {
        return opTracker;
    }

    void makeListOfCells() {
        cells = new ArrayList<>();
        for (int i = 0; i < maze.getDimX(); i++) {
            for (int j = 0; j < maze.getDimY(); j++) {
                cells.add(maze.getTile(i, j));
            }
        }
    }

    void runRandomAlgo() {
        int choice;
        makeListOfCells();
        currentX = (int) (Math.random() * maze.getDimX());
        currentY = (int) (Math.random() * maze.getDimY());

        // Mark the first cell as visited
        frontiers.add(maze.getTile(currentX, currentY));
        //System.out.println("Adding (" + currentX + ", " + currentY + ")");
        maze.setVisited(currentX, currentY);
        addFrontiers(currentX, currentY);
        // Remove from cells left and add to path
        path.push(maze.getTile(currentX, currentY));
        cells.remove(maze.getTile(currentX, currentY));

        while (!cells.isEmpty()) {
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

            /*for(int i = 0; i < maze.getDimX()/2; i++) {
                stepRecursiveBacktracking();
            }
            stepPrim();
            stepWilson();*/
        }
        frontiers.remove(maze.getTile(currentX, currentY));
        //System.out.println("Removing (" + currentX + ", " + currentY + ")");
    }

    private void stepRecursiveBacktracking() {
        // Remove cell from frontier list
        frontiers.remove(maze.getTile(currentX, currentY));
        //System.out.println("Removing (" + currentX + ", " + currentY + ")");

        // Find a neighbouring cell
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY);

        if (neighbours.isEmpty()) {
            //path.pop();
            Cell nextCell = path.pop();
            currentX = nextCell.getXCoordinate();
            currentY = nextCell.getYCoordinate();
            return;
        }
        Cell nextCell = Method.getRandom(neighbours);

        // Break down wall between the current and the next cell
        maze.breakDownWall(maze.getTile(currentX, currentY), nextCell);
        opTracker.add(new KnockDownWall(currentX, currentY, nextCell.getXCoordinate(), nextCell.getYCoordinate()));
        maze.setVisited(nextCell.getXCoordinate(), nextCell.getYCoordinate());

        // Add neighbours to the frontiers
        addFrontiers(nextCell.getXCoordinate(), nextCell.getYCoordinate());
        path.push(maze.getTile(nextCell.getXCoordinate(), nextCell.getYCoordinate()));
        cells.remove(nextCell);

        // Set next cell to be the current cell
        currentX = nextCell.getXCoordinate();
        currentY = nextCell.getYCoordinate();
    }

    private void stepPrim() {
        frontiers.remove(maze.getTile(currentX, currentY));
        //System.out.println("Removing (" + currentX + ", " + currentY + ")");

        // If there is no frontiers, return
        if(frontiers.isEmpty()) {
            return;
        }

        // Choose next cell and remove it from frontiers
        Cell nextCell = Method.getRandom(frontiers);
        frontiers.remove(nextCell);
        //System.out.println("Removing (" + nextCell.getXCoordinate() + ", " + nextCell.getYCoordinate() + ")");

        // Get a random cell which is visited and a neighbour to the chosen frontier
        ArrayList<Cell> neighbourCells = maze.getPossibleNeighboursToFrontier(nextCell.getXCoordinate(), nextCell.getYCoordinate());

        Cell neighbour = Method.getRandom(neighbourCells);

        // Break down wall and track the operation
        maze.breakDownWall(maze.getTile(neighbour.getXCoordinate(), neighbour.getYCoordinate()), nextCell);
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

    private void addFrontiers(int currentX, int currentY){
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY);
        for (Cell cell : neighbours){
            if (!frontiers.contains(cell)) {
                frontiers.add(cell);
                //System.out.println("Adding (" + cell.getXCoordinate() + ", " + cell.getYCoordinate() + ")");
            }
        }
    }

    private void stepWilson(){
        frontiers.remove(maze.getTile(currentX, currentY));
        //System.out.println("Removing (" + currentX + ", " + currentY + ")");
        if(cells.isEmpty())
            return;
        Cell startingCell = Method.getRandomPossibleCellInMaze(maze);

        // Perform walk and add it to the maze
        HashMap<Cell, Cell> walk = Method.performRandomWalk(startingCell, maze);

        maze.setVisited(startingCell.getXCoordinate(), startingCell.getYCoordinate());
        path.push(startingCell);
        cells.remove(maze.getTile(startingCell.getXCoordinate(), startingCell.getYCoordinate()));
        addFrontiers(startingCell.getXCoordinate(), startingCell.getYCoordinate());

        if(walk.isEmpty())
            return;

        addWalkToMaze(walk, startingCell);
    }

    private void addWalkToMaze(HashMap<Cell, Cell> walk, Cell startingCell) {
        Cell nextCell, currentCell = startingCell;
        do {
            frontiers.remove(currentCell);
                //System.out.println("Removing (" + currentCell.getXCoordinate() + ", " + currentCell.getYCoordinate() + ")");

            nextCell = walk.get(currentCell);

            maze.breakDownWall(currentCell, nextCell);
            opTracker.add(new KnockDownWall(currentCell.getXCoordinate(), currentCell.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));
            addFrontiers(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            if (nextCell.isVisited())
                break;

            maze.setVisited(nextCell.getXCoordinate(), nextCell.getYCoordinate());
            path.push(nextCell);
            cells.remove(maze.getTile(nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            currentCell = nextCell;
        } while (true);
        currentX = nextCell.getXCoordinate();
        currentY = nextCell.getYCoordinate();
    }
}