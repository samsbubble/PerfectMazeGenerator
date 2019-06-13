package app.controllers;

import app.Main;
import app.logic.Algorithm;
import app.domainUI.PopUpWindow;
import app.logic.domain.SolutionException;
import app.logic.tracking.*;
import app.logic.domain.Cell;
import app.test.TestGenerator;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerGenerator {

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
    private TestGenerator testGenerator = new TestGenerator();
    private ArrayList<Cell> mazeSolution;
    private int dimX = 0, dimY = 0, imageCount = 1;

    static Stage window;

    // Load the layout and set the scene
    public void startProgram() throws IOException {
        Parent layoutMenu = FXMLLoader.load(getClass().getResource("layoutGenerator.fxml"));
        window = Main.getWindow();
        window.setTitle("Perfect Maze Generator!");
        window.setScene(new Scene(layoutMenu, 1100, 840));
        window.show();
    }

    // Initialising drop-downs, buttons, the canvas and draw a grid on the canvas.
    @FXML
    public void initialize(){
        choiceAlgorithms.setItems(observableArrayList("Recursive Backtracking Algorithm", "Prim's Algorithm", "Wilson's Algorithm", "Random Choice", "Bottom Up", "RB Bottom Up"));
        choiceSize.setItems(observableArrayList("3", "5", "10", "15", "20", "25", "50", "60", "70", "80", "90", "100"));
        btnSolution.setVisible(false);
        gc = canvas.getGraphicsContext2D();
        initialiseMaze(15, canvas.getHeight()/15);
    }

    // Method calling the test generator for making the property tests.
    @FXML
    public void generatePropertyTests(){
        try {
            testGenerator.generatePropertyTests();
        } catch (SolutionException | IOException e) {
            e.printStackTrace();
        }
    }

    // Method calling the test generator for making the time complexity tests.
    @FXML
    public void generateTimeTests(){
        try {
            testGenerator.generateTimeTests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save an image of the maze on the canvas. The images are all put inside the image folder.
    @FXML
    public void saveMaze() {
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);

        String path = choiceAlgorithms.getValue();

        File file = new File("images/" + path + " Maze " + imageCount + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            imageCount++;
        } catch (IOException e) {
            PopUpWindow.display("Error", "The maze could not be saved.", 200);
        }
    }

    // Close the program.
    @FXML
    public void closeWindow(){
        System.exit(0);
    }

    // Method creating a pop-up window with instruction for the program.
    @FXML
    public void about(){
        PopUpWindow.display("About", "This application generates perfect mazes.\n\n" +
                "You can choose, which algorithm and the size of the maze you wish, in the right column and press the Generate maze button, " +
                "to see the maze being generated on the canvas.\n\n" +
                "Furthermore, you are able to see the values of the properties under the Generate maze button and press the Show solution button, " +
                "to see the solution of the generated maze on the canvas.\n\n" +
                "If you wish to close the program, you can do so by either pressing the small red cross in the top left/right corner of the window," +
                " or by going under File in the menu bar and pressing the option Close.", 400);
    }


    // Method run when the generate button is clicked.
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
            PopUpWindow.display("Warning", "You haven't chosen a dimension or an algorithm yet.", 200);
            btnGenerate.setDisable(false);
            choiceAlgorithms.setDisable(false);
            choiceSize.setDisable(false);
            return;
        }

        // Clear canvas of any previously drawings
        clearCanvas();

        // Draw the maze skeleton
        initialiseMaze(dimX, conversion);

        // Generate maze and draw the generation on the canvas.
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
            myTimer.scheduleAtFixedRate(myTask, 0L, (int)(1000L*dimX/operations.size()));
        }catch (Exception e) {
            PopUpWindow.display("Warning", "Something went wrong in the generation of the maze.", 200);
            btnGenerate.setDisable(false);
            choiceAlgorithms.setDisable(false);
            choiceSize.setDisable(false);
            return;
        }

        // Calculate solution to the maze and the properties
        try {
            mazeSolution = algo.getSolution();
        } catch (SolutionException e){
            PopUpWindow.display("Warning", "The solution could not be found.", 200);
            btnGenerate.setDisable(false);
            choiceAlgorithms.setDisable(false);
            choiceSize.setDisable(false);
            return;
        }

        // Print the properties of the maze.
        labelDeadEnd.setText(String.valueOf( algo.getNumberOfDeadEnds() ));
        labelRiver.setText(String.valueOf( algo.getRiverFactor() ));
        labelLength.setText(String.valueOf( algo.lengthOfSolution() ));
        labelTurn.setText(String.valueOf( algo.getNumberOfTurnsInSolution() ));
    }

    // Draw the solution on the canvas
    public void showSolution() {
        int xCoordinate, yCoordinate, nextXCoordinate, nextYCoordinate;

        // Make an entrance and exit to the maze.
        gc.clearRect(0 + (strokeFactor*0.55),
                0 - (strokeFactor),
                conversion-(strokeFactor*1.2), (strokeFactor*1.5));

        gc.clearRect((dimX-1)*conversion + (strokeFactor*0.55),
                (dimY)*conversion - (strokeFactor),
                conversion-(strokeFactor*1.2), (strokeFactor*1.5));

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(conversion/12.0 > 1.5 ? conversion/12.0 : 2);

        // Draw a small starting line at the entrance.
        gc.strokeLine((mazeSolution.get(0).getXCoordinate() + 0.5) * conversion, mazeSolution.get(0).getYCoordinate()*conversion, (mazeSolution.get(0).getXCoordinate() + 0.5) * conversion, (mazeSolution.get(0).getYCoordinate() + 0.5) * conversion);

        // Draw a line from centre to centre for each of the cells in the solution.
        for (int i = 0; i < mazeSolution.size()-1; i++) {
            xCoordinate = mazeSolution.get(i).getXCoordinate();
            yCoordinate = mazeSolution.get(i).getYCoordinate();

            nextXCoordinate = mazeSolution.get(i + 1).getXCoordinate();
            nextYCoordinate = mazeSolution.get(i + 1).getYCoordinate();
            gc.strokeLine((xCoordinate + 0.5) * conversion, (yCoordinate + 0.5) * conversion,
                    (nextXCoordinate + 0.5) * conversion, (nextYCoordinate + 0.5) * conversion);
        }

        // Draw a small line at the exit.
        gc.strokeLine((mazeSolution.get(mazeSolution.size()-1).getXCoordinate() + 0.5) * conversion, (mazeSolution.get(mazeSolution.size()-1).getYCoordinate()+0.5)*conversion, (mazeSolution.get(mazeSolution.size()-1).getXCoordinate() + 0.5) * conversion, (mazeSolution.get(mazeSolution.size()-1).getYCoordinate()+1) * conversion);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        btnSolution.setVisible(false);
    }

    // Method, drawing the specified operation.
    private void drawOperation(Operation op){
        if (op instanceof Move) { // Move draws a small pink box, making the route for the Recursive Backtracking algorithm.
            currentX = ((Move) op).getxCoordinate();
            currentY = ((Move) op).getyCoordinate();
            gc.fillRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2.0, conversion/2.0);
        }
        else if (op instanceof BackTrack) { // Removes the pink box, when backtracking with the Recursive Backtracking algorithm.
            gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2.0, conversion/2.0);
            currentX = ((BackTrack) op).getxCoordinate();
            currentY = ((BackTrack) op).getyCoordinate();
        }
        else if (op instanceof KnockDownWall) { // This operations draws a while box over the wall to make an opening.
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
        else if (op instanceof UnMark) { // Unmarking is used to remove the cell from the frontier list and move to the cell.
            gc.clearRect((currentX+0.24)*conversion, (currentY+0.24)*conversion, conversion/2.0, conversion/2.0);
            currentX = ((UnMark) op).getxCoordinate();
            currentY = ((UnMark) op).getyCoordinate();
        }
        else if (op instanceof AddFrontier) { // Marking a cell to the frontier list with a box.
            gc.fillRect((((AddFrontier) op).getxCoordinate()+0.24)*conversion, (((AddFrontier) op).getyCoordinate()+0.24)*conversion, conversion/2.0, conversion/2.0);
        }
    }

    // Clears the canvas.
    private void clearCanvas(){
        double dim = canvas.getHeight();
        gc.clearRect(0,0, dim, dim);
    }

    // Initials the grid of the maze by drawing all the vertical and horizontal lines.
    private void initialiseMaze(int dim, double conversion){
        strokeFactor = conversion/25.0 > 1.5 ? conversion/25.0 : 2;

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(strokeFactor);
        gc.setFill(Color.DEEPPINK);

        // Drawing the vertical lines
        for (int i = 0; i < dim+1; i++){
            gc.strokeLine(i*conversion,0, i*conversion, dim*conversion);
        }

        // Drawing the horizontal lines
        for (int i = 0; i < dim+1; i++){
            gc.strokeLine(0,i*conversion, dim*conversion, i*conversion);
        }
    }

}
