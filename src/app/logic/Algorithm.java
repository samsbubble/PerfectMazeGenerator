package app.logic;

import app.logic.algorithms.*;
import app.logic.tracking.*;
import app.logic.domain.Cell;
import app.logic.domain.Direction;
import app.logic.domain.Maze;
import app.logic.domain.SolutionException;

import java.util.ArrayList;

public class Algorithm {
    private int dimX, dimY;
    private OperationTracker operationTracker;
    private Maze maze;
    private ArrayList<Cell> mazeSolution;
    private Cell startingCell, endingCell;

    // Empty constructor for the algorithm class.
    public Algorithm(){}

    // Method setting up a initial maze and the elements the algorithm needs.
    public void generateMaze(int dimX, int dimY) {
        this.dimX = dimX;
        this.dimY = dimY;
        maze = new Maze(dimX, dimY);
        operationTracker = new OperationTracker();
        startingCell = new Cell(0,0);
        endingCell = new Cell(dimX-1, dimY-1);
    }

    // Method to run the specified algorithm with.
    public void runAlgorithm(String algo){
        // Generate a random starting point.
        int currentX = (int) (Math.random() * dimX);
        int currentY = (int) (Math.random() * dimY);

        // Choose the specified algorithm.
        switch (algo){
            case "Recursive Backtracking Algorithm":
                RecursiveBacktracking rec = new RecursiveBacktracking(maze);
                rec.runRBT(currentX, currentY);
                this.operationTracker = rec.getOpTracker();
                break;
            case "Prim's Algorithm":
                Prim prim = new Prim(maze);
                prim.runPrim(currentX, currentY);
                this.operationTracker = prim.getOpTracker();
                break;
            case "Wilson's Algorithm":
                Wilson wilson = new Wilson(maze);
                wilson.runWilson(currentX, currentY);
                this.operationTracker = wilson.getOpTracker();
                break;
            case "Random Choice":
                RandomAlgo randomAlgo = new RandomAlgo(maze);
                randomAlgo.runRandomAlgo();
                this.operationTracker = randomAlgo.getOpTracker();
                break;
            case "Bottom Up":
                BottomUpAlgo bottomUpAlgo = new BottomUpAlgo(maze);
                bottomUpAlgo.runBottomUpAlgo();
                this.operationTracker = bottomUpAlgo.getOpTracker();
                break;
            case "RB Bottom Up":
                RBBottomUp rbBottomUp = new RBBottomUp(maze);
                rbBottomUp.runRBBottomUp();
                this.operationTracker = rbBottomUp.getOperationTracker();
                break;
        }
    }

    // Method returning the operation tracker for the last algorithm run.
    public OperationTracker getOperationTracker(){
        return this.operationTracker;
    }


    // Recursive way of finding the solution.
    private boolean calculateSolutionToMaze(Cell prevCell, Cell curCell){
        // Add the current cell to the solution
        mazeSolution.add(maze.getCell(curCell.getXCoordinate(), curCell.getYCoordinate()));

        // Check if we are done
        if(curCell.getXCoordinate() == endingCell.getXCoordinate() && curCell.getYCoordinate() == endingCell.getYCoordinate()){
            return true;
        }

        // Get the possible paths out of the cell
        for (Cell next : maze.getPossiblePaths
                (
                        prevCell == null ? null : maze.getCell(prevCell.getXCoordinate(), prevCell.getYCoordinate()),
                        maze.getCell(curCell.getXCoordinate(), curCell.getYCoordinate())
                )
            )
        {
            // Make the recursive call with the next cell.
            if(calculateSolutionToMaze(maze.getCell(curCell.getXCoordinate(), curCell.getYCoordinate()), next))
                return true;
        }
        // Backtrack by removing the last cell in the solution.
        mazeSolution.remove(mazeSolution.size()-1);
        return false;
    }


