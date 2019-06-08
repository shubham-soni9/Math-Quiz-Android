package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private           String   id;
    private           String   operation;
    private           String   question;
    private           String   answer;
    private           int      answerType;
    private transient Listener listener;
    private String  userInput;
    private boolean correct;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;

    public Question() {
    }

    protected Question(Parcel in) {
        id = in.readString();
        operation = in.readString();
        question = in.readString();
        answer = in.readString();
        answerType = in.readInt();
        userInput = in.readString();
        correct = in.readByte() != 0;
        option_1 = in.readString();
        option_2 = in.readString();
        option_3 = in.readString();
        option_4 = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getOption_1() {
        return option_1;
    }

    public void setOption_1(String option_1) {
        this.option_1 = option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public void setOption_2(String option_2) {
        this.option_2 = option_2;
    }

    public String getOption_3() {
        return option_3;
    }

    public void setOption_3(String option_3) {
        this.option_3 = option_3;
    }

    public String getOption_4() {
        return option_4;
    }

    public void setOption_4(String option_4) {
        this.option_4 = option_4;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Object getView() {
        return listener != null ? listener.getView() : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(operation);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeInt(answerType);
        dest.writeString(userInput);
        dest.writeByte((byte) (correct ? 1 : 0));
        dest.writeString(option_1);
        dest.writeString(option_2);
        dest.writeString(option_3);
        dest.writeString(option_4);
    }

    public interface Listener {
        Object getView();
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

}
