package org.example.pixelbattery;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.example.pixelbattery.Service.Kernel32;
import org.example.pixelbattery.model.HeartPattern;


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
        updateBatteryDisplay();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> updateBatteryDisplay()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateBatteryDisplay() {
        pixelArtContainer.getChildren().clear();
        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        pixelArtContainer.getChildren().add(createGridPanePixelArt(batteryStatus.BatteryLifePercent));
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    private void drawHeart (GridPane grid, int[][] heartPattern){
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

    private HBox createGridPanePixelArt(byte batteryLife) {

        System.out.println("batteryLife : " + batteryLife);
        batteryLife = 79;
        // Create 5 heart, with 5 GridPane
        ArrayList<GridPane> batteryBar = new ArrayList<>();
        int numOfFullHeart = batteryLife / 20;
        int odd = batteryLife % 20;
        int numOfHeartFilled = 0;
        // Each heart will present 20% of the battery
        for(int i = 0; i < numOfFullHeart; i++) {
            GridPane grid = new GridPane();
            grid.setHgap(2);
            grid.setVgap(2);
            grid.setStyle("-fx-background-color: white;");
            grid.setPadding(new Insets(10));
            drawHeart(grid, HeartPattern.HEART_FULL);
            batteryBar.add(grid);
        }
        //        Fill the rest with empty heart

        // the one in the middle should represent the half heart
        if(odd != 0){
            if(odd >= 10){
                // Blink between full heart to half heart

            }else{
                // Blink between half heart to empty heart

            }
        }

        for(int i = 0; i < (5 - numOfFullHeart + 1); i++) {
            GridPane grid = new GridPane();
            grid.setHgap(2);
            grid.setVgap(2);
            grid.setStyle("-fx-background-color: white;");
            grid.setPadding(new Insets(10));
            drawHeart(grid, HeartPattern.HEART_EMPTY);
            batteryBar.add(grid);
        }


        HBox container = new HBox(10);
        container.getChildren().addAll(batteryBar);
        return container;
    }
}
