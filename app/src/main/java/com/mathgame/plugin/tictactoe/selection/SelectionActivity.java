package com.mathgame.plugin.tictactoe.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mathgame.R;
import com.mathgame.plugin.tictactoe.TTTConstants;
import com.mathgame.plugin.tictactoe.game.TTTGameActivity;

/**
 * This activity takes the user through the selection flow.
 * <p>
 * are prompted for selection.
 */
public class SelectionActivity extends AppCompatActivity implements SelectionFragment.OnValueSelectedListener {
    private SelectionFragment modeSelectionFragment;
    private SelectionFragment signSelectionFragment;
    private SelectionFragment turnSelectionFragment;

    private @TTTConstants.Sign
    int player1Sign;
    private @TTTConstants.Sign
    int player2Sign;
    private @TTTConstants.Player
    int firstTurn;
    private @TTTConstants.GameMode
    int gameMode;

    private boolean backFromGameActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        modeSelectionFragment = SelectionFragment.newInstance(getResources().getString(
                R.string.mode_selection_text), R.drawable.single_player, R.drawable.multi_player);

        signSelectionFragment = SelectionFragment.newInstance(getResources().getString(
                R.string.sign_selection_text), R.drawable.circle, R.drawable.cross);

        turnSelectionFragment = SelectionFragment.newInstance(getResources().getString(
                R.string.turn_selection_text), R.drawable.user, R.drawable.system);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, modeSelectionFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (backFromGameActivity) {
            setFragmentTransitionAnimationEnabled(false);

            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().popBackStack();

            backFromGameActivity = false;
        }
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();

        setFragmentTransitionAnimationEnabled(true);
    }

    private void setFragmentTransitionAnimationEnabled(boolean enabled) {
        modeSelectionFragment.setTransitionAnimationEnabled(enabled);
        signSelectionFragment.setTransitionAnimationEnabled(enabled);
        turnSelectionFragment.setTransitionAnimationEnabled(enabled);
    }

    @Override
    public void onValueSelected(Fragment fragment, @SelectionFragment.ButtonType int buttonType) {
        if (fragment == modeSelectionFragment) {

            switch (buttonType) {
                case SelectionFragment.TOP_BUTTON:
                    gameMode = TTTConstants.SINGLE_PLAYER;
                    break;
                case SelectionFragment.BOTTOM_BUTTON:
                    gameMode = TTTConstants.MULTI_PLAYER;
                    break;
            }

            replaceFragment(signSelectionFragment);

        } else if (fragment == signSelectionFragment) {

            switch (buttonType) {
                case SelectionFragment.TOP_BUTTON:
                    player1Sign = TTTConstants.CIRCLE;
                    player2Sign = TTTConstants.CROSS;
                    break;
                case SelectionFragment.BOTTOM_BUTTON:
                    player1Sign = TTTConstants.CROSS;
                    player2Sign = TTTConstants.CIRCLE;
                    break;
            }

            switch (gameMode) {
                case TTTConstants.SINGLE_PLAYER:
                    replaceFragment(turnSelectionFragment);
                    break;
                case TTTConstants.MULTI_PLAYER:
                    firstTurn = TTTConstants.PLAYER_1;
                    startGameActivity();
                    break;
            }

        } else if (fragment == turnSelectionFragment) {

            switch (buttonType) {
                case SelectionFragment.TOP_BUTTON:
                    firstTurn = TTTConstants.PLAYER_1;
                    break;
                case SelectionFragment.BOTTOM_BUTTON:
                    firstTurn = TTTConstants.PLAYER_2;
                    break;
            }

            startGameActivity();
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.fade_out, R.anim.fade_in, R.anim.exit_to_left)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void startGameActivity() {
        Intent intent = new Intent(getApplicationContext(), TTTGameActivity.class);
        intent.putExtra("PLAYER_1_SIGN", player1Sign);
        intent.putExtra("PLAYER_2_SIGN", player2Sign);
        intent.putExtra("FIRST_TURN", firstTurn);
        intent.putExtra("GAME_MODE", gameMode);
        startActivity(intent);

        backFromGameActivity = true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
