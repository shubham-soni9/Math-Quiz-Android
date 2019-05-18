package com.mathgame.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mathgame.R;
import com.mathgame.model.Article;
import com.mathgame.adapter.TutorialAdapter;
import com.mathgame.appdata.Keys;
import com.mathgame.util.Utils;

import java.util.ArrayList;

public class TutorialListFragment extends BaseFragment {
    private RecyclerView rvTutorialList;
    @Override
    protected int getContentView() {
        return R.layout.fragment_tutorial_list;
    }

    @Override
    protected void init(View parentView) {
         rvTutorialList = parentView.findViewById(R.id.rvTutorialList);
    }

    @Override
    protected void render(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<Article> articles = bundle.getParcelableArrayList(Keys.Extras.TUTORIAL_LIST);
            if (Utils.containData(articles)) {
                TutorialAdapter tutorialAdapter = new TutorialAdapter(articles);
                rvTutorialList.setLayoutManager(new LinearLayoutManager(context));
                rvTutorialList.setAdapter(tutorialAdapter);
            }
        }
    }
}
