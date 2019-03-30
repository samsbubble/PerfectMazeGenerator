package app.algorithms;

import java.util.ArrayList;

public class Maze {

    // This is the structure to hold the maze

    private int dimX, dimY;
    private Tile[][] tiles;

    public Maze(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;

        tiles = new Tile[dimX][dimY];
        for (int i = 0; i < dimX; i++){
            for (int j = 0; j < dimY; j++){
                tiles[i][j] = new Tile(i, j);
            }
        }
    }

    public void setVisited(int posX, int posY){
        tiles[posX][posY].setVisited();
    }

    public Tile getTile(int currentX, int currentY){
        return tiles[currentX][currentY];
    }

    public void breakDownWall(Tile currentCell, Tile nextCell){
        // Check for WEST
        if (currentCell.getXCoordinate()-1 == nextCell.getXCoordinate()){
            currentCell.breakDownWall(Direction.WEST);
            nextCell.breakDownWall(Direction.EAST);
        }

        // Check for EAST
        if (currentCell.getXCoordinate()+1 == nextCell.getXCoordinate()){
            currentCell.breakDownWall(Direction.EAST);
            nextCell.breakDownWall(Direction.WEST);
        }

        // Check for NORTH
        if (currentCell.getYCoordinate()-1 == nextCell.getYCoordinate()){
            currentCell.breakDownWall(Direction.NORTH);
            nextCell.breakDownWall(Direction.SOUTH);
        }

        // Check for SOUTH
        if (currentCell.getYCoordinate()+1 == nextCell.getYCoordinate()){
            currentCell.breakDownWall(Direction.SOUTH);
            nextCell.breakDownWall(Direction.NORTH);
        }


    }

    public ArrayList<Tile> getPossibleNeighbours(int currentX, int currentY) {
        ArrayList<Tile> possibleNeighbours = new ArrayList<>();

        // WEST
        if (0 <= currentX-1 && tiles[currentX-1][currentY].isVisited().equals(false))
            possibleNeighbours.add(tiles[currentX-1][currentY]);

        // EAST
        if(currentX+1 < dimX && tiles[currentX+1][currentY].isVisited().equals(false))
            possibleNeighbours.add(tiles[currentX+1][currentY]);

        // NORTH
        if(0 <= currentY-1 && tiles[currentX][currentY-1].isVisited().equals(false))
            possibleNeighbours.add(tiles[currentX][currentY-1]);

        // SOUTH
        if(currentY+1 < dimY && tiles[currentX][currentY+1].isVisited().equals(false))
            possibleNeighbours.add(tiles[currentX][currentY+1]);

        return possibleNeighbours;
    }
}
