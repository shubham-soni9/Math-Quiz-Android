package com.mathgame.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mathgame.R;
import com.mathgame.plugin.sudoku.controller.GameStateManager;
import com.mathgame.plugin.sudoku.controller.NewLevelManager;
import com.mathgame.plugin.sudoku.controller.helper.GameInfoContainer;
import com.mathgame.plugin.sudoku.game.GameDifficulty;
import com.mathgame.plugin.sudoku.game.GameType;
import com.mathgame.structure.BaseActivity;

import java.util.LinkedList;
import java.util.List;


public class SudokuHomeActivity extends BaseActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {
    private RatingBar         difficultyBar;
    private TextView          difficultyText;
    private SharedPreferences settings;
    private ImageView         arrowLeft;
    private ImageView         arrowRight;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        NewLevelManager newLevelManager = NewLevelManager.getInstance(getApplicationContext(), settings);
        newLevelManager.checkAndRestock();
        setContentView(R.layout.activity_sudoku_home);
        final SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.scroller);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // set default gametype choice to whatever was chosen the last time.
        List<GameType> validGameTypes = GameType.getValidGameTypes();
        String lastChosenGameType = settings.getString("lastChosenGameType", GameType.Default_9x9.name());
        int index = validGameTypes.indexOf(Enum.valueOf(GameType.class, lastChosenGameType));
        mViewPager.setCurrentItem(index);
        arrowLeft = findViewById(R.id.arrow_left);
        arrowRight = findViewById(R.id.arrow_right);

        //care for initial postiton of the ViewPager
        arrowLeft.setVisibility((index == 0) ? View.INVISIBLE : View.VISIBLE);
        arrowRight.setVisibility((index == mSectionsPagerAdapter.getCount() - 1) ? View.INVISIBLE : View.VISIBLE);

        //Update ViewPager on change
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                arrowLeft.setVisibility((position == 0) ? View.INVISIBLE : View.VISIBLE);
                arrowRight.setVisibility((position == mSectionsPagerAdapter.getCount() - 1) ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        // Set the difficulty Slider to whatever was chosen the last time
        difficultyBar = findViewById(R.id.difficultyBar);
        difficultyText = findViewById(R.id.difficultyText);
        final LinkedList<GameDifficulty> difficultyList = GameDifficulty.getValidDifficultyList();
        difficultyBar.setNumStars(difficultyList.size());
        difficultyBar.setMax(difficultyList.size());
        difficultyBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 1) {
                    ratingBar.setRating(1);
                }
                difficultyText.setText(getString(difficultyList.get((int) ratingBar.getRating() - 1).getStringResID()));
            }
        });
        GameDifficulty lastChosenDifficulty = GameDifficulty.valueOf(settings.getString("lastChosenDifficulty", "Moderate"));
        difficultyBar.setRating(GameDifficulty.getValidDifficultyList().indexOf(lastChosenDifficulty) + 1);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("savesChanged", true);
        editor.apply();
        refreshContinueButton();
    }

    private void showMoreOption(final View view) {
        PopupMenu popup = new PopupMenu(this, view, Gravity.NO_GRAVITY, 0, R.style.appPopupMenu);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_drawer_sudoku, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.arrow_left:
                mViewPager.arrowScroll(View.FOCUS_LEFT);
                break;
            case R.id.arrow_right:
                mViewPager.arrowScroll(View.FOCUS_RIGHT);
                break;
            case R.id.continueButton:
                intent = new Intent(this, SudokuHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.playButton:
                GameType gameType = GameType.getValidGameTypes().get(mViewPager.getCurrentItem());
                int index = difficultyBar.getProgress() - 1;
                GameDifficulty gameDifficulty = GameDifficulty.getValidDifficultyList().get(index < 0 ? 0 : index);

                NewLevelManager newLevelManager = NewLevelManager.getInstance(getApplicationContext(), settings);
                if (newLevelManager.isLevelLoadable(gameType, gameDifficulty)) {
                    // save current setting for later
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("lastChosenGameType", gameType.name());
                    editor.putString("lastChosenDifficulty", gameDifficulty.name());
                    editor.apply();
                    // send everything to game activity
                    intent = new Intent(this, SudokuGameActivity.class);
                    intent.putExtra("gameType", gameType.name());
                    intent.putExtra("gameDifficulty", gameDifficulty.name());
                    startActivity(intent);
                } else {
                    newLevelManager.checkAndRestock();
                    Toast t = Toast.makeText(getApplicationContext(), R.string.generating, Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivMoreOption:
                showMoreOption(view);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshContinueButton();
    }

    private void refreshContinueButton() {
        // enable continue button if we have saved games.
        Button continueButton = findViewById(R.id.continueButton);
        GameStateManager fm = new GameStateManager(getBaseContext(), settings);
        List<GameInfoContainer> gic = fm.loadGameStateInfo();
        if (gic.size() > 0) {
            continueButton.setEnabled(true);
            continueButton.setBackgroundResource(R.drawable.standalone_button);
        } else {
            continueButton.setEnabled(false);
            continueButton.setBackgroundResource(R.drawable.inactive_button);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.menu_settings: /**/
                intent = new Intent(this, SudokuSettingsActivity.class);
                intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SudokuSettingsActivity.GamePreferenceFragment.class.getName());
                intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
                startActivity(intent);
                return true;
            case R.id.nav_highscore:
                intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class GameTypeFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */


        private static final String ARG_SECTION_NUMBER = "section_number";

        public GameTypeFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        static GameTypeFragment newInstance(int sectionNumber) {
            GameTypeFragment fragment = new GameTypeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);

            GameType gameType = GameType.getValidGameTypes().get(getArguments().getInt(ARG_SECTION_NUMBER));
            ImageView imageView = rootView.findViewById(R.id.gameTypeImage);
            imageView.setImageResource(gameType.getResIDImage());
            TextView textView = rootView.findViewById(R.id.section_label);
            textView.setText(getString(gameType.getStringResID()));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {
        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return GameTypeFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return GameType.getValidGameTypes().size();
        }
    }

}
