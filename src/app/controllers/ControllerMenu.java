package app.controllers;

import app.Main;
import app.domainUI.AlertBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMenu extends Controller {

    static Stage window;
    @FXML Button btnexit, btncompleximp;

    public void startProgram() throws IOException {
        Parent layoutMenu = FXMLLoader.load(getClass().getResource("layoutMenu.fxml"));
        window = Main.getWindow();
        window.setTitle("Maze Generator");
        window.setScene(new Scene(layoutMenu, 1000, 600));
        window.show();
    }

    @FXML
    public void goToSimpleImp() throws IOException {
        changeScene("layoutGenerator.fxml");
    }

    @FXML
    public void exitProgram() {
        System.exit(0);
    }

    @FXML
    public void goToComplexImp(){
        AlertBox.display("Warning", "Not Implemented yet.");
    }

}