    // Iterative way of finding the solution.
    /*private boolean calculateSolutionToMaze(Cell startingCell){

        Cell prevCell = null;
        Cell curCell = startingCell;
        ArrayList<Cell> possiblePaths = null;
        HashSet<Cell> deadCells = new HashSet<>();

        while(true) {
            //System.out.println(mazeSolution.size());
            mazeSolution.add(maze.getCell(curCell.getXCoordinate(), curCell.getYCoordinate()));

            if(curCell.equals(endingCell)) {
                break;
            }

            possiblePaths = maze.getPossiblePaths (
                    prevCell == null ? null : maze.getCell(prevCell.getXCoordinate(), prevCell.getYCoordinate()),
                    maze.getCell(curCell.getXCoordinate(), curCell.getYCoordinate())
            );

            boolean didBreak = false;
            for(Cell next : possiblePaths) {
                if(deadCells.contains(next)) {
                    continue;
                }

                prevCell = curCell;
                curCell = next;
                didBreak = true;
                break;
            }

            if(didBreak) {
                continue;
            }

            deadCells.add(mazeSolution.remove(mazeSolution.size()-1));
            curCell = mazeSolution.remove(mazeSolution.size()-1);

            prevCell = mazeSolution.isEmpty() ? null : mazeSolution.get(mazeSolution.size()-1);

        }
        return true;
    }*/

    // Method returning the solution as a list of cells.
    public ArrayList<Cell> getSolution() throws SolutionException {
        mazeSolution = new ArrayList<>();
        if (calculateSolutionToMaze(null, startingCell)) {
            return mazeSolution;
        } else
            throw new SolutionException("Error in solution");
    }

    // Calculates the number of dead ends - cells with only one opening - three walls
    public int getNumberOfDeadEnds() {
        int deadEnds = 0;
        Cell cell;

        for (int i = 0; i < maze.getDimX(); i++) {
            for (int j = 0; j < maze.getDimY(); j++) {
                cell = maze.getCell(i, j);
                if (cell.getNumberOfWalls() == 3)
                    deadEnds++;
            }
        }
        return deadEnds;
    }

    // Calculates the number of rivers - cells with three/four openings - one/no wall(s)
    public int getRiverFactor() {
        int riverFactor = 0;
        Cell cell;

        for (int i = 0; i < maze.getDimX(); i++) {
            for (int j = 0; j < maze.getDimY(); j++) {
                cell = maze.getCell(i, j);
                if (cell.getNumberOfWalls() == 1 || cell.getNumberOfWalls() == 0)
                    riverFactor++;
            }
        }
        return riverFactor;
    }

    // Calculates the length of the solution
    public int lengthOfSolution() {
        return mazeSolution.size();
    }

    // Calculates the number of turns in the solution
    public int getNumberOfTurnsInSolution() {
        // Direction  EAST/WEST <-> NORTH/SOUTH
        int turns = 0;
        Direction current, next;
        for (int i = 0; i < mazeSolution.size()-2; i++){
            current = getDirection(mazeSolution.get(i), mazeSolution.get(i+1));
            next = getDirection(mazeSolution.get(i+1), mazeSolution.get(i+2));

            if ((current.equals(Direction.EAST) || current.equals(Direction.WEST)) && (next.equals(Direction.NORTH) || next.equals(Direction.SOUTH)) ||
                (current.equals(Direction.SOUTH) || current.equals(Direction.NORTH)) && (next.equals(Direction.WEST) || next.equals(Direction.EAST))){
                turns++;
            }
        }

       return turns;
    }

    // Method determining, which direction was chosen going from the current to the next cell.
    private Direction getDirection(Cell currentCell, Cell nextCell) {
        if (currentCell.getXCoordinate()-1 == nextCell.getXCoordinate()){ // Check for WEST
            return Direction.WEST;
        } else if (currentCell.getXCoordinate()+1 == nextCell.getXCoordinate()){ // Check for EAST
            return Direction.EAST;
        } else if (currentCell.getYCoordinate()-1 == nextCell.getYCoordinate()){ // Check for NORTH
            return Direction.NORTH;
        } else { // Check for SOUTH
            return Direction.SOUTH;
        }
    }
}

