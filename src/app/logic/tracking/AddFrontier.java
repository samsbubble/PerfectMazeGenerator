package app.logic.tracking;


// Class for saving a frontier as an operation.
public class AddFrontier extends Operation {
    private int xCoordinate, yCoordinate;

    public AddFrontier(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getxCoordinate(){
        return xCoordinate;
    }

    public int getyCoordinate(){
        return yCoordinate;
    }

    @Override
    public String toString(){
        return "Adding frontier (" + xCoordinate + "," + yCoordinate + ")";
    }
}
