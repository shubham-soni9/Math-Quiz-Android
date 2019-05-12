package com.mathgame.plugin.sudoku.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.plugin.sudoku.controller.SaveLoadStatistics;
import com.mathgame.plugin.sudoku.controller.helper.HighscoreInfoContainer;
import com.mathgame.plugin.sudoku.game.GameDifficulty;
import com.mathgame.plugin.sudoku.game.GameType;
import com.mathgame.structure.BaseActivity;

import java.util.List;

public class StatsActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.menu_highscore);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#024265")));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /**
         * The {@link ViewPager} that will host the section contents.
         */
        ViewPager mViewPager = findViewById(R.id.main_content);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
        //getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
        //return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int i = item.getItemId();
        if (i == R.id.action_reset) {
            SaveLoadStatistics.resetStats(this);
            mSectionsPagerAdapter.refresh(this);
            return true;
        } else if (i == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String   ARG_SECTION_NUMBER = "section_number";
        private              View     rootView;
        private              TextView difficultyView, averageTimeView, minTimeView;
        private RatingBar difficultyBarView;
        private String    s;
        private int       t;
        private int       totalTime  = 0;
        private int       totalGames = 0;
        private int       totalHints = 0;


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        void refresh(Context context) {
            resetGeneral();
            SaveLoadStatistics s = new SaveLoadStatistics(context);
            List<HighscoreInfoContainer> stats = s.loadStats(GameType.getValidGameTypes().get(getArguments().getInt(ARG_SECTION_NUMBER)));
            int j = 0;
            for (HighscoreInfoContainer i : stats) {
                updateGeneralInfo(i.getTime(), i.getNumberOfGames(), i.getNumberOfHintsUsed());
                setStats(i, j++);
            }
            setGeneralInfo();
        }

        private void resetGeneral() {
            totalTime = 0;
            totalHints = 0;
            totalGames = 0;
        }

        private String formatTime(int totalTime) {
            if (totalTime == 0) return "-";
            int seconds = totalTime % 60;
            int minutes = ((totalTime - seconds) / 60) % 60;
            int hours = (totalTime - minutes - seconds) / (3600);
            String h, m, s;
            s = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
            m = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
            h = (hours < 10) ? "0" + hours : String.valueOf(hours);
            return (h + ":" + m + ":" + s);

        }

        private void updateGeneralInfo(int time, int games, int hints) {
            totalHints += hints;
            totalGames += games;
            totalTime += time;
        }

        private void setGeneralInfo() {
            TextView generalInfoView;

            generalInfoView = rootView.findViewById(R.id.numb_of_hints);
            generalInfoView.setText(String.valueOf(totalHints));
            generalInfoView = rootView.findViewById(R.id.numb_of_total_games);
            generalInfoView.setText(String.valueOf(totalGames));
            generalInfoView = rootView.findViewById(R.id.numb_of_total_time);
            generalInfoView.setText(formatTime(totalTime));

        }

        private void setStats(HighscoreInfoContainer infos, int pos) {

            switch (pos) {
                case 0:
                    difficultyBarView = rootView.findViewById(R.id.first_diff_bar);
                    difficultyView = rootView.findViewById(R.id.first_diff_text);
                    averageTimeView = rootView.findViewById(R.id.first_ava_time);
                    minTimeView = rootView.findViewById(R.id.first_min_time);
                    break;
                case 1:
                    difficultyBarView = rootView.findViewById(R.id.second_diff_bar);
                    difficultyView = rootView.findViewById(R.id.second_diff_text);
                    averageTimeView = rootView.findViewById(R.id.second_ava_time);
                    minTimeView = rootView.findViewById(R.id.second_min_time);
                    break;
                case 2:
                    difficultyBarView = rootView.findViewById(R.id.third_diff_bar);
                    difficultyView = rootView.findViewById(R.id.third_diff_text);
                    averageTimeView = rootView.findViewById(R.id.third_ava_time);
                    minTimeView = rootView.findViewById(R.id.third_min_time);
                    break;
                case 3:
                    difficultyBarView = rootView.findViewById(R.id.fourth_diff_bar);
                    difficultyView = rootView.findViewById(R.id.fourth_diff_text);
                    averageTimeView = rootView.findViewById(R.id.fourth_ava_time);
                    minTimeView = rootView.findViewById(R.id.fourth_min_time);
                    break;
                default:
                    return;
            }
            difficultyBarView.setMax(GameDifficulty.getValidDifficultyList().size());
            difficultyBarView.setNumStars(GameDifficulty.getValidDifficultyList().size());
            difficultyBarView.setRating(infos.getDifficulty().ordinal());
            difficultyView.setText(rootView.getResources().getString(infos.getDifficulty().getStringResID()));
            t = (infos.getTimeNoHints() == 0) ? 0 : (infos.getTimeNoHints() / infos.getNumberOfGamesNoHints());
            averageTimeView.setText(formatTime(t));
            t = (infos.getMinTime() == Integer.MAX_VALUE) ? 0 : (infos.getMinTime());
            minTimeView.setText(formatTime(t));
        }


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_stats, container, false);
            this.rootView = rootView;
            resetGeneral();
            TextView textView = rootView.findViewById(R.id.section_label);

            SaveLoadStatistics s = new SaveLoadStatistics(this.getContext());
            List<HighscoreInfoContainer> stats = s.loadStats(GameType.getValidGameTypes().get(getArguments().getInt(ARG_SECTION_NUMBER)));


            int j = 0;
            for (HighscoreInfoContainer i : stats) {
                updateGeneralInfo(i.getTime(), i.getNumberOfGames(), i.getNumberOfHintsUsed());
                setStats(i, j++);
            }
            setGeneralInfo();

            ImageView imageView = rootView.findViewById(R.id.statistic_image);
            imageView.setImageResource(GameType.getValidGameTypes().get(getArguments().getInt(ARG_SECTION_NUMBER)).getResIDImage());

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {


        private final FragmentManager fm;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return GameType.getValidGameTypes().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(GameType.getValidGameTypes().get(position).getStringResID());
        }

        void refresh(Context context) {
            for (Fragment f : fm.getFragments()) {
                if (f instanceof PlaceholderFragment) {
                    ((PlaceholderFragment) f).refresh(context);
                }
            }
        }
    }
}
