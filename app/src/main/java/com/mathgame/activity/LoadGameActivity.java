package com.mathgame.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.plugin.sudoku.controller.GameStateManager;
import com.mathgame.plugin.sudoku.controller.helper.GameInfoContainer;
import com.mathgame.plugin.sudoku.game.GameDifficulty;
import com.mathgame.plugin.sudoku.ui.listener.IDeleteDialogFragmentListener;
import com.mathgame.structure.BaseActivity;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public class LoadGameActivity extends BaseActivity implements IDeleteDialogFragmentListener {

    private List<GameInfoContainer> loadableGameList;
    private SharedPreferences       settings;
    private LoadGameAdapter         loadGameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {

        GameStateManager gameStateManager = new GameStateManager(this, settings);
        loadableGameList = gameStateManager.loadGameStateInfo();

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(parent.getContext(), SudokuGameActivity.class);
                i.putExtra("loadLevel", true);
                i.putExtra("loadLevelID", position);
                finish();
                startActivity(i);
            }
        };

        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialogFragment deleteDialog = new DeleteDialogFragment();
                deleteDialog.setPosition(position);
                deleteDialog.show(getFragmentManager(), "DeleteDialogFragment");

                return true;
            }
        };

        ListView listView = findViewById(R.id.main_content);
        loadGameAdapter = new LoadGameAdapter(this, loadableGameList);
        listView.setAdapter(loadGameAdapter);
        listView.setOnItemClickListener(clickListener);
        listView.setOnItemLongClickListener(longClickListener);

    }

    @Override
    public void onDialogPositiveClick(int position) {
        GameStateManager gameStateManager = new GameStateManager(getApplicationContext(), settings);
        gameStateManager.deleteGameStateFile(loadableGameList.get(position));
        loadGameAdapter.delete(position);
    }

    public static class DeleteDialogFragment extends DialogFragment {

        final   LinkedList<IDeleteDialogFragmentListener> listeners = new LinkedList<>();
        private int                                       position  = 0;

        int getPosition() {
            return position;
        }

        void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            // Verify that the host activity implements the callback interface
            if (activity instanceof IDeleteDialogFragmentListener) {
                listeners.add((IDeleteDialogFragmentListener) activity);
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.loadgame_delete_confirmation)
                    .setPositiveButton(R.string.loadgame_delete_confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            for (IDeleteDialogFragmentListener l : listeners) {
                                l.onDialogPositiveClick(getPosition());
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            return builder.create();
        }
    }


    private class LoadGameAdapter extends BaseAdapter {

        private final Context                 context;
        private final List<GameInfoContainer> loadableGameList;

        LoadGameAdapter(Context context, List<GameInfoContainer> loadableGameList) {
            this.context = context;
            this.loadableGameList = loadableGameList;
        }

        void delete(int position) {
            loadableGameList.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return loadableGameList.size();
        }

        @Override
        public Object getItem(int position) {
            return loadableGameList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return loadableGameList.get(position).getID();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_entry_layout, null);
            }

            GameInfoContainer gic = loadableGameList.get(position);

            TextView gameType = convertView.findViewById(R.id.loadgame_listentry_gametype);
            TextView difficulty = convertView.findViewById(R.id.loadgame_listentry_difficultytext);
            RatingBar difficultyBar = convertView.findViewById(R.id.loadgame_listentry_difficultybar);
            TextView playedTime = convertView.findViewById(R.id.loadgame_listentry_timeplayed);
            TextView lastTimePlayed = convertView.findViewById(R.id.loadgame_listentry_lasttimeplayed);
            ImageView image = convertView.findViewById(R.id.loadgame_listentry_gametypeimage);

            switch (gic.getGameType()) {
                case Default_6x6:
                    image.setImageResource(R.drawable.icon_default_6x6);
                    break;
                case Default_12x12:
                    image.setImageResource(R.drawable.icon_default_12x12);
                    break;
                case Default_9x9:
                    image.setImageResource(R.drawable.icon_default_9x9);
                    break;
                default:
                    image.setImageResource(R.drawable.icon_default_9x9);
            }
            gameType.setText(gic.getGameType().getStringResID());
            difficulty.setText(gic.getDifficulty().getStringResID());
            difficultyBar.setNumStars(GameDifficulty.getValidDifficultyList().size());
            difficultyBar.setMax(GameDifficulty.getValidDifficultyList().size());
            difficultyBar.setRating(GameDifficulty.getValidDifficultyList().indexOf(gic.getDifficulty()) + 1);

            int time = gic.getTimePlayed();
            int seconds = time % 60;
            int minutes = ((time - seconds) / 60) % 60;
            int hours = (time - minutes - seconds) / (3600);
            String h, m, s;
            s = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
            m = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
            h = (hours < 10) ? "0" + hours : String.valueOf(hours);
            playedTime.setText(String.format("%s:%s:%s", h, m, s));

            Date lastTimePlayedDate = gic.getLastTimePlayed();

            DateFormat format = DateFormat.getDateTimeInstance();
            format.setTimeZone(TimeZone.getDefault());

            lastTimePlayed.setText(format.format(lastTimePlayedDate));

            return convertView;
        }
    }
}
