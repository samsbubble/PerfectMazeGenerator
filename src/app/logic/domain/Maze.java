package app.logic.domain;

import java.util.ArrayList;

public class Maze {
    // This is the structure to hold the maze.
    private int dimX, dimY;
    private Cell[][] cells;

    // Constructor of the maze object. It initialises the 2D-array of cells.
    public Maze(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;

        cells = new Cell[dimX][dimY];
        for (int i = 0; i < dimX; i++){
            for (int j = 0; j < dimY; j++){
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    // Get the x dimension
    public int getDimX(){
        return dimX;
    }

    // Get the y dimension
    public int getDimY(){
        return dimY;
    }

    // Set a given cell to visited by calling the cell object.
    public void setVisited(int posX, int posY){
        cells[posX][posY].setVisited();
    }

    // Get the cell with the given coordinates.
    public Cell getCell(int currentX, int currentY){
        return cells[currentX][currentY];
    }

    // Break down the wall between the current and next cell by calling the method from the cell class on each cell.
    public void breakDownWall(Cell currentCell, Cell nextCell){
        // Check for WEST
        if (currentCell.getXCoordinate()-1 == nextCell.getXCoordinate()){
            cells[currentCell.getXCoordinate()][currentCell.getYCoordinate()].breakDownWall(Direction.WEST);
            cells[nextCell.getXCoordinate()][nextCell.getYCoordinate()].breakDownWall(Direction.EAST);
        }

        // Check for EAST
        if (currentCell.getXCoordinate()+1 == nextCell.getXCoordinate()){
            cells[currentCell.getXCoordinate()][currentCell.getYCoordinate()].breakDownWall(Direction.EAST);
            cells[nextCell.getXCoordinate()][nextCell.getYCoordinate()].breakDownWall(Direction.WEST);
        }

        // Check for NORTH
        if (currentCell.getYCoordinate()-1 == nextCell.getYCoordinate()){
            cells[currentCell.getXCoordinate()][currentCell.getYCoordinate()].breakDownWall(Direction.NORTH);
            cells[nextCell.getXCoordinate()][nextCell.getYCoordinate()].breakDownWall(Direction.SOUTH);
        }

        // Check for SOUTH
        if (currentCell.getYCoordinate()+1 == nextCell.getYCoordinate()){
            cells[currentCell.getXCoordinate()][currentCell.getYCoordinate()].breakDownWall(Direction.SOUTH);
            cells[nextCell.getXCoordinate()][nextCell.getYCoordinate()].breakDownWall(Direction.NORTH);
        }
    }

    // Method returning the legal neighbours in a list.
    public ArrayList<Cell> getPossibleNeighbours(int currentX, int currentY) {
        ArrayList<Cell> possibleNeighbours = new ArrayList<>();
        // WEST
        if (0 <= currentX-1 && cells[currentX-1][currentY].isVisited().equals(false))
            possibleNeighbours.add(cells[currentX-1][currentY]);

        // EAST
        if(currentX+1 < dimX && cells[currentX+1][currentY].isVisited().equals(false))
            possibleNeighbours.add(cells[currentX+1][currentY]);

        // NORTH
        if(0 <= currentY-1 && cells[currentX][currentY-1].isVisited().equals(false))
            possibleNeighbours.add(cells[currentX][currentY-1]);

        // SOUTH
        if(currentY+1 < dimY && cells[currentX][currentY+1].isVisited().equals(false))
            possibleNeighbours.add(cells[currentX][currentY+1]);

        return possibleNeighbours;
    }

    // Method returning the neighbours which have already been visited in a list.
    public ArrayList<Cell> getPossibleNeighboursToFrontier(int currentX, int currentY) {
        ArrayList<Cell> possibleNeighbours = new ArrayList<>();

        // WEST
        if (0 <= currentX-1 && cells[currentX-1][currentY].isVisited().equals(true))
            possibleNeighbours.add(cells[currentX-1][currentY]);

        // EAST
        if(currentX+1 < dimX && cells[currentX+1][currentY].isVisited().equals(true))
            possibleNeighbours.add(cells[currentX+1][currentY]);

        // NORTH
        if(0 <= currentY-1 && cells[currentX][currentY-1].isVisited().equals(true))
            possibleNeighbours.add(cells[currentX][currentY-1]);

        // SOUTH
        if(currentY+1 < dimY && cells[currentX][currentY+1].isVisited().equals(true))
            possibleNeighbours.add(cells[currentX][currentY+1]);

        return possibleNeighbours;
    }

    // Method returning all neighbours, which are within the grid of the maze.
    public ArrayList<Cell> getAllNeighbours(int currentX, int currentY) {
        ArrayList<Cell> possibleNeighbours = new ArrayList<>();
        // WEST
        if (0 <= currentX-1)
            possibleNeighbours.add(cells[currentX-1][currentY]);

        // EAST
        if(currentX+1 < dimX)
            possibleNeighbours.add(cells[currentX+1][currentY]);

        // NORTH
        if(0 <= currentY-1)
            possibleNeighbours.add(cells[currentX][currentY-1]);

        // SOUTH
        if(currentY+1 < dimY)
            possibleNeighbours.add(cells[currentX][currentY+1]);

        return possibleNeighbours;
    }

    // Method returning all the possible paths except to walk to except from the cell it came from.
    public ArrayList<Cell> getPossiblePaths(Cell prevCell, Cell curCell) {
        ArrayList<Cell> possiblePath = new ArrayList<>();

        if( !curCell.getValueOfWall(Direction.NORTH) && 0 <= curCell.getYCoordinate()-1){
            possiblePath.add(cells[curCell.getXCoordinate()][curCell.getYCoordinate()-1]);
        }
        if( !curCell.getValueOfWall(Direction.SOUTH) && curCell.getYCoordinate()+1 < dimY){
            possiblePath.add(cells[curCell.getXCoordinate()][curCell.getYCoordinate()+1]);
        }
        if( !curCell.getValueOfWall(Direction.EAST) && curCell.getXCoordinate()+1 < dimX){
            possiblePath.add(cells[curCell.getXCoordinate()+1][curCell.getYCoordinate()]);
        }
        if( !curCell.getValueOfWall(Direction.WEST) && 0 <= curCell.getXCoordinate()-1){
            possiblePath.add(cells[curCell.getXCoordinate()-1][curCell.getYCoordinate()]);
        }
        if (prevCell != null){
            possiblePath.remove(prevCell);
        }
        return possiblePath;
    }
}
