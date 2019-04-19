package app.logic.Tracking;

public class UnMark extends Operation {
    private int xCoordinate, yCoordinate;

    public UnMark(int xCoordinate, int yCoordinate){
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
        return "Unmarking (" + xCoordinate + "," + yCoordinate + ") from the frontiers";
    }

}
