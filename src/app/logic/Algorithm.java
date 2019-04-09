package app.logic;

import app.logic.Tracking.*;
import app.logic.domain.Cell;
import app.logic.domain.Maze;

import java.util.ArrayList;

public class Algorithm {
    int dimX, dimY;
    OperationTracker operationTracker;
    Maze maze;

    public Algorithm(){}

    public void generateMaze(int dimX, int dimY) {
        this.dimX = dimX;
        this.dimY = dimY;
        maze = new Maze(dimX, dimY);
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
        }
    }

    public OperationTracker getOperationTracker(){
        return this.operationTracker;
    }

}

