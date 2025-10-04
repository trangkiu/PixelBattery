package org.example.pixelbattery.model;


import javafx.scene.paint.Color;

public class HeartPattern {
    public static final Color COLOR_BROWN = Color.web("#8B0000");
    public static final Color COLOR_RED = Color.web("#FF0000");
    public static final Color COLOR_WHITE = Color.WHITE;
    public static final Color COLOR_BLUE = Color.rgb(47 , 98, 162, 0.9);

    public static final int[][]  HEART_FULL = {
            {0, 1, 1, 0, 0, 1, 1, 0},
            {1, 2, 2, 1, 1, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 1},
            {0, 1, 2, 2, 2, 2, 1, 0},
            {0, 0, 1, 2, 2, 1, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0}

    };

    public static final int[][]  HEART_EMPTY = {
            {0, 1, 1, 0, 0, 1, 1, 0},
            {1, 3, 3, 1, 1, 3, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 1},
            {0, 1, 3, 3, 3, 3, 1, 0},
            {0, 0, 1, 3, 3, 1, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0}

    };

    public static final int[][]  HEART_HALF = {
            {0, 1, 1, 0, 0, 1, 1, 0},
            {1, 2, 2, 1, 1, 3, 3, 1},
            {1, 2, 2, 2, 3, 3, 3, 1},
            {1, 2, 2, 2, 3, 3, 3, 1},
            {0, 1, 2, 2, 3, 3, 1, 0},
            {0, 0, 1, 2, 3, 1, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0}

    };

}

