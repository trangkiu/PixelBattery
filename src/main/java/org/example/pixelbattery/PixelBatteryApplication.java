package org.example.pixelbattery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.pixelbattery.Service.Kernel32;


import java.io.IOException;
import java.util.Objects;

public class PixelBatteryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Remove native window decorations
        stage.initStyle(StageStyle.UNDECORATED);

        // Load the main content
        FXMLLoader fxmlLoader = new FXMLLoader(PixelBatteryApplication.class.getResource("hello-view.fxml"));
        Region mainContent = fxmlLoader.load();
        mainContent.getStyleClass().add("main-content");

        // Create custom title bar
        Label titleLabel = new Label("Health bar!");
        titleLabel.getStyleClass().add("custom-title-label");

        // Spacer to push close button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Close button
        Button closeButton = new Button("×");
        closeButton.getStyleClass().add("close-button");
        closeButton.setOnAction(e -> stage.close());

        // Title bar container
        HBox titleBar = new HBox(titleLabel, spacer, closeButton);
        titleBar.getStyleClass().add("custom-title-bar");

        // Make title bar draggable
        final double[] xOffset = {0};
        final double[] yOffset = {0};
        titleBar.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });
        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        });

        // Main container
        VBox root = new VBox(titleBar, mainContent);
        root.setSpacing(0);
        Scene scene = new Scene(root, 320, 84);

        // Load CSS styling
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/style.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
