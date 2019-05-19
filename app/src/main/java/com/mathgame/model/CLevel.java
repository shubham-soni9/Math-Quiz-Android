package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CLevel implements Parcelable {
    private String question;
    private String opt_1;
    private String opt_2;
    private String opt_3;
    private String opt_4;
    private int    answer ;
    private int level;

    protected CLevel(Parcel in) {
        level=in.readInt();
        question = in.readString();
        opt_1 = in.readString();
        opt_2 = in.readString();
        opt_3 = in.readString();
        opt_4 = in.readString();
        answer = in.readInt();
    }

    public static final Creator<CLevel> CREATOR = new Creator<CLevel>() {
        @Override
        public CLevel createFromParcel(Parcel in) {
            return new CLevel(in);
        }

        @Override
        public CLevel[] newArray(int size) {
            return new CLevel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(level);
        dest.writeString(question);
        dest.writeString(opt_1);
        dest.writeString(opt_2);
        dest.writeString(opt_3);
        dest.writeString(opt_4);
        dest.writeInt(answer);
    }

    public String getQuestion() {
        return question;
    }

    public String getOpt1() {
        return opt_1;
    }

    public String getOpt2() {
        return opt_2;
    }

    public String getOpt3() {
        return opt_3;
    }

    public String getOpt4() {
        return opt_4;
    }

    public int getAnswer() {
        return answer;
    }

}
