package app.controllers;

import app.domainUI.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerGenerator extends Controller {

    @FXML MenuItem menuitemsavefile, menuitemsavevideo, menuitemclose, menuitemabout;
    @FXML Button btnGenerate;
    @FXML Label labelDeadEnd, labelLength, labelRiver, labelTurn;
    @FXML ComboBox<String> comboAlgorithms;
    @FXML ComboBox<String> comboSize;

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
        String algorithm = comboAlgorithms.getValue();
        int dim = Integer.parseInt(comboSize.getValue());


        // Draw the maze



        // Calculate and write out the properties
    }

}
