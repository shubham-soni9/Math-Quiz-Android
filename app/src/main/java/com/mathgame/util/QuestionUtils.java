package com.mathgame.util;

import android.util.Pair;

import com.mathgame.appdata.Constant;
import com.mathgame.model.CustomMode;

public class QuestionUtils {
    private static final int MAXIMUM=20;
    private static final int MINIMUM=2;
    public static Pair<String, String> getQuestionWithAnswer(CustomMode customMode) {
        String[] operations = customMode.getMathOperations().split(" ");
        String question = Constant.EMPTY;
        String answer = Constant.EMPTY;
        if (customMode.getNumberOfVariables() == 2) {
            int a = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
            int b = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);

            String chosenOperation = operations[RandomUtils.getRandomInt(operations.length - 1)];

            while (a == b) {
                a = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
                b = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
            }

            int questionType = RandomUtils.getRandomInt(3, 1);
            if (questionType == 1) {
                question = a + " " + chosenOperation + " " + b + " = ?";
                switch (chosenOperation) {
                    case Constant.MathSign.ADDITION:
                        answer = String.valueOf(a + b);
                        break;
                    case Constant.MathSign.SUBTRACTION:
                        answer = String.valueOf(a - b);
                        break;
                    case Constant.MathSign.MULTIPLICATION:
                        answer = String.valueOf(a * b);
                        break;
                    case Constant.MathSign.DIVISION:
                        answer = String.valueOf(a / b);
                        break;
                    case Constant.MathSign.PERCENTAGE:
                        answer = String.valueOf(a % b);
                        break;
                }
            } else if (questionType == 2) {
                question = a + " " + chosenOperation + " ? " + " = " + b;

                switch (chosenOperation) {
                    case "+":
                        answer = String.valueOf(b - a);
                        break;
                    case "-":
                        answer = String.valueOf(a - b);
                        break;
                    case "*":
                        answer = String.valueOf(b / a);
                        break;
                    case "/":
                        answer = String.valueOf(b * a);
                        break;
                    case "%":
                        answer = String.valueOf(b % a);
                        break;
                }
            } else if (questionType == 3) {
                question = "? " + chosenOperation + " " + a + " = " + b;
                switch (chosenOperation) {
                    case "+":
                        answer = String.valueOf(b - a);
                        break;
                    case "-":
                        answer = String.valueOf(b + a);
                        break;
                    case "*":
                        answer = String.valueOf(b / a);
                        break;
                    case "/":
                        answer = String.valueOf(b * a);
                        break;
                    case "%":
                        answer = String.valueOf(b % a);
                        break;
                }
            }
        }
        return new Pair<>(question, answer);
    }
}
