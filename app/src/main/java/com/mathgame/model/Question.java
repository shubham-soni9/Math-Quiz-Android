package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private           String   id;
    private           String   operation;
    private           String   question;
    private           String   answer;
    private           int      a;
    private           int      b;
    private           int      answerType;
    private transient Listener listener;
    private String  userInput;
    private boolean correct;

    public Question() {
    }

    private Question(Parcel in) {
        id = in.readString();
        operation = in.readString();
        question = in.readString();
        answer = in.readString();
        a = in.readInt();
        b = in.readInt();
        answerType = in.readInt();
        userInput = in.readString();
        correct = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(operation);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeInt(a);
        dest.writeInt(b);
        dest.writeInt(answerType);
        dest.writeString(userInput);
        dest.writeByte((byte) (correct ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
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
