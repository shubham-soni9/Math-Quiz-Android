package com.mathgame.appdata;

public interface Constant {
    String EMPTY = "";
    String GENERATOR_NOTIFICATION = "generator_notification";

    interface MathSign {
        String ADDITION       = "+";
        String SUBTRACTION    = "-";
        String MULTIPLICATION = "*";
        String DIVISION       = "/";
        String PERCENTAGE     = "%";
        String SQUARE_ROOT    = "sqrt()";
    }

    interface DifficultyLevel {
        int SMALL  = 0;
        int MEDIUM = 1;
        int LARGE  = 2;
    }

}
