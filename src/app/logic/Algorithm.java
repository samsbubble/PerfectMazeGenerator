package app.logic;

import app.logic.Tracking.*;
import app.logic.domain.Cell;
import app.logic.domain.Direction;
import app.logic.domain.Maze;
import java.util.ArrayList;

public class Algorithm {
    private int dimX, dimY;
    private OperationTracker operationTracker;
    private Maze maze;
    private ArrayList<Cell> mazeSolution;
    private Cell startingCell, endingCell;

    public Algorithm(){}

    public void generateMaze(int dimX, int dimY) {
        this.dimX = dimX;
        this.dimY = dimY;
        maze = new Maze(dimX, dimY);
        operationTracker = new OperationTracker();
        startingCell = new Cell(0,0);
        endingCell = new Cell(dimX-1, dimY-1);
    }

    public void runAlgorithm(String algo){
        int currentX = (int) (Math.random() * dimX);
        int currentY = (int) (Math.random() * dimY);

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
            case "Random Algo":
                RandomAlgo randomAlgo = new RandomAlgo(maze);
                randomAlgo.runRandomAlgo();
                this.operationTracker = randomAlgo.getOpTracker();
                break;
            case "Bottom up Algo":
                BottomUpAlgo bottomUpAlgo = new BottomUpAlgo(maze);
                bottomUpAlgo.runBottomUpAlgo();
                this.operationTracker = bottomUpAlgo.getOpTracker();
                break;
            case "RB Bottom Up Algo":
                RBBottomUp rbBottomUp = new RBBottomUp(maze);
                rbBottomUp.runRBBottomUp();
                this.operationTracker = rbBottomUp.getOperationTracker();
                break;
        }
    }

    public OperationTracker getOperationTracker(){
        return this.operationTracker;
    }


    private boolean calculateSolutionToMaze(Cell prevCell, Cell curCell){
        mazeSolution.add(maze.getTile(curCell.getXCoordinate(), curCell.getYCoordinate()));
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
            if(calculateSolutionToMaze(maze.getTile(curCell.getXCoordinate(), curCell.getYCoordinate()), next))
                return true;
        }

        mazeSolution.remove(mazeSolution.size()-1);
        //System.out.println("Removing " + "(" + curCell.getXCoordinate() +", " + curCell.getYCoordinate() + ")");
        return false;
    }


    public ArrayList<Cell> getSolution() throws SolutionException {
        mazeSolution = new ArrayList<>();
        if (calculateSolutionToMaze(null, startingCell)) {
            //System.out.println(mazeSolution.size());
            return mazeSolution;
        } else
            throw new SolutionException("Error in solution");
    }

    public int getNumberOfDeadEnds() {
        // Calculate the number of dead ends - cells with only one opening - three walls
        int deadEnds = 0;
        Cell cell;

        for (int i = 0; i < maze.getDimX(); i++) {
            for (int j = 0; j < maze.getDimY(); j++) {
                cell = maze.getTile(i, j);
                if (cell.getNumberOfWalls() == 3)
                    deadEnds++;
            }
        }
        return deadEnds;
    }

    public int getRiverFactor() {
        // Calculate the number of rivers - cells with three openings - one wall
        int riverFactor = 0;
        Cell cell;

        for (int i = 0; i < maze.getDimX(); i++) {
            for (int j = 0; j < maze.getDimY(); j++) {
                cell = maze.getTile(i, j);
                if (cell.getNumberOfWalls() == 1)
                    riverFactor++;
            }
        }
        return riverFactor;
    }

    public int lengthOfSolution() {
        // Calculate the length of the solution
        //System.out.println(mazeSolution.size());
        return mazeSolution.size();
    }

    public int getNumberOfTurnsInSolution() {
        // Calculate the number of turns in the solution
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

