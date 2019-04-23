package com.mathgame.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mathgame.R;

import java.util.ArrayList;

public class CustomModeAdapter extends RecyclerView.Adapter<CustomModeAdapter.ViewHolder> {
    private ArrayList<Rights> rightsList;
    private OnItemListener    onItemListener;

    public CustomModeAdapter(CustomModeAdapter.OnItemListener onItemListener, ArrayList<Rights> rightsList) {
        this.rightsList = rightsList;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settings_rights, parent, false);
        return new CustomModeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        position = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return rightsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRight;
        private View     parentLayout;
        private View     vRights;

        ViewHolder(View itemView) {
            super(itemView);
            tvRight = itemView.findViewById(R.id.tv_right);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            vRights = itemView.findViewById(R.id.vRights);
        }
    }

    public interface OnItemListener {
        void onFleetRightSelected(int value);
    }
}
