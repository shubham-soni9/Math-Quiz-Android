package com.mathgame.appdata;

public interface Constant {
    String EMPTY = "";

    interface MathSign {
        String ADDITION       = "+";
        String SUBTRACTION    = "-";
        String MULTIPLICATION = "*";
        String DIVISION       = "/";
        String PERCENTAGE     = "%";
        String SQUARE_ROOT    = "sqt()";
    }

    interface DifficultyLevel {
        int SMALL  = 0;
        int MEDIUM = 1;
        int LARGE  = 2;
    }
}
