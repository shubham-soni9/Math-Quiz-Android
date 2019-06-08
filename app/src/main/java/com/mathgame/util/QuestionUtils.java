package com.mathgame.util;

import com.mathgame.appdata.Codes;
import com.mathgame.appdata.Constant;
import com.mathgame.model.CLevel;
import com.mathgame.model.CustomMode;
import com.mathgame.model.Question;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

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
            question = question + " = " + answerPrediction;
            mQuestion.setQuestion(question);
            mQuestion.setCorrect(answerPrediction.equalsIgnoreCase(answer));
        }
        else if(customMode.getGameType() == Codes.GameType.MULTIPLE_CHOICE.value){
            ArrayList<String> options = new ArrayList<>();
            options.add(mQuestion.getAnswer());
             maximum = 99;
             minimum = 2;
            switch (mQuestion.getOperation()) {
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

            if (!mQuestion.getOperation().equals(Constant.MathSign.SQUARE_ROOT)) {
                while ((maximum - minimum) < 4) {
                    maximum = ++maximum;
                    minimum = --minimum;
                }
            }
            for (int i = 0; i < 3; i++) {
                String wrongOption;
                if (mQuestion.getOperation().equals(Constant.MathSign.SQUARE_ROOT)) {
                    wrongOption = String.valueOf(twoDecimalFormatter.format(RandomUtils.getRandomDouble(maximum, minimum)));
                } else {
                    wrongOption = String.valueOf(RandomUtils.getRandomInt(maximum, minimum));
                }
                for (int j = 0; j < options.size(); j++) {
                    String value = options.get(j);
                    if (wrongOption.equals(value) || wrongOption.equalsIgnoreCase(mQuestion.getAnswer())) {
                        if (mQuestion.getOperation().equals(Constant.MathSign.SQUARE_ROOT)) {
                            wrongOption = String.valueOf(twoDecimalFormatter.format(RandomUtils.getRandomDouble(maximum, minimum)));
                        } else {
                            wrongOption = String.valueOf(RandomUtils.getRandomInt(maximum, minimum));
                        }
                        j = 0;
                    }
                }
                options.add(wrongOption);
            }

            Collections.shuffle(options);
            mQuestion.setOption_1(options.get(0));
            mQuestion.setOption_2(options.get(1));
            mQuestion.setOption_3(options.get(2));
            mQuestion.setOption_4(options.get(3));
        }
        mQuestion.setId(Utils.getUniqueId());
        return mQuestion;
    }

    public static Question getLevelQuestionWithAnswer(CLevel level) {
        DecimalFormat dFormat = new DecimalFormat("###.#");
        DecimalFormat twoDecimalFormatter = new DecimalFormat("#.##");
        String question=Constant.EMPTY;
        String answer = Constant.EMPTY;

        int maximum;
        int minimum;
        switch (level.getDifficulty()) {
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
        int c = RandomUtils.getRandomInt(maximum, minimum);

        switch (level.getQuestionSample()) {
            case Constant.QuestionFormat.FORMAT_1:
                question = dFormat.format(a) + " + " + dFormat.format(b) + " = ?";
                answer = dFormat.format(a + b);
                break;
            case Constant.QuestionFormat.FORMAT_2:
                question = dFormat.format(a) + " - " + dFormat.format(b) + " = ?";
                answer = dFormat.format(a - b);
                break;
            case Constant.QuestionFormat.FORMAT_3:
                question = dFormat.format(a) + " * " + dFormat.format(b) + " = ?";
                answer = dFormat.format(a * b);
                break;
            case Constant.QuestionFormat.FORMAT_4:
                question = dFormat.format(a) + " / " + dFormat.format(b) + " = ?";
                answer = dFormat.format(a / b);
                break;
            case Constant.QuestionFormat.FORMAT_5:
                question = dFormat.format(a) + " + " + dFormat.format(b) + " + " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a + b + c);
                break;
            case Constant.QuestionFormat.FORMAT_6:
                question = dFormat.format(a) + " + " + dFormat.format(b) + " - " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a + b - c);
                break;
            case Constant.QuestionFormat.FORMAT_7:
                question = dFormat.format(a) + " + " + dFormat.format(b) + " * " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a + b * c);
                break;
            case Constant.QuestionFormat.FORMAT_8:
                question = dFormat.format(a) + " + " + dFormat.format(b) + " / " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a + b / c);
                break;
            case Constant.QuestionFormat.FORMAT_9:
                question = dFormat.format(a) + " - " + dFormat.format(b) + " + " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a - b + c);
                break;
            case Constant.QuestionFormat.FORMAT_10:
                question = dFormat.format(a) + " - " + dFormat.format(b) + " - " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a - b - c);
                break;
            case Constant.QuestionFormat.FORMAT_11:
                question = dFormat.format(a) + " - " + dFormat.format(b) + " * " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a - b * c);
                break;
            case Constant.QuestionFormat.FORMAT_12:
                question = dFormat.format(a) + " - " + dFormat.format(b) + " / " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a - b / c);
                break;
            case Constant.QuestionFormat.FORMAT_13:
                question = dFormat.format(a) + " * " + dFormat.format(b) + " + " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a * b + c);
                break;
            case Constant.QuestionFormat.FORMAT_14:
                question = dFormat.format(a) + " * " + dFormat.format(b) + " - " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a * b - c);
                break;
            case Constant.QuestionFormat.FORMAT_15:
                question = dFormat.format(a) + " * " + dFormat.format(b) + " * " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a * b * c);
                break;
            case Constant.QuestionFormat.FORMAT_16:
                question = dFormat.format(a) + " * " + dFormat.format(b) + " / " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a * b / c);
                break;
            case Constant.QuestionFormat.FORMAT_17:
                question = dFormat.format(a) + " / " + dFormat.format(b) + " * " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a / b * c);
                break;
            case Constant.QuestionFormat.FORMAT_18:
                question = dFormat.format(a) + " / " + dFormat.format(b) + " - " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a / b - c);
                break;
            case Constant.QuestionFormat.FORMAT_19:
                question = dFormat.format(a) + " / " + dFormat.format(b) + " + " + dFormat.format(c) + " = ?";
                answer = dFormat.format(a / b + c);
                break;
            case Constant.QuestionFormat.FORMAT_20:
                question = dFormat.format(a) + " * " + dFormat.format(b) + " - ? = " + dFormat.format(c);
                answer = dFormat.format(a * b + c);
                break;
            case Constant.QuestionFormat.FORMAT_21:
                question = dFormat.format(a) + " * (" + dFormat.format(b) + " / " + dFormat.format(c) + ") = ?";
                answer = dFormat.format(a * (b / c));
                break;
            case Constant.QuestionFormat.FORMAT_22:
                question = "(" + dFormat.format(a) + " * " + dFormat.format(b) + ") - ? = " + dFormat.format(c);
                answer = dFormat.format(a * b + c);
                break;
            case Constant.QuestionFormat.FORMAT_23:
                question = "(" + dFormat.format(a) + " * " + dFormat.format(b) + ") / " + dFormat.format(c) + " = ?";
                answer = dFormat.format((a * b) / c);
                break;
            case Constant.QuestionFormat.FORMAT_24:
                question = "(" + dFormat.format(a) + " * ?) - " + dFormat.format(b) + " = " + dFormat.format(c);
                answer = dFormat.format(c + b / a);
                break;
            case Constant.QuestionFormat.FORMAT_25:
                question = "(" + dFormat.format(a) + " * ?)" + " / " + dFormat.format(b) + " = " + dFormat.format(c);
                answer = dFormat.format(c * b / a);
                break;
            case Constant.QuestionFormat.FORMAT_26:
                question = "(" + dFormat.format(a) + " * " + dFormat.format(b) + ") * ?" + " = " + dFormat.format(c);
                answer = dFormat.format(c / b / a);
                break;
            case Constant.QuestionFormat.FORMAT_27:
                question = "sqt(" + dFormat.format(a) + ") * ?) - " + dFormat.format(b) + " = " + dFormat.format(c);
                answer = dFormat.format(c + b / Math.sqrt(a));
                break;
            case Constant.QuestionFormat.FORMAT_28:
                question = "(" + dFormat.format(a) + " * ?) - sqt(" + dFormat.format(b) + ") = " + dFormat.format(c);
                answer = dFormat.format(c + Math.sqrt(c) / a);
                break;
            case Constant.QuestionFormat.FORMAT_29:
                question = dFormat.format(a) + " + ? = " + dFormat.format(b) + " % " + dFormat.format(c);
                answer = dFormat.format(b % c - a);
                break;
            case Constant.QuestionFormat.FORMAT_30:
                question = "(" + dFormat.format(a) + " * " + dFormat.format(b) + ") - sqt(" + dFormat.format(c) + ") = ?";
                answer = dFormat.format((a * b) - Math.sqrt(c));
                break;

        }

        mQuestion.setQuestion(question);
        mQuestion.setAnswer(answer);
        mQuestion.setId(Utils.getUniqueId());
        return mQuestion;
    }


}
