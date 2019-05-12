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
        DecimalFormat twoDecimalFormatter = new DecimalFormat("#.##");
        String[] operations = customMode.getMathOperations().split(" ");
        String question;
        String answer = Constant.EMPTY;

        int maximum;
        int minimum;
        String chosenOperation = operations[RandomUtils.getRandomInt(operations.length - 1)];
        switch (customMode.getDifficulty()) {
            case Constant.DifficultyLevel.SMALL:
                maximum = 10;
                minimum = 2;
                break;
            case Constant.DifficultyLevel.MEDIUM:
                maximum = 99;
                minimum = 2;
                break;
            case Constant.DifficultyLevel.LARGE:
                maximum = 10;
                minimum = 999;
                break;
            default:
                maximum = 20;
                minimum = 2;
        }
        Question mQuestion = new Question();
        int a = RandomUtils.getRandomInt(maximum, minimum);
        int b = RandomUtils.getRandomInt(maximum, minimum);


        switch (chosenOperation) {
            case Constant.MathSign.DIVISION:
                while (a <= b || (a % b != 0)) {
                    Log.e(TAG, "a = " + a + " b = " + b);
                    a = RandomUtils.getRandomInt(maximum, minimum);
                    b = RandomUtils.getRandomInt(maximum, minimum);
                }
                break;
            case Constant.MathSign.PERCENTAGE:
                while (a <= b) {
                    a = RandomUtils.getRandomInt(maximum, minimum);
                    b = RandomUtils.getRandomInt(maximum, minimum);
                }
                break;
            case Constant.MathSign.SQUARE_ROOT:
                if (a == 1) {
                    a++;
                }
                break;
            default:
                while (a == b) {
                    a = RandomUtils.getRandomInt(maximum, minimum);
                    b = RandomUtils.getRandomInt(maximum, minimum);
                }
                break;
        }
        if (chosenOperation.equals(Constant.MathSign.SQUARE_ROOT)) {
            question = "sqrt(" + dFormat.format(a) + ")";
        } else {
            question = dFormat.format(a) + " " + chosenOperation + " " + dFormat.format(b) + " = ?";
        }
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
            case Constant.MathSign.SQUARE_ROOT:
                answer = twoDecimalFormatter.format(Math.sqrt(a));
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
                    case Constant.MathSign.PERCENTAGE:
                        maximum = 9;
                        minimum = 1;
                        break;
                    case Constant.MathSign.SQUARE_ROOT:
                        maximum = (int) (Math.sqrt(a) + 1);
                        minimum = (int) (Math.sqrt(a) - 1);
                        break;
                }
                int randomInt = RandomUtils.getRandomInt(3, 1);
                if (randomInt == 2) {
                    answerPrediction = answer;
                } else {
                    if (chosenOperation.equals(Constant.MathSign.SQUARE_ROOT)) {
                        answerPrediction = String.valueOf(twoDecimalFormatter.format(RandomUtils.getRandomDouble(maximum, minimum)));
                    } else {
                        answerPrediction = String.valueOf(RandomUtils.getRandomInt(maximum, minimum));
                    }
                }

            } else {
                answerPrediction = answer;
            }
            question = question.replace("?", answerPrediction);
            mQuestion.setQuestion(question);
            mQuestion.setCorrect(answerPrediction.equalsIgnoreCase(answer));
        }
        return mQuestion;
    }
}
