package app.controllers;

import app.logic.Algorithm;
import app.logic.Prim;
import app.logic.RecursiveBacktracking;
import app.domainUI.AlertBox;
import app.logic.Tracking.*;
import com.sun.org.apache.regexp.internal.RE;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerGenerator extends Controller {

    @FXML MenuItem menuitemsavefile, menuitemsavevideo, menuitemclose, menuitemabout;
    @FXML Button btnGenerate;
    @FXML Label labelDeadEnd, labelLength, labelRiver, labelTurn;
    @FXML ComboBox<String> comboAlgorithms;
    @FXML ComboBox<String> comboSize;
    @FXML Canvas canvas;
    private GraphicsContext gc;
    private int eventIndex = 0;
    private int conversion = 0;
    private int currentX = -1, currentY = -1;
    private Algorithm algo = new Algorithm();

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
        OperationTracker operations = new OperationTracker();
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

            // Get the solution
        try {
            algo.generateMaze(dim, dim);
            algo.runAlgorithm(algorithm);

            operations = algo.getOperationTracker();

            eventIndex = 0;
            Timer myTimer = new Timer();
            OperationTracker finalOperations = operations;
            TimerTask myTask = new TimerTask() {
                @Override
                public void run() {
                    if (eventIndex >= finalOperations.size()) {
                        gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2, conversion/2);
                        myTimer.cancel();
                        return;
                    }
                    // Draw next step
                    drawOperation(finalOperations.get(eventIndex));
                    eventIndex++;
                }
            };
            myTimer.scheduleAtFixedRate(myTask, 0L, 200L);
        }catch (Exception e) {
            AlertBox.display("Warning", "Something went wrong in the generation of the maze.");
        }

        /*for (int i = 0; i < operations.size(); i++){
            System.out.println(operations.get(i));
        }*/

        /*try {
            // Animate the maze
            eventIndex = 0;
            Timer myTimer = new Timer();
            OperationTracker finalOperations = operations;
            TimerTask myTask = new TimerTask() {
                @Override
                public void run() {
                    if (eventIndex >= finalOperations.size()) {
                        gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2, conversion/2);
                        myTimer.cancel();
                        return;
                    }
                    // Draw next step
                    drawOperation(finalOperations.get(eventIndex));
                    eventIndex++;
                }
            };
            myTimer.scheduleAtFixedRate(myTask, 0L, 200L);

            // Calculate and write out the properties


        } catch (Exception e ){
            AlertBox.display("Warning", "Something went wrong in the animation of the maze.");
        }*/
    }

    private void drawOperation(Operation op){
        if (op instanceof Move) {
            currentX = ((Move) op).getxCoordinate();
            currentY = ((Move) op).getyCoordinate();
            gc.fillRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2, conversion/2);
        }
        else if (op instanceof BackTrack) {
            gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2, conversion/2);
            currentX = ((BackTrack) op).getxCoordinate();
            currentY = ((BackTrack) op).getyCoordinate();
        }
        else if (op instanceof KnockDownWall) {
            switch (((KnockDownWall) op).getWall()){
                case EAST:
                    gc.clearRect((currentX+1)*conversion - 1,
                            (currentY)*conversion + 1,
                            3, conversion-3);
                    /*gc.clearRect((currentX+1)*conversion - 1,
                                 (currentY)*conversion + 1,
                            3, conversion-2);*/
                    break;
                case WEST:
                    gc.clearRect((currentX)*conversion - 1,
                            (currentY)*conversion + 1,
                            3, conversion-3);
                    /*gc.clearRect((currentX)*conversion - 1,
                            (currentY)*conversion + 1,
                            3, conversion-2);*/
                    break;
                case NORTH:
                    gc.clearRect((currentX)*conversion + 2,
                            (currentY)*conversion - 2,
                            conversion-3, 3);
                    /*gc.clearRect((currentX)*conversion + 2,
                            (currentY)*conversion - 2,
                            conversion-2, 3);*/
                    break;
                case SOUTH:
                    gc.clearRect((currentX)*conversion + 2,
                            (currentY+1)*conversion - 2,
                            conversion-3, 3);
                    /*gc.clearRect((currentX)*conversion + 2,
                            (currentY+1)*conversion - 2,
                            conversion-2, 3);*/
                    break;
            }
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
