package app.algorithms;

import app.algorithms.Tracking.*;

import java.util.ArrayList;

public class RecursiveBacktracking {

    private int dimX, dimY;
    private Maze maze;
    public OperationTracker opTracker = new OperationTracker();


    public RecursiveBacktracking(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;
        maze = new Maze(dimX, dimY);
    }

    public void generateMaze() {
        int currentX = (int) (Math.random() * dimX);
        int currentY = (int) (Math.random() * dimY);
        generateMazeRBT(currentX, currentY);
    }

    public void generateMazeRBT(int currentX, int currentY){
        // Track move
        opTracker.add(new Move(currentX, currentY));

        // Track visited
        maze.setVisited(currentX, currentY);
        opTracker.add(new Visited());

        Tile nextCell;
        ArrayList<Tile> neighbours;
        do{
            neighbours = maze.getPossibleNeighbours(currentX, currentY);
            if(neighbours.isEmpty())
                break;
            nextCell = getRandom(neighbours);
            // Break down wall and track the operation
            maze.breakDownWall(maze.getTile(currentX, currentY), nextCell);
            opTracker.add(new KnockDownWall(currentX, currentY, nextCell.getXCoordinate(), nextCell.getYCoordinate()));
            // Make recursive call
            generateMazeRBT(nextCell.getXCoordinate(), nextCell.getYCoordinate());
            // Track backtracking operation
            opTracker.add(new BackTrack(currentX, currentY));
        } while(true);
    }


    private Tile getRandom(ArrayList<Tile> neighbours){
        if(neighbours.size() == 1){
            return neighbours.get(0);
        }
        int index = (int) (Math.random() * neighbours.size());
        return neighbours.get(index);
    }
}
