package com.mathgame.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.model.CustomMode;

import java.util.ArrayList;

public class CustomModeAdapter extends RecyclerView.Adapter<CustomModeAdapter.ViewHolder> {
    private ArrayList<CustomMode> modeList;

    public CustomModeAdapter(ArrayList<CustomMode> rightsList) {
        this.modeList = rightsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_mode, parent, false);
        return new CustomModeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return modeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
