package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.mathgame.R;
import com.mathgame.structure.BaseActivity;

public class MultipleQuestionActivity extends BaseActivity {
    private LinearLayout llQuestionList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_question);
        llQuestionList=findViewById(R.id.llQuestionList);
    }
}
