package app.logic;

import app.logic.domain.Cell;
import app.logic.domain.Maze;

import java.util.ArrayList;

public class Method {

    static Cell getRandom(ArrayList<Cell> neighbours) {
        if (neighbours.size() == 1) {
            return neighbours.get(0);
        }
        int index = (int) (Math.random() * neighbours.size());
        return neighbours.get(index);
    }


    static Cell getRandomPossibleCellInMaze(Maze maze) {
        do {
            int x = (int) (Math.random() * maze.getDimX());
            int y = (int) (Math.random() * maze.getDimY());
            if (!maze.getTile(x, y).isVisited())
                return maze.getTile(x, y);
        } while (true);
    }
}