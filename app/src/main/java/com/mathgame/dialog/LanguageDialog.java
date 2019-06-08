package com.mathgame.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.adapter.LanguageAdapter;
import com.mathgame.appdata.Constant;
import com.mathgame.listener.OnListItemClickListener;

import java.util.ArrayList;
import java.util.Objects;

public class LanguageDialog implements OnListItemClickListener {
    private static final float DIM_AMOUNT = 0.6f;

    //ui params
    private Dialog mDialog;

    //general params
    private int                             mSelectedPos       = -1;
    private Context                         mContext;
    private OnListItemClickListener         mListItemClickListener;
    private boolean                         mIsButtonsRequired = false;
    private LanguageAdapter                 mDialogListAdapter;
    private ArrayList<Constant.AppLanguage> languageList;
    private String                          selectedCode;

    private LanguageDialog(final Context context) {
        this.mContext = context;
    }

    /**
     * @param context the provided context
     */
    public static LanguageDialog with(final Context context) {
        return new LanguageDialog(context);
    }


    /**
     * show dialog with buttons
     */
    public void show(final String header, ArrayList<Constant.AppLanguage> languageList, final boolean isButtonsRequired,
                     final OnListItemClickListener listener, String preselectedCode) {
        try {
            this.mListItemClickListener = listener;
            this.mIsButtonsRequired = isButtonsRequired;
            this.languageList = languageList;
            this.selectedCode = preselectedCode;
            mDialog = new Dialog(mContext);
            Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.setContentView(R.layout.dialog_language);
            WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
            layoutParams.dimAmount = DIM_AMOUNT;
            mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);

            TextView tvHeader = mDialog.findViewById(R.id.dialog_buttons_tv_header);
            LinearLayout llButtons = mDialog.findViewById(R.id.dialog_buttons_ll_buttons);
            Button btnCancel = mDialog.findViewById(R.id.dialog_buttons_btn_cancel);
            Button btnOk = mDialog.findViewById(R.id.dialog_buttons_btn_ok);
            btnOk.setText(mContext.getString(R.string.submit));
            RecyclerView rvListItems = mDialog.findViewById(R.id.dialog_buttons_rv_list);

            rvListItems.setItemAnimator(new DefaultItemAnimator());
            rvListItems.setLayoutManager(new LinearLayoutManager(mContext));
            if (header == null || header.isEmpty()) {
                tvHeader.setVisibility(View.GONE);
            } else {
                tvHeader.setText(header);
            }

            if (isButtonsRequired) {
                llButtons.setVisibility(View.VISIBLE);
            } else {
                llButtons.setVisibility(View.GONE);
            }

            for (Constant.AppLanguage taskStatus : languageList) {
                if (taskStatus.code.equalsIgnoreCase(preselectedCode)) {
                    mSelectedPos = languageList.indexOf(taskStatus);
                    break;
                }
            }
            btnCancel.setOnClickListener(view -> mDialog.dismiss());
            btnOk.setOnClickListener(view -> {
                if (mSelectedPos != -1) {
                    mDialog.dismiss();
                    mListItemClickListener.onListItemSelected(mSelectedPos, selectedCode);
                }
            });

            // ListView
            mDialogListAdapter = new LanguageAdapter(mContext, this, languageList, mSelectedPos);
            mDialogListAdapter.setSelectedPosition(mSelectedPos);
            mDialogListAdapter.notifyDataSetChanged();
            rvListItems.setAdapter(mDialogListAdapter);
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemSelected(final int clickedPosition, final String selectedCode) {
        if (mIsButtonsRequired) {
            mSelectedPos = clickedPosition;
            for (Constant.AppLanguage taskStatus : languageList) {
                if (taskStatus.code.equalsIgnoreCase(selectedCode)) {
                    this.selectedCode = selectedCode;
                    break;
                }
            }

            mDialogListAdapter.setSelectedPosition(clickedPosition);
            mDialogListAdapter.notifyDataSetChanged();
        } else {
            mDialog.dismiss();
            mListItemClickListener.onListItemSelected(clickedPosition, selectedCode);
        }
    }
}

