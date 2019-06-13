package app.logic.domain;

import java.util.ArrayList;
import java.util.HashMap;


// Helper class with frequently used methods.
public class Method {

    // Method returning a random element from the given list.
    public static Cell getRandom(ArrayList<Cell> neighbours) {
        if (neighbours.size() == 1) {
            return neighbours.get(0);
        }
        int index = (int) (Math.random() * neighbours.size());
        return neighbours.get(index);
    }

    // Method returning a unvisited cell from the list.
    public static Cell getRandomPossibleCellInMaze(Maze maze) {
        for(int i = 0; i < maze.getDimX(); i++){
            for(int j = 0; j < maze.getDimY(); j++){
                if (!maze.getCell(i, j).isVisited())
                    return maze.getCell(i, j);
            }
        }
        return null;
    }

    // Method performing a random walk in the given maze from a given starting cell.
    public static HashMap<Cell, Cell> performRandomWalk(Cell startingCell, Maze maze) {
        HashMap<Cell, Cell> walk = new HashMap<>();
        Cell currentCell = startingCell;
        Cell nextCell;
        ArrayList<Cell> neighbours;
        do {
            // Get a random next cell from all neighbours to the current cell.
            neighbours = maze.getAllNeighbours(currentCell.getXCoordinate(), currentCell.getYCoordinate());
            nextCell = Method.getRandom(neighbours);

            // Save the choice in the map. (key, value) = (current, next)
            walk.put(maze.getCell(currentCell.getXCoordinate(), currentCell.getYCoordinate()), maze.getCell(nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // If the cell is visited break the loop.
            if (maze.getCell(nextCell.getXCoordinate(), nextCell.getYCoordinate()).isVisited())
                break;

            // Set the current cell equal to the next.
            currentCell = maze.getCell(nextCell.getXCoordinate(), nextCell.getYCoordinate());
        } while(true);
        return walk;
    }
}