package org.example.pixelbattery;


import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.example.pixelbattery.Service.Kernel32;
import org.example.pixelbattery.model.HEART_STATE;
import org.example.pixelbattery.model.HeartPattern;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML
    private HBox pixelArtContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateBatteryDisplay();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> updateBatteryDisplay()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateBatteryDisplay() {
        pixelArtContainer.getChildren().clear();
        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        pixelArtContainer.getChildren().add(createGridPanePixelArt(batteryStatus.BatteryLifePercent));

        //pixelArtContainer.getChildren().add(createGridPanePixelArt((byte)65));

    }


    private HBox createGridPanePixelArt(byte batteryLife) {
        System.out.println("batteryLife : " + batteryLife);
        final int MAX_HEART = 5;
        // Create 5 heart, with 5 GridPane
        ArrayList<GridPane> batteryBar = new ArrayList<>();
        int numOfFullHeart = batteryLife / 20;
        // Each heart will present 20% of the battery
        for(int i = 0; i < numOfFullHeart; i++) {
            batteryBar.add(createHeartGrid(HEART_STATE.FULL));
        }

        int remainder = batteryLife % 20;
        if(remainder != 0 ){
            if(remainder == 10) {
                batteryBar.add(createHeartGrid(HEART_STATE.HALF_FULL));
                numOfFullHeart++;
            } else if(0 < remainder && remainder <= 9){
                // Blink between empty and half full
                GridPane blinkGrid = createHeartGrid(HEART_STATE.BLINK_LEFT_HALF_FULL);
                batteryBar.add(blinkGrid);

                AnimationTimer timer = new AnimationTimer() {
                    long lastBlink = 0;
                    boolean isVisible = true;
                    @Override
                    public void handle(long now) {

                        if (now - lastBlink >= 500_000_000) {
                            isVisible = !isVisible;
                            updateBlinkingGrid( blinkGrid,HEART_STATE.BLINK_LEFT_HALF_FULL,isVisible );
                            lastBlink = now;
                        }
                    }
                };
                timer.start();

                numOfFullHeart++;
            } else if(10 < remainder && remainder < 19){
                // Blink between half full and full
                GridPane blinkGrid = createHeartGrid(HEART_STATE.BLINK_RIGHT_HALF_FULL);
                batteryBar.add(blinkGrid);
                AnimationTimer timer = new AnimationTimer() {
                    long lastBlink = 0;
                    boolean isVisible = true;
                    @Override
                    public void handle(long now) {

                        if (now - lastBlink >= 500_000_000) {
                            isVisible = !isVisible;
                            updateBlinkingGrid( blinkGrid,HEART_STATE.BLINK_RIGHT_HALF_FULL ,isVisible);
                            lastBlink = now;
                        }
                    }
                };
                timer.start();
                numOfFullHeart++;
            }

        }

        for(int i = 0; i < (MAX_HEART - numOfFullHeart ); i++) {
            batteryBar.add(createHeartGrid(HEART_STATE.EMPTY));
        }
        HBox container = new HBox(10);
        container.getChildren().addAll(batteryBar);
        return container;
    }

    private GridPane createHeartGrid(HEART_STATE state){
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setStyle("-fx-background-color: white;");
        grid.setPadding(new Insets(10));
        switch (state){
            case FULL :
                drawHeart(grid, HeartPattern.HEART_FULL);
                break;
            case HALF_FULL :
                drawHeart(grid, HeartPattern.HEART_HALF);
                break;
            case EMPTY :
                drawHeart(grid, HeartPattern.HEART_EMPTY);
                break;
            case BLINK_RIGHT_HALF_FULL :
                drawHeart(grid, HeartPattern.HEART_FULL);
                break;
            case BLINK_LEFT_HALF_FULL :
                drawHeart(grid, HeartPattern.HEART_HALF);
                break;
        }
        return grid;
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
                        pixel.setFill(HeartPattern.COLOR_BROWN);
                        break;
                    case 2:
                        pixel.setFill(HeartPattern.COLOR_RED);
                        break;
                }
                grid.add(pixel, col, row);
            }
        }
    }

    private void updateBlinkingGrid(GridPane pane, HEART_STATE state, boolean isVisible) {
        Color color = isVisible ? HeartPattern.COLOR_RED : Color.WHITE;
        int[][] heartPattern = HeartPattern.HEART_FULL;

        for (int row = 0; row < pane.getRowCount(); row++) {
            for (int col = 0; col < pane.getColumnCount(); col++) {
                Node node = getNodeByRowCol(pane, row, col);
                if(state == HEART_STATE.BLINK_RIGHT_HALF_FULL && col >= pane.getColumnCount()/2) {
                    if (heartPattern[row][col] == 2) {
                        ((Rectangle) node).setFill(color);
                    }
                }
                if(state == HEART_STATE.BLINK_LEFT_HALF_FULL && col < pane.getColumnCount()/2) {
                    if (heartPattern[row][col] == 2) {
                        ((Rectangle) node).setFill(color);
                    }
                }
            }
        }
    }

    public Node getNodeByRowCol(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);

            // Handle null (defaults to 0)
            int r = (nodeRow == null) ? 0 : nodeRow;
            int c = (nodeCol == null) ? 0 : nodeCol;

            if (r == row && c == col) {
                return node;
            }
        }
        return null;
    }
}
