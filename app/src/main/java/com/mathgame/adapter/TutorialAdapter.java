package com.mathgame.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.activity.WebViewActivity;
import com.mathgame.model.Article;
import com.mathgame.util.Transition;

import java.util.ArrayList;

import static com.mathgame.appdata.Keys.Prefs.KEY_TITLE;
import static com.mathgame.appdata.Keys.Prefs.KEY_WEB_URL;

public class TutorialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private       ArrayList<Article> articles;
    private final int                TYPE_TUTORIAL = 0;
    private final int                TYPE_HEADER   = 1;
    private       Activity           context;

    public TutorialAdapter(Activity context, ArrayList<Article> articles) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header, viewGroup, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tutorial, viewGroup, false);
            return new TutorialViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            holder.tvHeader.setText(articles.get(holder.getAdapterPosition()).getTitle());
        } else {
            final TutorialViewHolder holder = (TutorialViewHolder) viewHolder;
            holder.tvTutorialName.setText(articles.get(holder.getAdapterPosition()).getTitle());
            holder.llItemParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_TITLE, articles.get(holder.getAdapterPosition()).getTitle());
                    bundle.putString(KEY_WEB_URL, articles.get(holder.getAdapterPosition()).getLink());
                    Transition.startActivity(context, WebViewActivity.class, bundle);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return articles.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    public class TutorialViewHolder extends RecyclerView.ViewHolder {
        private TextView     tvTutorialName;
        private LinearLayout llItemParent;

        TutorialViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTutorialName = itemView.findViewById(R.id.tvTutorialName);
            llItemParent = itemView.findViewById(R.id.llItemParent);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHeader;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tvHeader);
        }
    }
}
