package com.mathgame.util;

import android.util.Pair;

import com.mathgame.appdata.Constant;
import com.mathgame.model.CustomMode;

import java.text.DecimalFormat;

public class QuestionUtils {
    private static final int MAXIMUM = 20;
    private static final int MINIMUM = 2;

    public static Pair<String, String> getQuestionWithAnswer(CustomMode customMode) {
        DecimalFormat dFormat = new DecimalFormat("###.#");
        String[] operations = customMode.getMathOperations().split(" ");
        String question = Constant.EMPTY;
        String answer = Constant.EMPTY;
        if (customMode.getNumberOfVariables() == 2) {
            double a = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
            double b = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);

            String chosenOperation = operations[RandomUtils.getRandomInt(operations.length - 1)];

            while (a == b) {
                b = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
            }

            int questionType = RandomUtils.getRandomInt(3, 1);
            if (questionType == 1) {
                question = dFormat.format(a) + " " + chosenOperation + " " + dFormat.format(b) + " = ?";
                switch (chosenOperation) {
                    case Constant.MathSign.ADDITION:
                        answer = dFormat.format(a + b);
                        break;
                    case Constant.MathSign.SUBTRACTION:
                        answer = dFormat.format(a - b);
                        break;
                    case Constant.MathSign.MULTIPLICATION:
                        answer = dFormat.format(a * b);
                        break;
                    case Constant.MathSign.DIVISION:
                        answer = dFormat.format(a / b);
                        break;
                    case Constant.MathSign.PERCENTAGE:
                        answer = dFormat.format(a % b);
                        break;
                }
            } else if (questionType == 2) {
                question = dFormat.format(a) + " " + chosenOperation + " ? " + " = " + dFormat.format(b);

                switch (chosenOperation) {
                    case "+":
                        answer = dFormat.format(b - a);
                        break;
                    case "-":
                        answer = dFormat.format(a - b);
                        break;
                    case "*":
                        answer = dFormat.format(b / a);
                        break;
                    case "/":
                        answer = dFormat.format(b * a);
                        break;
                    case "%":
                        answer = dFormat.format(b % a);
                        break;
                }
            } else if (questionType == 3) {
                question = "? " + chosenOperation + " " + dFormat.format(a) + " = " + dFormat.format(b);
                switch (chosenOperation) {
                    case "+":
                        answer = dFormat.format(b - a);
                        break;
                    case "-":
                        answer = dFormat.format(b + a);
                        break;
                    case "*":
                        answer = dFormat.format(b / a);
                        break;
                    case "/":
                        answer = dFormat.format(b * a);
                        break;
                    case "%":
                        answer = dFormat.format(b % a);
                        break;
                }
            }
        }
        return new Pair<>(question,answer);
    }
}
