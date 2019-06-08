package com.mathgame.appdata;

public interface Constant {
    String EMPTY                  = "";
    String GENERATOR_NOTIFICATION = "generator_notification";
    int    QUESTION_DELAY_TIME    = 300;

    interface MathSign {
        String ADDITION       = "+";
        String SUBTRACTION    = "-";
        String MULTIPLICATION = "*";
        String DIVISION       = "/";
        String PERCENTAGE     = "%";
        String SQUARE_ROOT    = "sqrt()";
    }

    interface QuestionFormat {
        String FORMAT_1  = "a + b = ?";
        String FORMAT_2  = "a - b = ?";
        String FORMAT_3  = "a * b = ?";
        String FORMAT_4  = "a / b = ?";
        String FORMAT_5  = "a + b + c = ?";
        String FORMAT_6  = "a + b - c = ?";
        String FORMAT_7  = "a + b * c = ?";
        String FORMAT_8  = "a + b / c = ?";
        String FORMAT_9  = "a - b + c = ?";
        String FORMAT_10 = "a - b - c = ?";
        String FORMAT_11 = "a - b * c = ?";
        String FORMAT_12 = "a - b / c = ?";
        String FORMAT_13 = "a * b + c = ?";
        String FORMAT_14 = "a * b - c = ?";
        String FORMAT_15 = "a * b * c = ?";
        String FORMAT_16 = "a * b / c = ?";
        String FORMAT_17 = "a / b * c = ?";
        String FORMAT_18 = "a / b - c = ?";
        String FORMAT_19 = "a / b + ? = c";
        String FORMAT_20 = "a * b - ? = c";
        String FORMAT_21 = "a * (b / c) = ?";
        String FORMAT_22 = "(a * b) - ? = c";
        String FORMAT_23 = "(a * b) / c = ?";
        String FORMAT_24 = "(a * ?) - b = c";
        String FORMAT_25 = "(a * ?) / b = c";
        String FORMAT_26 = "(a * b) * ? = c";
        String FORMAT_27 = "(sqt(a) * ?) - b = c";
        String FORMAT_28 = "(a * ?) - sqt(b) = c";
        String FORMAT_29 = "a + ? = b % c";
        String FORMAT_30 = "(a * b) - sqt(c) = ?";
    }

    interface DifficultyLevel {
        int SMALL  = 0;
        int MEDIUM = 1;
        int LARGE  = 2;
    }

    interface JSONFileNames {
        String TUTORIAL_FILE   = "tutorial.json";
        String CAREER_QUESTION = "career_question.json";
    }

    interface AnswerType {
        int CORRECT   = 1;
        int INCORRECT = 2;
        int SKIPPED   = 3;
    }
}

