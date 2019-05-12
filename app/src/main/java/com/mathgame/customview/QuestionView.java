package com.mathgame.customview;

import android.app.Activity;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.listener.OnQuestionListener;
import com.mathgame.model.Question;
import com.mathgame.util.Utils;

public class QuestionView implements Question.Listener {
    private final Activity           mActivity;
    private final View               mView;
    private final EditText           etAnswer;
    private final OnQuestionListener onQuestionListener;
    private final TextView           tvQuestion;
    private       Question           question;

    public QuestionView(Activity mActivity) {
        this.mActivity = mActivity;
        onQuestionListener = (OnQuestionListener) mActivity;
        mView = LayoutInflater.from(mActivity).inflate(R.layout.layout_question_view, null);
        tvQuestion = mView.findViewById(R.id.tvQuestion);
        etAnswer = mView.findViewById(R.id.etAnswer);
    }

    public View render(final Question mQuestion, final int position) {
        this.question = mQuestion;
        question.setListener(this);
        tvQuestion.setText(question.getQuestion().replace("?", ""));
        etAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        onQuestionListener.onAnswerCompleted(position);
                        etAnswer.setBackgroundResource(R.drawable.cornered_border_success);
                }
                return false;
            }
        });
        if (position == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestFocus();
                }
            }, 500);
        }
        etAnswer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onQuestionListener.onFocusChanged(position);
                return true;
            }
        });
        return mView;
    }

    public boolean isCorrectAnswer() {
        return question.getAnswer().equalsIgnoreCase(Utils.get(etAnswer));
    }


    public void requestFocus() {
        etAnswer.requestFocus();
        etAnswer.setBackgroundResource(R.drawable.cornered_border_selected);
        Utils.showSoftKeyboard(mActivity, etAnswer);
    }

    public void setUI() {
        if (Utils.get(etAnswer).isEmpty()) {
            etAnswer.setBackgroundResource(R.drawable.cornered_border_unselected);
        }
    }

    public void setError(){
        etAnswer.setBackgroundResource(R.drawable.cornered_border_error);
    }

    @Override
    public Object getView() {
        return this;
    }
}
