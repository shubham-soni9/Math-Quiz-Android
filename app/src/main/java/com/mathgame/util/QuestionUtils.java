package com.mathgame.util;

import com.mathgame.appdata.Constant;
import com.mathgame.model.CustomMode;
import com.mathgame.model.Question;

import java.text.DecimalFormat;

import static android.content.ContentValues.TAG;

public class QuestionUtils {

    public static Question getQuestionWithAnswer(CustomMode customMode) {
        DecimalFormat dFormat = new DecimalFormat("###.#");
        String[] operations = customMode.getMathOperations().split(" ");
        String question;
        String answer = Constant.EMPTY;

        int MAXIMUM;
        int MINIMUM;
        switch (customMode.getDifficulty()) {
            case Constant.DifficultyLevel.SMALL:
                MAXIMUM = 9;
                MINIMUM = 2;
                break;
            case Constant.DifficultyLevel.MEDIUM:
                MAXIMUM = 99;
                MINIMUM = 10;
                break;
            case Constant.DifficultyLevel.LARGE:
                MAXIMUM = 100;
                MINIMUM = 999;
                break;
            default:
                MAXIMUM = 9;
                MINIMUM = 2;
        }
        Question mQuestion = new Question();
        if (customMode.getNumberOfVariables() == 2) {
            int a = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
            int b = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);

            String chosenOperation = operations[RandomUtils.getRandomInt(operations.length - 1)];

            if (chosenOperation.equals(Constant.MathSign.DIVISION)) {
                while (a <= b || (a % b != 0)) {
                    a = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
                    b = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
                }
            } else {
                while (a == b) {
                    Log.e(TAG, "Value of a::" + a + " b::" + b);
                    b = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
                    a = RandomUtils.getRandomInt(MAXIMUM, MINIMUM);
                }
            }
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
            mQuestion.setA(a);
            mQuestion.setB(b);
            mQuestion.setOperation(chosenOperation);
            mQuestion.setQuestion(question);
            mQuestion.setAnswer(answer);
        }
        return mQuestion;
    }
}
