package com.mathgame.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.dialog.AlertDialog;
import com.mathgame.model.CLevel;
import com.mathgame.model.CQuestion;

import java.util.ArrayList;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
    private Context              context;
    private ArrayList<CLevel> levelList;

    public LevelAdapter(Context context, ArrayList<CLevel> levelList) {
        this.context = context;
        this.levelList = levelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_clevel, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvLevelName.setText(String.valueOf(viewHolder.getAdapterPosition()+1));
        viewHolder.tvLevelName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return levelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView     tvLevelName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLevelName = itemView.findViewById(R.id.tvLevelName);
        }
    }
}
