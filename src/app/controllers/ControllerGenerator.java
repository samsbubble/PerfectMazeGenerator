package app.controllers;

import app.algorithms.RecursiveBacktracking;
import app.algorithms.Solution;
import app.domainUI.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerGenerator extends Controller {

    @FXML MenuItem menuitemsavefile, menuitemsavevideo, menuitemclose, menuitemabout;
    @FXML Button btnGenerate;
    @FXML Label labelDeadEnd, labelLength, labelRiver, labelTurn;
    @FXML ComboBox<String> comboAlgorithms;
    @FXML ComboBox<String> comboSize;
    @FXML Canvas canvas;
    private GraphicsContext gc;

    @FXML
    public void initialize(){
        comboAlgorithms.setItems(observableArrayList("Recursive Backtracking Algorithm", "Prim's Algorithm", "Wilson's Algorithm"));
        comboSize.setItems(observableArrayList("5", "10", "15", "20", "25", "50"));
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

    @FXML
    public void generate(){
        String algorithm;
        int dim;
        try {
            algorithm = comboAlgorithms.getValue();
            dim = Integer.parseInt(comboSize.getValue());
            gc = canvas.getGraphicsContext2D();

            // Clear canvas
            clearCanvas();
            // Draw the maze skeleton
            initialiseMaze(dim);

            // Animate the maze
            RecursiveBacktracking maze = new RecursiveBacktracking(dim, dim);
            maze.generateMaze();
            List<Solution> solutionList = maze.getSolution();

            // Calculate and write out the properties


        } catch (Exception e ){
            AlertBox.display("Warning", "You haven't chosen a dimension or an algorithm yet.");
        }
    }


    private void animateMaze(){



    }

    private void clearCanvas(){
        double dim = canvas.getHeight();
        gc.clearRect(0,0, dim, dim);
    }


    private void initialiseMaze(int dim){
        double height = canvas.getHeight();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // Draw the square
        /*gc.strokeLine(0,0, 0, height); // left line
        gc.strokeLine(0, height, width, height); // bottom line
        gc.strokeLine(0,0, width, 0); // top line
        gc.strokeLine(width, 0, width, height); // right line*/

        // Draw the grid
        // Drawing the vertical lines
        double conversion = height/dim;
        for (int i = 0; i < dim+1; i++){
            gc.strokeLine(i*conversion,0, i*conversion, height);
        }

        // Drawing the horizontal lines
        for (int i = 0; i < dim+1; i++){
            gc.strokeLine(0,i*conversion, height, i*conversion);
        }

    }

}
