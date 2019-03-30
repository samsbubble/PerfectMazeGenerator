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
    private GraphicsContext gcAdd, gcRemove;

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

    @FXML
    public void generate(){
        String algorithm;
        int dim;
        try {
            algorithm = comboAlgorithms.getValue();
            dim = Integer.parseInt(comboSize.getValue());
            gcAdd = canvas.getGraphicsContext2D();
            gcRemove = canvas.getGraphicsContext2D();
            double conversion = canvas.getHeight()/dim;

            // Clear canvas of any previously drawings
            clearCanvas();

            // Draw the maze skeleton
            initialiseMaze(dim, conversion);

            // Get the solution
            RecursiveBacktracking maze = new RecursiveBacktracking(dim, dim);
            maze.generateMaze();

            for (int i = 0; i < maze.opTracker.operations.size(); i++){
                System.out.println(maze.opTracker.operations.get(i));
            }

            //List<Solution> solutionList = maze.getSolution();

            // Animate the maze
            //animateMaze(solutionList, conversion);

            // Calculate and write out the properties


        } catch (Exception e ){
            AlertBox.display("Warning", "You haven't chosen a dimension or an algorithm yet.");
        }
    }


    private void animateMaze(List<Solution> solutionList, double con) throws InterruptedException {
        gcRemove.setStroke(Color.WHITE);
        gcRemove.setLineWidth(1);

        Solution move;
        for (int i = 0; i < solutionList.size(); i++){
            move = solutionList.get(i);
            switch (move.getDirection()){
                case NORTH:
                    gcRemove.strokeLine((move.getxCoordinate()*con), (move.getyCoordinate()*con), (move.getxCoordinate()*con) + con, (move.getyCoordinate()*con));
                    break;
                case SOUTH:
                    gcRemove.strokeLine((move.getxCoordinate()*con), (move.getyCoordinate()*con) + con, (move.getxCoordinate()*con) + con, (move.getyCoordinate()*con) + con);
                    break;
                case WEST:
                    gcRemove.strokeLine((move.getxCoordinate()*con), (move.getyCoordinate()*con), (move.getxCoordinate()*con), (move.getyCoordinate()*con) + con);
                    break;
                case EAST:
                    gcRemove.strokeLine((move.getxCoordinate()*con) + con, (move.getyCoordinate()*con), (move.getxCoordinate()*con) + con, (move.getyCoordinate()*con) + con);
                    break;
            }
        }
    }

    private void clearCanvas(){
        double dim = canvas.getHeight();
        gcAdd.clearRect(0,0, dim, dim);
    }


    private void initialiseMaze(int dim, double conversion){
        double height = canvas.getHeight();

        gcAdd.setStroke(Color.BLACK);
        gcAdd.setLineWidth(1);

        // Draw the grid
        // Drawing the vertical lines
        for (int i = 0; i < dim+1; i++){
            gcAdd.strokeLine(i*conversion,0, i*conversion, height);
        }

        // Drawing the horizontal lines
        for (int i = 0; i < dim+1; i++){
            gcAdd.strokeLine(0,i*conversion, height, i*conversion);
        }

    }

}
