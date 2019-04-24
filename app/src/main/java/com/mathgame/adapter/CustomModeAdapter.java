package com.mathgame.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.model.CustomMode;
import com.mathgame.util.Codes;
import com.mathgame.util.Dependencies;
import com.mathgame.util.Utils;

import java.util.List;

public class CustomModeAdapter extends RecyclerView.Adapter<CustomModeAdapter.ViewHolder> {
    private List<CustomMode> customModeList;
    private Context          context;

    public CustomModeAdapter(List<CustomMode> customModeList) {
        this.customModeList = customModeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_mode, parent, false);
        context = view.getContext();
        return new CustomModeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomMode customMode = customModeList.get(position);
        holder.tvTitle.setText(customMode.getTitle());
        holder.tvQuestionValue.setText(String.valueOf(customMode.getNumberOfQuestions()));
        holder.tvSkipValue.setText(String.valueOf(customMode.getSkipNumbers()));
        holder.tvVariableValue.setText(String.valueOf(customMode.getNumberOfVariables()));
        holder.tvTimerType.setText(Codes.TimerType.get(customMode.getTimerType()).label);
        holder.tvGameType.setText(Codes.TimerType.get(customMode.getGameType()).label);
        int seconds = customMode.getTimerValue() % 60;
        int minutes = customMode.getTimerValue() / 60;
        holder.tvTimerValue.setText(String.format(Dependencies.getLocale(context), "%s:%s", Utils.convertToTwoDigit(minutes)
                , Utils.convertToTwoDigit(seconds)));
        holder.tvOperations.setText(customMode.getMathOperations());
        if (customMode.getPlayerType() == Codes.PlayerType.SINGLE.value) {
            holder.ivPlayerType.setImageResource(R.drawable.ic_single_player);
        } else {
            holder.ivPlayerType.setImageResource(R.drawable.ic_dual_player);
        }
    }

    @Override
    public int getItemCount() {
        return customModeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvQuestionValue, tvVariableValue, tvSkipValue, tvGameType, tvTimerType, tvTimerValue, tvOperations;
        private ImageView ivPlayerType;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvQuestionValue = itemView.findViewById(R.id.tvQuestionValue);
            tvVariableValue = itemView.findViewById(R.id.tvVariableValue);
            tvSkipValue = itemView.findViewById(R.id.tvSkipValue);
            tvGameType = itemView.findViewById(R.id.tvGameType);
            tvTimerType = itemView.findViewById(R.id.tvTimerType);
            tvTimerValue = itemView.findViewById(R.id.tvTimerValue);
            tvOperations = itemView.findViewById(R.id.tvOperations);
            ivPlayerType = itemView.findViewById(R.id.ivPlayerType);
        }
    }

}
