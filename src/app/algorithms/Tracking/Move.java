package app.algorithms.Tracking;

public class Move extends Operation {

    private int xCoordinate, yCoordinate;

    public Move(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public String toString(){
        return "Moving to (" + xCoordinate + "," + yCoordinate + ")";
    }
}
