package app.logic;

import app.logic.Tracking.KnockDownWall;
import app.logic.Tracking.OperationTracker;
import app.logic.domain.Cell;
import app.logic.domain.Maze;

import java.util.ArrayList;
import java.util.HashMap;


class Wilson {

    private OperationTracker opTracker;
    private Maze maze;
    private int remainingCells;


    Wilson(Maze maze){
        this.maze = maze;
        opTracker = new OperationTracker();
        remainingCells = maze.getDimX()*maze.getDimY();
    }

    OperationTracker getOpTracker(){
        return opTracker;
    }


    void runWilson(int currentX, int currentY){
        Cell startingCell;
        HashMap<Cell, Cell> walk;
        Cell currentCell = maze.getTile(currentX, currentY);
        currentCell.setVisited();
        remainingCells--;
        while (remainingCells > 0) {
            startingCell = Method.getRandomPossibleCellInMaze(maze);

            walk = performRandomWalk(startingCell);

            addWalkToMaze(walk, startingCell);
        }
    }

    private void addWalkToMaze(HashMap<Cell, Cell> walk, Cell startingCell) {
        Cell nextCell, currentCell;
        currentCell = startingCell;
        do {
            remainingCells--;
            currentCell.setVisited();
            nextCell = walk.get(currentCell);

            maze.breakDownWall(currentCell, nextCell);
            opTracker.add(new KnockDownWall(currentCell.getXCoordinate(), currentCell.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            if (nextCell.isVisited())
                break;

            currentCell = nextCell;
        } while (true);
    }

    private HashMap<Cell, Cell> performRandomWalk(Cell startingCell) {
        HashMap<Cell, Cell> walk = new HashMap<>();
        Cell currentCell = startingCell;
        Cell nextCell;
        ArrayList<Cell> neighbours;
        do {
            neighbours = maze.getAllNeighbours(currentCell.getXCoordinate(), currentCell.getYCoordinate());
            nextCell = Method.getRandom(neighbours);

            walk.put(currentCell, nextCell);

            if (nextCell.isVisited())
                break;

            currentCell = nextCell;
        } while(true);
        return walk;
    }


}
