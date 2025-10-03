package org.example.pixelbattery;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    @FXML
    private Label welcomeText;

    @FXML
    private HBox pixelArtContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pixelArtContainer.getChildren().add(createGridPanePixelArt());
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private void heartPattern (GridPane grid){
        int[][] heartPattern = {
                {0, 1, 1, 0, 0, 1, 1, 0},
                {1, 2, 2, 1, 1, 2, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 1},
                {0, 1, 2, 2, 2, 2, 1, 0},
                {0, 0, 1, 2, 2, 1, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        int pixelSize = 5;

        for (int row = 0; row < heartPattern.length; row++) {
            for (int col = 0; col < heartPattern[row].length; col++) {
                Rectangle pixel = new Rectangle(pixelSize, pixelSize);

                switch (heartPattern[row][col]) {
                    case 0:
                        pixel.setFill(Color.TRANSPARENT);
                        break;
                    case 1:
                        pixel.setFill(Color.web("#8B0000"));
                        break;
                    case 2:
                        pixel.setFill(Color.web("#FF0000"));
                        break;
                }

                grid.add(pixel, col, row);
            }
        }

    }

    private HBox createGridPanePixelArt() {

        // Create 5 heart, with 5 GridPane
        ArrayList<GridPane> batteryBar = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            GridPane grid = new GridPane();
            grid.setHgap(2);
            grid.setVgap(2);
            grid.setStyle("-fx-background-color: white;");
            grid.setPadding(new Insets(10));
            heartPattern(grid);
            batteryBar.add(grid);
        }

        HBox container = new HBox(10);
        container.getChildren().addAll(batteryBar);
        return container;
    }
}
