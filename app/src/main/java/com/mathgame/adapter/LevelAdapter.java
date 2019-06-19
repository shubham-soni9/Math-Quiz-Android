package com.mathgame.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.activity.SingleGameActivity;
import com.mathgame.appdata.Codes;
import com.mathgame.appdata.Constant;
import com.mathgame.appdata.Keys;
import com.mathgame.model.CLevel;
import com.mathgame.model.CustomMode;
import com.mathgame.util.AudioUtils;
import com.mathgame.util.Transition;

import java.util.ArrayList;

import static com.mathgame.appdata.Keys.Extras.CAREER_LEVEL;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
    private Context           context;
    private ArrayList<CLevel> levelList;
    private int               currentLevel;

    public LevelAdapter(Context context, ArrayList<CLevel> levelList, int currentLevel) {
        this.context = context;
        this.levelList = levelList;
        this.currentLevel = currentLevel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_clevel, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final CLevel cLevel = levelList.get(viewHolder.getAdapterPosition());
        viewHolder.tvLevelName.setText(String.valueOf(position + 1));
        if ((position + 1) <= currentLevel) {
            viewHolder.tvLevelName.setVisibility(View.VISIBLE);
            viewHolder.ibLock.setVisibility(View.GONE);
        } else {
            viewHolder.tvLevelName.setVisibility(View.GONE);
            viewHolder.ibLock.setVisibility(View.VISIBLE);
        }
        switch (cLevel.getDifficulty()) {
            case Constant.DifficultyLevel.SMALL:
                viewHolder.tvLevelName.setBackgroundResource(R.drawable.bg_level_small);
                break;
            case Constant.DifficultyLevel.MEDIUM:
                viewHolder.tvLevelName.setBackgroundResource(R.drawable.bg_level_medium);
                break;
            case Constant.DifficultyLevel.LARGE:
                viewHolder.tvLevelName.setBackgroundResource(R.drawable.bg_level_large);
                break;
        }
        viewHolder.tvLevelName.setOnClickListener(v -> {
            AudioUtils.getInstance().onButtonClicked(context);
            CustomMode customMode = new CustomMode();
            customMode.setGameType(Codes.GameType.MULTIPLE_CHOICE.value);
            customMode.setTimerValue(cLevel.getTime_per_question());
            customMode.setDifficulty(cLevel.getDifficulty());
            customMode.setNumberOfQuestions(10);
            customMode.setQuestionSample(cLevel.getQuestionSample());
            Bundle bundle = new Bundle();
            bundle.putParcelable(CustomMode.class.getName(), customMode);
            bundle.putInt(Keys.Extras.CAREER_LEVEL,position+1);
            Transition.transitForResult((Activity) context, SingleGameActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return levelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView             tvLevelName;
        private AppCompatImageButton ibLock;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLevelName = itemView.findViewById(R.id.tvLevelName);
            ibLock = itemView.findViewById(R.id.ibLock);
        }
    }
}
