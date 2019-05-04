package com.mathgame.util;

import com.mathgame.appdata.Codes;
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

        int maximum;
        int minimum;
        switch (customMode.getDifficulty()) {
            case Constant.DifficultyLevel.SMALL:
                maximum = 9;
                minimum = 2;
                break;
            case Constant.DifficultyLevel.MEDIUM:
                maximum = 99;
                minimum = 10;
                break;
            case Constant.DifficultyLevel.LARGE:
                maximum = 100;
                minimum = 999;
                break;
            default:
                maximum = 9;
                minimum = 2;
        }
        Question mQuestion = new Question();
        if (customMode.getNumberOfVariables() == 2) {
            int a = RandomUtils.getRandomInt(maximum, minimum);
            int b = RandomUtils.getRandomInt(maximum, minimum);

            String chosenOperation = operations[RandomUtils.getRandomInt(operations.length - 1)];

            if (chosenOperation.equals(Constant.MathSign.DIVISION)) {
                while (a <= b || (a % b != 0)) {
                    a = RandomUtils.getRandomInt(maximum, minimum);
                    b = RandomUtils.getRandomInt(maximum, minimum);
                }
            } else {
                while (a == b) {
                    Log.e(TAG, "Value of a::" + a + " b::" + b);
                    b = RandomUtils.getRandomInt(maximum, minimum);
                    a = RandomUtils.getRandomInt(maximum, minimum);
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
            if (customMode.getGameType() == Codes.GameType.YES_NO.value) {
                int randomCheck = RandomUtils.getRandomInt(3, 1);
                String answerPrediction;
                if (randomCheck == 2) {
                    switch (chosenOperation) {
                        case Constant.MathSign.ADDITION:
                            maximum = a + b;
                            minimum = b;
                            break;
                        case Constant.MathSign.SUBTRACTION:
                            maximum = a - b;
                            minimum = b;
                            break;
                        case Constant.MathSign.MULTIPLICATION:
                            maximum = a * b;
                            minimum = a;
                            break;
                        case Constant.MathSign.DIVISION:
                            maximum = a;
                            minimum = b;
                            break;
                    }
                    answerPrediction = String.valueOf(RandomUtils.getRandomInt(maximum, minimum));

                } else {
                    answerPrediction = answer;
                }
                question = question.replace("?", answerPrediction);
                mQuestion.setQuestion(question);
                mQuestion.setCorrect(answerPrediction.equalsIgnoreCase(answer));
            }
        }
        return mQuestion;
    }
}
