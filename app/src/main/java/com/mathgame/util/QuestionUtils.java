package com.mathgame.util;

import android.util.Pair;

import com.mathgame.appdata.Constant;
import com.mathgame.model.CustomMode;

public class QuestionUtils {
    public static Pair<String, String> getQuestionWithAnswer(CustomMode customMode) {
        String[] operations = customMode.getMathOperations().split(" ");
        String question = Constant.EMPTY;
        String answer = Constant.EMPTY;
        if (customMode.getNumberOfVariables() == 2) {
            int a = RandomUtils.getRandomInteger(9, 1);
            int b = RandomUtils.getRandomInteger(9, 1);
            String chosenOperation = operations[RandomUtils.getRandomInteger(operations.length - 1)];
            int questionType = RandomUtils.getRandomInteger(3, 1);

            if (questionType == 1) {
                question = a + " " + chosenOperation + " " + b + " = ?";
                switch (chosenOperation) {
                    case "+":
                        answer = String.valueOf(a + b);
                        break;
                    case "-":
                        answer = String.valueOf(a - b);
                        break;
                    case "*":
                        answer = String.valueOf(a * b);
                        break;
                    case "/":
                        answer = String.valueOf(a / b);
                        break;
                    case "%":
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
