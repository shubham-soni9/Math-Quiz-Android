package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Tutorial implements Parcelable {
    public static final Creator<Tutorial>  CREATOR = new Creator<Tutorial>() {
        @Override
        public Tutorial createFromParcel(Parcel in) {
            return new Tutorial(in);
        }

        @Override
        public Tutorial[] newArray(int size) {
            return new Tutorial[size];
        }
    };
    private             int                tutorial_id;
    private             String             tutorial_name;
    @SerializedName("articles")
    @Expose
    private             ArrayList<Article> articleList;

    protected Tutorial(Parcel in) {
        tutorial_id = in.readInt();
        tutorial_name = in.readString();
        articleList = in.createTypedArrayList(Article.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tutorial_id);
        dest.writeString(tutorial_name);
        dest.writeTypedList(articleList);
    }

    public int getTutorialId() {
        return tutorial_id;
    }

    public String getTutorialName() {
        return tutorial_name;
    }

    public ArrayList<Article> getArticleList() {
        return articleList;
    }
}
