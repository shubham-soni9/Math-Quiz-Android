package com.mathgame.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mathgame.fragment.TutorialListFragment;
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
    public TutorialPagerAdapter(final FragmentManager fragmentManager, ArrayList<Tutorial> tutorialList) {
        super(fragmentManager);
        this.tutorialList = tutorialList;
        for (Tutorial tutorial : tutorialList) {
            mFragmentList.add(TutorialListFragment.newInstance(tutorial.getArticleList()));
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