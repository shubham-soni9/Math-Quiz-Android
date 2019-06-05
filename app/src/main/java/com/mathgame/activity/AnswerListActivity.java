package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mathgame.R;
import com.mathgame.adapter.AnswerAdapter;
import com.mathgame.model.Question;
import com.mathgame.structure.BaseActivity;

public class AnswerListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        init();
        setData();
    }

    private void init() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.results);
    }

    private void setData() {
        RecyclerView rvQuestionList=findViewById(R.id.rvQuestionList);
        rvQuestionList.setLayoutManager(new LinearLayoutManager(this));
        AnswerAdapter answerAdapter=new AnswerAdapter(getIntent().<Question>getParcelableArrayListExtra(Question.class.getName()));
        rvQuestionList.setAdapter(answerAdapter);
    }

}
