package app;

import app.controllers.ControllerMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        ControllerMenu controller = new ControllerMenu();
        controller.startProgram();
    }

    public static Stage getWindow(){
        return window;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
