package app.algorithms.Tracking;

public class BackTrack extends Operation {

    private int xCoordinate, yCoordinate;

    public BackTrack(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public String toString(){
        return "Backtracking to (" + xCoordinate + "," + yCoordinate + ")";
    }

}
