package com.mathgame.util;

import com.mathgame.appdata.Constant;
import com.mathgame.model.CustomMode;
import com.mathgame.model.Question;

import java.text.DecimalFormat;

public class QuestionUtils {

    public static Question getQuestionWithAnswer(CustomMode customMode) {
        DecimalFormat dFormat = new DecimalFormat("###.#");
        String[] operations = customMode.getMathOperations().split(" ");
        String question = Constant.EMPTY;
        String answer = Constant.EMPTY;
        int MAXIMUM = customMode.getMaximum();
        int MINIMUM = customMode.getMinimum();
        Question mQuestion = new Question();
        if (customMode.getNumberOfVariables() == 2) {
            double a = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
            double b = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);

            String chosenOperation = operations[RandomUtils.getRandomInt(operations.length - 1)];
            String mathOperation = chosenOperation;
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
                    case Constant.MathSign.ADDITION:
                        mathOperation = Constant.MathSign.SUBTRACTION;
                        answer = dFormat.format(b - a);
                        break;
                    case Constant.MathSign.SUBTRACTION:
                        mathOperation = Constant.MathSign.SUBTRACTION;
                        answer = dFormat.format(a - b);
                        break;
                    case Constant.MathSign.MULTIPLICATION:
                        mathOperation = Constant.MathSign.DIVISION;
                        answer = dFormat.format(b / a);
                        break;
                    case Constant.MathSign.DIVISION:
                        mathOperation = Constant.MathSign.MULTIPLICATION;
                        answer = dFormat.format(b * a);
                        break;
                    case Constant.MathSign.PERCENTAGE:
                        mathOperation = Constant.MathSign.PERCENTAGE;
                        answer = dFormat.format(b % a);
                        break;
                }
            } else if (questionType == 3) {
                question = "? " + chosenOperation + " " + dFormat.format(a) + " = " + dFormat.format(b);
                switch (chosenOperation) {
                    case Constant.MathSign.ADDITION:
                        mathOperation = Constant.MathSign.SUBTRACTION;
                        answer = dFormat.format(b - a);
                        break;
                    case Constant.MathSign.SUBTRACTION:
                        mathOperation = Constant.MathSign.ADDITION;
                        answer = dFormat.format(b + a);
                        break;
                    case Constant.MathSign.MULTIPLICATION:
                        mathOperation = Constant.MathSign.DIVISION;
                        answer = dFormat.format(b / a);
                        break;
                    case Constant.MathSign.DIVISION:
                        mathOperation = Constant.MathSign.MULTIPLICATION;
                        answer = dFormat.format(b * a);
                        break;
                    case Constant.MathSign.PERCENTAGE:
                        mathOperation = Constant.MathSign.PERCENTAGE;
                        answer = dFormat.format(b % a);
                        break;
                }
            }
            mQuestion.setOperation(mathOperation);
            mQuestion.setQuestion(question);
            mQuestion.setAnswer(answer);
        }
        return mQuestion;
    }
}
