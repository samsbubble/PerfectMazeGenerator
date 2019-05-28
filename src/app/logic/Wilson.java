package app.logic;

import app.logic.Tracking.KnockDownWall;
import app.logic.Tracking.OperationTracker;
import app.logic.domain.Cell;
import app.logic.domain.Maze;

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
        maze.setVisited(currentX, currentY);
        remainingCells--;
        while (remainingCells > 0) {
            startingCell = Method.getRandomPossibleCellInMaze(maze);

            walk = Method.performRandomWalk(startingCell, maze);

            addWalkToMaze(walk, startingCell);
        }
        System.gc();
    }

    private void addWalkToMaze(HashMap<Cell, Cell> walk, Cell startingCell) {
        Cell nextCell, currentCell;
        currentCell = startingCell;
        do {
            remainingCells--;
            maze.setVisited(currentCell.getXCoordinate(), currentCell.getYCoordinate());
            nextCell = walk.get(currentCell);

            maze.breakDownWall(currentCell, nextCell);
            opTracker.add(new KnockDownWall(currentCell.getXCoordinate(), currentCell.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            if (nextCell.isVisited())
                break;

            currentCell = nextCell;
        } while (true);
    }


}
