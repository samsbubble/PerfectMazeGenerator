package app.logic;

import app.logic.Tracking.KnockDownWall;
import app.logic.Tracking.OperationTracker;
import app.logic.domain.Cell;
import app.logic.domain.Maze;

import java.util.ArrayList;

public class RBBottomUp extends Algorithm {
    private Maze maze;
    private OperationTracker opTracker;
    private ArrayList<Cell> frontiers;
    private int currentX, currentY;
    private ArrayList<Cell> rbSolution;
    private Cell endingCell, startingCell;

    public RBBottomUp(Maze maze){
        this.maze = maze;
        opTracker = new OperationTracker();
        frontiers = new ArrayList<>();
        rbSolution = new ArrayList<>();
        currentX = (int) (Math.random()*maze.getDimX());
        currentY = (int) (Math.random()*maze.getDimY());
        startingCell = maze.getTile(0,0);
        endingCell = maze.getTile(maze.getDimX()-1, maze.getDimY()-1);
    }

    @Override
    public OperationTracker getOperationTracker() {
        return opTracker;
    }

    void runRBBottomUp(){
        ArrayList<Cell> neighbourCells;
        Cell nextCell, neighbour;

        ArrayList<Cell> solutionPath = getSolutionPath();
        if(solutionPath == null){
            return;
        }

        addSolutionToMaze(solutionPath);

        // Run Prim's algorithm to finish the maze.
        do{
            // Track visited
            if(!maze.getTile(currentX, currentY).isVisited())
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
            maze.breakDownWall(maze.getTile(neighbour.getXCoordinate(), neighbour.getYCoordinate()), nextCell);
            opTracker.add(new KnockDownWall(neighbour.getXCoordinate(), neighbour.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // Set next cell to current cell
            currentX = nextCell.getXCoordinate();
            currentY = nextCell.getYCoordinate();
        } while(true);
        maze = null;
        frontiers = null;
        System.gc();
    }

    // Generate RB and get the solution
    private ArrayList<Cell> getSolutionPath() {
        Maze mazeSolution = new Maze(maze.getDimX(), maze.getDimY());
        RecursiveBacktracking recursiveBacktracking = new RecursiveBacktracking(mazeSolution);
        recursiveBacktracking.runRBT(currentX, currentY);

        if(calculateSolution(null, startingCell, mazeSolution))
            return rbSolution;

        return null;
    }


    private boolean calculateSolution(Cell prevCell, Cell curCell, Maze maze){
        rbSolution.add(maze.getTile(curCell.getXCoordinate(), curCell.getYCoordinate()));
        //System.out.println("Adding " + "(" + curCell.getXCoordinate() +", " + curCell.getYCoordinate() + ")");

        // Check if we are done
        if(curCell.getXCoordinate() == endingCell.getXCoordinate() && curCell.getYCoordinate() == endingCell.getYCoordinate()){
            return true;
        }

        for (Cell next : maze.getPossiblePaths
                (
                        prevCell == null ? null : maze.getTile(prevCell.getXCoordinate(), prevCell.getYCoordinate()),
                        maze.getTile(curCell.getXCoordinate(), curCell.getYCoordinate())
                )
        )
        {
            if(calculateSolution(maze.getTile(curCell.getXCoordinate(), curCell.getYCoordinate()), next, maze))
                return true;
        }

        rbSolution.remove(rbSolution.size()-1);
        //System.out.println("Removing " + "(" + curCell.getXCoordinate() +", " + curCell.getYCoordinate() + ")");
        return false;
    }



    // Add solution to the maze, run Prim
    private void addSolutionToMaze(ArrayList<Cell> solutionPath) {
        // Get the first cell and add the frontiers
        Cell currentCell = solutionPath.get(0), nextCell = new Cell(0,0);
        addFrontiers(currentCell.getXCoordinate(), currentCell.getYCoordinate());
        maze.setVisited(currentCell.getXCoordinate(), currentCell.getYCoordinate());

        int index = 1;

        while(index < solutionPath.size()){
            frontiers.remove(maze.getTile(currentCell.getXCoordinate(), currentCell.getYCoordinate()));
            nextCell = solutionPath.get(index);

            maze.breakDownWall(maze.getTile(currentCell.getXCoordinate(), currentCell.getYCoordinate()), maze.getTile(nextCell.getXCoordinate(), nextCell.getYCoordinate()));
            opTracker.add(new KnockDownWall(currentCell.getXCoordinate(), currentCell.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));
            addFrontiers(nextCell.getXCoordinate(), nextCell.getYCoordinate());
            maze.setVisited(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            currentCell = nextCell;
            index++;

        }
        frontiers.remove(maze.getTile(currentCell.getXCoordinate(), currentCell.getYCoordinate()));
        currentX = currentCell.getXCoordinate();
        currentY = currentCell.getYCoordinate();
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




}
