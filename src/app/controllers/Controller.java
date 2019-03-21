package app.controllers;

import app.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

class Controller {

    void changeScene(String fxmlFile) throws IOException {
        Parent layout = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage window = Main.getWindow();
        window.setScene(new Scene(layout, 600, 400));
        window.show();
    }

}
