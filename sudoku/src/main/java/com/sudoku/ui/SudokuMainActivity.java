package com.sudoku.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sudoku.R;
import com.sudoku.controller.GameStateManager;
import com.sudoku.controller.NewLevelManager;
import com.sudoku.controller.helper.GameInfoContainer;
import com.sudoku.game.GameDifficulty;
import com.sudoku.game.GameType;



import java.util.LinkedList;
import java.util.List;


public class SudokuMainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RatingBar         difficultyBar;
    private TextView          difficultyText;
    private SharedPreferences settings;
    private ImageView         arrowLeft;
    private ImageView arrowRight;
    private DrawerLayout   drawer;
    private NavigationView mNavigationView;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = PreferenceManager.getDefaultSharedPreferences(this);

        NewLevelManager newLevelManager = NewLevelManager.getInstance(getApplicationContext(), settings);

        // check if we need to pre generate levels.
        newLevelManager.checkAndRestock();

        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

            /*
          The {@link android.support.v4.view.PagerAdapter} that will provide
          fragments for each of the sections. We use a
          {@link FragmentPagerAdapter} derivative, which will keep every
          loaded fragment in memory. If this becomes too memory intensive, it
          may be best to switch to a
          {@link android.support.v4.app.FragmentStatePagerAdapter}.
         */
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
        /*LayerDrawable stars = (LayerDrawable)difficultyBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);//Color for Stars fully selected
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.middleblue), PorterDuff.Mode.SRC_ATOP);//Color for Stars partially selected
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.lightblue), PorterDuff.Mode.SRC_ATOP);//color for stars not selected
        */
        // on first create always check for loadable levels!
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("savesChanged", true);
        editor.apply();
        refreshContinueButton();


        // set Nav_Bar
        drawer = findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view_main);
        mNavigationView.setNavigationItemSelectedListener(this);

        selectNavigationItem(R.id.nav_newgame_main);

        overridePendingTransition(0, 0);
    }

    public void callFragment(View view) {
        /*FragmentManager fm = getSupportFragmentManager();
        DialogWinScreen winScreen = new DialogWinScreen();

        winScreen.show(fm,"win_screen_layout");*/

    }


    public void onClick(View view) {

        Intent i = null;

        int i1 = view.getId();
        if (i1 == R.id.arrow_left) {
            mViewPager.arrowScroll(View.FOCUS_LEFT);
        } else if (i1 == R.id.arrow_right) {
            mViewPager.arrowScroll(View.FOCUS_RIGHT);
        } else if (i1 == R.id.continueButton) {
            i = new Intent(this, LoadGameActivity.class);
        } else if (i1 == R.id.playButton) {
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
                i = new Intent(this, GameActivity.class);
                i.putExtra("gameType", gameType.name());
                i.putExtra("gameDifficulty", gameDifficulty.name());
            } else {
                newLevelManager.checkAndRestock();
                Toast t = Toast.makeText(getApplicationContext(), R.string.generating, Toast.LENGTH_SHORT);
                t.show();
                return;
            }
        }

        final Intent intent = i;

        if (intent != null) {

            View mainContent = findViewById(R.id.main_content);
            if (mainContent != null) {
                mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, MAIN_CONTENT_FADEOUT_DURATION);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        selectNavigationItem(R.id.nav_newgame_main);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        drawer.closeDrawer(GravityCompat.START);

        // return if we are not going to another page
        if (id == R.id.nav_newgame_main) {
            return true;
        }

        // delay transition so the drawer can close
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNavigationItem(id);
            }
        }, NAVDRAWER_LAUNCH_DELAY);

        // fade out the active activity
        View mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
        }

        return true;
    }

    // set active navigation item
    private void selectNavigationItem(int itemId) {
        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
            boolean b = itemId == mNavigationView.getMenu().getItem(i).getItemId();
            mNavigationView.getMenu().getItem(i).setChecked(b);
        }
    }

    private void goToNavigationItem(int id) {
        Intent intent;

        if (id == R.id.menu_settings_main) {//open settings
            intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.GamePreferenceFragment.class.getName());
            intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else if (id == R.id.nav_highscore_main) {// see highscore list

            intent = new Intent(this, StatsActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else if (id == R.id.menu_help_main) {//open about page
            intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }*/

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
            // getItem is called to instantiate the fragment for the given page.
            // Return a GameTypeFragment (defined as a static inner class below).
            return GameTypeFragment.newInstance(position);
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return GameType.getValidGameTypes().size();
        }
    }

}
