package app.algorithms;

import java.util.ArrayList;

public class Maze {

    // This is the structure to hold the maze

    private int dimX, dimY;
    private Tile[][] tiles;
    private ArrayList<Solution> solution;

    public Maze(int dimX, int dimY){
        tiles = new Tile[dimX][dimY];
        solution = new ArrayList<>();
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

    public void setSolution(int xCoordinate, int yCoordinate, Direction dir){
        solution.add(new Solution(xCoordinate, yCoordinate, dir));
    }

    public ArrayList<Solution> getSolution(){
        return solution;
    }

}
