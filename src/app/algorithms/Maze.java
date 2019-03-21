package app.algorithms;

public class Maze {

    // This is the structure to hold the maze

    private int dimX, dimY;

    private Tile[][] tiles;

    public Maze(int dimX, int dimY){
        tiles = new Tile[dimX][dimY];
    }

    public void setTile(int posX, int posY, Direction dir){
        tiles[posX][posY].breakDownWall(dir);
    }

    public Boolean isVisited(int posX, int posY){
        return tiles[posX][posY].isVisited();
    }

    public void setVisited(int posX, int posY){
        tiles[posX][posY].setVisited();
    }


}
