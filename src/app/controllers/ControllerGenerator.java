package app.controllers;

import app.logic.Algorithm;
import app.domainUI.AlertBox;
import app.logic.Tracking.*;
import app.logic.domain.Cell;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerGenerator extends Controller {

    @FXML MenuItem menuitemsavefile, menuitemsavevideo, menuitemclose, menuitemabout;
    @FXML Button btnGenerate, btnSolution;
    @FXML Label labelDeadEnd, labelLength, labelRiver, labelTurn;
    @FXML ComboBox<String> comboAlgorithms;
    @FXML ComboBox<String> comboSize;
    @FXML Canvas canvas;
    private GraphicsContext gc;
    private int eventIndex = 0;
    private int conversion = 0;
    private int currentX = -1, currentY = -1;
    private Algorithm algo = new Algorithm();
    private ArrayList<Cell> mazeSolution;

    @FXML
    public void initialize(){
        comboAlgorithms.setItems(observableArrayList("Recursive Backtracking Algorithm", "Prim's Algorithm", "Wilson's Algorithm"));
        comboSize.setItems(observableArrayList("3", "5", "10", "15", "20", "25", "50"));
    }

    @FXML
    public void goToMenu() throws IOException {
        changeScene("layoutMenu.fxml");
    }

    @FXML
    public void saveFile(){
        AlertBox.display("Warning", "Not Implemented yet.");
    }

    @FXML
    public void saveVideo(){
        AlertBox.display("Warning", "Not Implemented yet.");
    }

    @FXML
    public void closeWindow(){
        System.exit(0);
    }

    @FXML
    public void about(){
        AlertBox.display("Warning", "Not Implemented yet.");
    }


    // When choosing a grid size, display immediately.


    @FXML
    public void generate(){
        String algorithm = "";
        int dim = 0;
        try {
            algorithm = comboAlgorithms.getValue();
            dim = Integer.parseInt(comboSize.getValue());
            gc = canvas.getGraphicsContext2D();
            conversion = (int) canvas.getHeight()/dim;
        } catch (Exception e ){
            AlertBox.display("Warning", "You haven't chosen a dimension or an algorithm yet.");
        }

        // Clear canvas of any previously drawings
        clearCanvas();

        // Draw the maze skeleton
        initialiseMaze(dim, conversion);

        // Get the maze generated
        try {
            algo.generateMaze(dim, dim);
            algo.runAlgorithm(algorithm);

            OperationTracker operations = algo.getOperationTracker();

            eventIndex = 0;
            Timer myTimer = new Timer();
            TimerTask myTask = new TimerTask() {
                @Override
                public void run() {
                    if (eventIndex >= operations.size()) {
                        gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2.0, conversion/2.0);

                        myTimer.cancel();
                        return;
                    }
                    // Draw next step
                    drawOperation(operations.get(eventIndex));
                    eventIndex++;
                }
            };
            myTimer.scheduleAtFixedRate(myTask, 0L, (10000L/operations.size()));
        }catch (Exception e) {
            AlertBox.display("Warning", "Something went wrong in the generation of the maze.");
        }

        // Calculate solution to the maze and the properties
        try {
            mazeSolution = algo.getSolution();
        } catch (Exception e){
            AlertBox.display("Warning", "The solution could not be found.");
        }

        labelDeadEnd.setText(String.valueOf( algo.getNumberOfDeadEnds() ));
        labelRiver.setText(String.valueOf( algo.getRiverFactor() ));
        labelLength.setText(String.valueOf( algo.lenghtOfSolution() ));
        labelTurn.setText(String.valueOf( algo.getNumberOfTurnsInSolution() ));
    }


    public void showSolution() {
        // Draw the solution on the canvas
        int xCoordinate, yCoordinate, nextXCoordinate, nextYCoordinate;

        for (int i = 0; i < mazeSolution.size()-1; i++) {
            xCoordinate = mazeSolution.get(i).getXCoordinate();
            yCoordinate = mazeSolution.get(i).getYCoordinate();

            nextXCoordinate = mazeSolution.get(i + 1).getXCoordinate();
            nextYCoordinate = mazeSolution.get(i + 1).getYCoordinate();

            gc.setStroke(Color.BLUE);
            gc.strokeLine((xCoordinate + 0.5) * conversion, (yCoordinate + 0.5) * conversion,
                    (nextXCoordinate + 0.5) * conversion, (nextYCoordinate + 0.5) * conversion);
            gc.setStroke(Color.BLACK);
        }
    }

    private void drawOperation(Operation op){
        if (op instanceof Move) {
            currentX = ((Move) op).getxCoordinate();
            currentY = ((Move) op).getyCoordinate();
            gc.fillRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2.0, conversion/2.0);
        }
        else if (op instanceof BackTrack) {
            gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2.0, conversion/2.0);
            currentX = ((BackTrack) op).getxCoordinate();
            currentY = ((BackTrack) op).getyCoordinate();
        }
        else if (op instanceof KnockDownWall) {
            switch (((KnockDownWall) op).getWall()){
                case EAST:
                    gc.clearRect((((KnockDownWall) op).getCurrentX()+1)*conversion - 1,
                            (((KnockDownWall) op).getCurrentY())*conversion + 1,
                            3, conversion-3);
                    break;
                case WEST:
                    gc.clearRect((((KnockDownWall) op).getCurrentX())*conversion - 1,
                            (((KnockDownWall) op).getCurrentY())*conversion + 1,
                            3, conversion-3);
                    break;
                case NORTH:
                    gc.clearRect((((KnockDownWall) op).getCurrentX())*conversion + 2,
                            (((KnockDownWall) op).getCurrentY())*conversion - 2,
                            conversion-3, 3);
                    break;
                case SOUTH:
                    gc.clearRect((((KnockDownWall) op).getCurrentX())*conversion + 2,
                            (((KnockDownWall) op).getCurrentY()+1)*conversion - 2,
                            conversion-3, 3);
                    break;
            }
        }
        else if (op instanceof UnMark) {
            gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2.0, conversion/2.0);
            currentX = ((UnMark) op).getxCoordinate();
            currentY = ((UnMark) op).getyCoordinate();
        }
        else if (op instanceof AddFrontier) {
            gc.fillRect((((AddFrontier) op).getxCoordinate()+0.24)*conversion, (((AddFrontier) op).getyCoordinate()+0.24)*conversion, conversion/2, conversion/2);
        }
    }


    private void clearCanvas(){
        double dim = canvas.getHeight();
        gc.clearRect(0,0, dim, dim);
    }


    private void initialiseMaze(int dim, double conversion){
        double height = canvas.getHeight();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFill(Color.DEEPPINK);

        // Draw the grid
        // Drawing the vertical lines
        for (int i = 0; i < dim+1; i++){
            gc.strokeLine(i*conversion,0, i*conversion, height);
        }

        // Drawing the horizontal lines
        for (int i = 0; i < dim+1; i++){
            gc.strokeLine(0,i*conversion, height, i*conversion);
        }
    }

}
