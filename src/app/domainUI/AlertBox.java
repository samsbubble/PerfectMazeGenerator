package app.domainUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message, int size) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);

        Label label = new Label();
        label.setText(message);
        label.setWrapText(true);

        Button closeButton = new Button("Close the window");
        closeButton.setOnAction( e -> window.close());

        VBox layout = new VBox(10);
        layout.setSpacing(20);
        layout.setPadding(new Insets(30, 30, 30, 30));
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, size, size);

        window.setScene(scene);
        window.showAndWait();
    }
}
