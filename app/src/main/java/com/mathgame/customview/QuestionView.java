package com.mathgame.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mathgame.R;
import com.mathgame.model.Question;

public class QuestionView {
    private Context context;
    private View    mView;

    public void QuestionView(Context context) {
        this.context = context;
        mView = LayoutInflater.from(context).inflate(R.layout.layout_question_view, null);

    }

    public View render(Question question) {
        return mView;
    }
}
