package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {
    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
    private             String           title;
    private             String           link;
    private             int              type;

    protected Article(Parcel in) {
        title = in.readString();
        link = in.readString();
        type = in.readInt();
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
    }

    public int getType() {
        return type;
    }
}
