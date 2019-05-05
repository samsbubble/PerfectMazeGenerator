package app.logic;

import app.logic.domain.Cell;
import app.logic.domain.Maze;

import java.util.ArrayList;
import java.util.HashMap;

public class Method {

    static Cell getRandom(ArrayList<Cell> neighbours) {
        if (neighbours.size() == 1) {
            return neighbours.get(0);
        }
        int index = (int) (Math.random() * neighbours.size());
        return neighbours.get(index);
    }


    static Cell getRandomPossibleCellInMaze(Maze maze) {
        for(int i = 0; i < maze.getDimX(); i++){
            for(int j = 0; j < maze.getDimY(); j++){
                if (!maze.getTile(i, j).isVisited())
                    return maze.getTile(i, j);
            }
        }
        return null;
    }

    static HashMap<Cell, Cell> performRandomWalk(Cell startingCell, Maze maze) {
        HashMap<Cell, Cell> walk = new HashMap<>();
        Cell currentCell = startingCell, nextCell;
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