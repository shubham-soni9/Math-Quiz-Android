package com.mathgame.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Constant;
import com.mathgame.model.Question;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    private ArrayList<Question> answerList;
    private Question            question;

    public AnswerAdapter(ArrayList<Question> answerList) {
        this.answerList = answerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_list, parent, false);
        return new AnswerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        question = answerList.get(position);
        holder.tvQuestion.setText(question.getQuestion());
        holder.tvInputValue.setText(question.getUserInput());
        holder.tvAnswer.setText(question.getAnswer());
        holder.tvQuestionNumber.setText(String.valueOf(position + 1));
        holder.tvResult.setText(String.valueOf(position + 1));
        if (question.getAnswerType() == Constant.AnswerType.CORRECT) {
            holder.tvResult.setText(R.string.correct);
            holder.tvResult.setBackgroundResource(R.drawable.bg_item_success);
        } else if (question.getAnswerType() == Constant.AnswerType.INCORRECT) {
            holder.tvResult.setBackgroundResource(R.drawable.bg_item_error);
            holder.tvResult.setText(R.string.incorrect);
        } else {
            holder.tvResult.setBackgroundResource(R.drawable.bg_item_unattempted);
            holder.tvResult.setText(R.string.unattempted);
        }
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuestion, tvInputValue, tvAnswer, tvQuestionNumber, tvResult;

        ViewHolder(View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvInputValue = itemView.findViewById(R.id.tvInputValue);
            tvAnswer = itemView.findViewById(R.id.tvCorrect);
            tvQuestionNumber = itemView.findViewById(R.id.tvQuestionNumber);
            tvResult = itemView.findViewById(R.id.tvResult);
        }
    }

}
