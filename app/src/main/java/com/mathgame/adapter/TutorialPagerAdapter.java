package com.mathgame.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mathgame.appdata.Keys;
import com.mathgame.fragment.TutorialListFragment;
import com.mathgame.model.Article;
import com.mathgame.model.Tutorial;

import java.util.ArrayList;
import java.util.List;

public class TutorialPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment>      mFragmentList = new ArrayList<>();
    private       ArrayList<Tutorial> tutorialList;

    /**
     * constructor for all agents pager adapter
     *
     * @param fragmentManager the provided fragment manager
     */
    public TutorialPagerAdapter(final Context context, final FragmentManager fragmentManager, ArrayList<Tutorial> tutorialList) {
        super(fragmentManager);
        this.tutorialList = tutorialList;
        Bundle bundle = new Bundle();
        String className = TutorialListFragment.class.getName();
        for (Tutorial tutorial : tutorialList) {
            bundle.putParcelableArrayList(Keys.Extras.TUTORIAL_LIST, tutorial.getArticleList());
            mFragmentList.add(TutorialListFragment.instantiate(context, className, bundle));
        }
    }

    @Override
    public Fragment getItem(final int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return tutorialList.get(position).getTutorialName();
    }
}