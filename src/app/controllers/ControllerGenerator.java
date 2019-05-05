package app.controllers;

import app.Main;
import app.logic.Algorithm;
import app.domainUI.AlertBox;
import app.logic.Tracking.*;
import app.logic.domain.Cell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerGenerator {

    @FXML MenuItem menuitemsavefile, menuitemsavevideo, menuitemclose, menuitemabout;
    @FXML Button btnGenerate, btnSolution;
    @FXML Label labelDeadEnd, labelLength, labelRiver, labelTurn;
    @FXML ChoiceBox<String> choiceAlgorithms;
    @FXML ChoiceBox<String> choiceSize;
    @FXML Canvas canvas;
    private GraphicsContext gc;
    private int eventIndex = 0;
    private int conversion = 0;
    private double strokeFactor = 2;
    private int currentX = -1, currentY = -1;
    private Algorithm algo = new Algorithm();
    private ArrayList<Cell> mazeSolution;
    private int dimX = 0, dimY = 0;

    static Stage window;

    public void startProgram() throws IOException {
        Parent layoutMenu = FXMLLoader.load(getClass().getResource("layoutGenerator.fxml"));
        window = Main.getWindow();
        window.setTitle("Perfect Maze Generator!");
        window.setScene(new Scene(layoutMenu, 1100, 840));
        window.show();
    }

    @FXML
    public void initialize(){
        choiceAlgorithms.setItems(observableArrayList("Recursive Backtracking Algorithm", "Prim's Algorithm", "Wilson's Algorithm", "Random Algo", "Bottom up Algo"));
        choiceSize.setItems(observableArrayList("3", "5", "10", "15", "20", "25", "50"));
        btnSolution.setVisible(false);
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
        btnGenerate.setDisable(true);
        choiceAlgorithms.setDisable(true);
        choiceSize.setDisable(true);
        btnSolution.setVisible(false);
        String algorithm = "";
        try {
            algorithm = choiceAlgorithms.getValue();
            dimX = Integer.parseInt(choiceSize.getValue());
            dimY = dimX;
            gc = canvas.getGraphicsContext2D();
            conversion = (int) canvas.getHeight()/dimX;
        } catch (Exception e ){
            AlertBox.display("Warning", "You haven't chosen a dimension or an algorithm yet.");
        }

        // Clear canvas of any previously drawings
        clearCanvas();

        // Draw the maze skeleton
        initialiseMaze(dimX, conversion);

        // Get the maze generated
        try {
            algo.generateMaze(dimX, dimY);
            algo.runAlgorithm(algorithm);

            OperationTracker operations = algo.getOperationTracker();

            eventIndex = 0;
            Timer myTimer = new Timer();
            TimerTask myTask = new TimerTask() {
                @Override
                public void run() {
                    if (eventIndex >= operations.size()) {
                        gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2.0, conversion/2.0);
                        btnGenerate.setDisable(false);
                        choiceAlgorithms.setDisable(false);
                        choiceSize.setDisable(false);
                        btnSolution.setVisible(true);
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
        labelLength.setText(String.valueOf( algo.lengthOfSolution() ));
        labelTurn.setText(String.valueOf( algo.getNumberOfTurnsInSolution() ));
    }


    public void showSolution() {
        // Draw the solution on the canvas
        int xCoordinate, yCoordinate, nextXCoordinate, nextYCoordinate;

        // Make start and end openings in the maze
        gc.clearRect(0 + (strokeFactor*0.55),
                0 - (strokeFactor),
                conversion-(strokeFactor*1.2), (strokeFactor*1.5));

        gc.clearRect((dimX-1)*conversion + (strokeFactor*0.55),
                (dimY)*conversion - (strokeFactor),
                conversion-(strokeFactor*1.2), (strokeFactor*1.5));

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(conversion/12.0 > 1.5 ? conversion/12.0 : 2);

        // Draw a small starting line
        gc.strokeLine((mazeSolution.get(0).getXCoordinate() + 0.5) * conversion, mazeSolution.get(0).getYCoordinate()*conversion, (mazeSolution.get(0).getXCoordinate() + 0.5) * conversion, (mazeSolution.get(0).getYCoordinate() + 0.5) * conversion);

        for (int i = 0; i < mazeSolution.size()-1; i++) {
            xCoordinate = mazeSolution.get(i).getXCoordinate();
            yCoordinate = mazeSolution.get(i).getYCoordinate();

            nextXCoordinate = mazeSolution.get(i + 1).getXCoordinate();
            nextYCoordinate = mazeSolution.get(i + 1).getYCoordinate();
            gc.strokeLine((xCoordinate + 0.5) * conversion, (yCoordinate + 0.5) * conversion,
                    (nextXCoordinate + 0.5) * conversion, (nextYCoordinate + 0.5) * conversion);
        }
        // Draw a small ending line
        gc.strokeLine((mazeSolution.get(mazeSolution.size()-1).getXCoordinate() + 0.5) * conversion, (mazeSolution.get(mazeSolution.size()-1).getYCoordinate()+0.5)*conversion, (mazeSolution.get(mazeSolution.size()-1).getXCoordinate() + 0.5) * conversion, (mazeSolution.get(mazeSolution.size()-1).getYCoordinate()+1) * conversion);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        btnSolution.setVisible(false);
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
                    gc.clearRect((((KnockDownWall) op).getCurrentX()+1)*conversion - (strokeFactor),
                            (((KnockDownWall) op).getCurrentY())*conversion + (strokeFactor/1.8),
                            (strokeFactor*1.5), conversion-(strokeFactor*1.1));
                    break;
                case WEST:
                    gc.clearRect((((KnockDownWall) op).getCurrentX())*conversion - (strokeFactor),
                            (((KnockDownWall) op).getCurrentY())*conversion + (strokeFactor/1.8),
                            (strokeFactor*1.5), conversion-(strokeFactor*1.1));
                    break;
                case NORTH:
                    gc.clearRect((((KnockDownWall) op).getCurrentX())*conversion + (strokeFactor*0.55),
                            (((KnockDownWall) op).getCurrentY())*conversion - (strokeFactor),
                            conversion-(strokeFactor*1.2), (strokeFactor*1.5));
                    break;
                case SOUTH:
                    gc.clearRect((((KnockDownWall) op).getCurrentX())*conversion + (strokeFactor*0.55),
                            (((KnockDownWall) op).getCurrentY()+1)*conversion - (strokeFactor),
                            conversion-(strokeFactor*1.2), (strokeFactor*1.5));
                    break;
            }
        }
        else if (op instanceof UnMark) {
            gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2.0, conversion/2.0);
            currentX = ((UnMark) op).getxCoordinate();
            currentY = ((UnMark) op).getyCoordinate();
        }
        else if (op instanceof AddFrontier) {
            gc.fillRect((((AddFrontier) op).getxCoordinate()+0.24)*conversion, (((AddFrontier) op).getyCoordinate()+0.24)*conversion, conversion/2.0, conversion/2.0);
        }
    }


    private void clearCanvas(){
        double dim = canvas.getHeight();
        gc.clearRect(0,0, dim, dim);
    }


    private void initialiseMaze(int dim, double conversion){
        double height = canvas.getHeight();
        strokeFactor = conversion/25.0 > 1.5 ? conversion/25.0 : 2;

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(strokeFactor);
        gc.setFill(Color.DEEPPINK);

        // Draw the grid
        // Drawing the vertical lines
        for (int i = 0; i < dim+1; i++){
            gc.strokeLine(i*conversion,0, i*conversion, dimY*conversion);
        }

        // Drawing the horizontal lines
        for (int i = 0; i < dim+1; i++){
            gc.strokeLine(0,i*conversion, dimX*conversion, i*conversion);
        }
    }

}
