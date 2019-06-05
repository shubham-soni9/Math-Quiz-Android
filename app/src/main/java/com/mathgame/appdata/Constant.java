package com.mathgame.appdata;

public interface Constant {
    String EMPTY                  = "";
    String GENERATOR_NOTIFICATION = "generator_notification";
    int QUESTION_DELAY_TIME=300;

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

    interface JSONFileNames {
        String TUTORIAL_FILE = "tutorial.json";
        String CAREER_QUESTION= "career_question.json";
    }

    interface AnswerType{
        int CORRECT=1;
        int INCORRECT=2;
        int SKIPPED=3;
    }
}

