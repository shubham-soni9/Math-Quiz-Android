package com.mathgame.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.GameSettings;
import com.mathgame.model.CustomMode;
import com.mathgame.plugin.numberpicker.NumberPicker;
import com.mathgame.plugin.switchBtn.ToggleSwitch;
import com.mathgame.util.Log;
import com.mathgame.util.Utils;


/**
 * Class that holds ready to use dialogs
 *
 * @author Rishabh
 */
public class SettingsDialog {

    private static final String TAG = SettingsDialog.class.getSimpleName();

    private Dialog optionsDialog;

    /*
     * The context which would listen to the Events
     * of the AlertDialog (must be an implementation of
     * Listener)
     */
    private Listener listener;

    /*
     * The activity on which the AlertDialog would be displayed
     */
    private Activity activity;

    /*
     * A unique purpose code to identify the action for which
     * the Dialog is Created
     */
    private int purposeCode;

    // The title for the Options Dialog
    private String title;
    // The message to be conveyed to the User
    private String message;
    // The text for Ok button
    private String positiveButton;
    // The text for Cancel button
    private String negativeButton;

    private Bundle     backpack;
    private CustomMode customMode;

    /**
     * Method to showOn a multi-event Dialog
     */
    private SettingsDialog init() {

        try {

            optionsDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            optionsDialog.setContentView(R.layout.dialog_settings);

            Window dialogWindow = optionsDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = 0.6f;

            dialogWindow.getAttributes().windowAnimations = R.style.CustomDialogStyle;

            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            optionsDialog.setCancelable(true);
            optionsDialog.setCanceledOnTouchOutside(true);

            TextView tvTitle = optionsDialog.findViewById(R.id.tvTitle);
            final ToggleSwitch twDifficultyLevel = optionsDialog.findViewById(R.id.twDifficultyLevel);
            final NumberPicker npNumberOfQuestion = optionsDialog.findViewById(R.id.npNumberOfQuestion);
            final NumberPicker npNumberOfSkip = optionsDialog.findViewById(R.id.npNumberOfSkip);
            RelativeLayout rlEnableTimer = optionsDialog.findViewById(R.id.rlEnableTimer);
            final AppCompatCheckBox cbEnableTimer = optionsDialog.findViewById(R.id.cbEnableTimer);
            final RelativeLayout rlTimerValue = optionsDialog.findViewById(R.id.rlTimerValue);
            final NumberPicker npTimerSecondValue = optionsDialog.findViewById(R.id.npTimerSecondValue);
            final AppCompatCheckBox cbSaveSettings = optionsDialog.findViewById(R.id.cbSaveSettings);
            Button btnOk = optionsDialog.findViewById(R.id.btnOk);
            final Button btnCancel = optionsDialog.findViewById(R.id.btnCancel);

            cbEnableTimer.setOnCheckedChangeListener((buttonView, isChecked) -> rlTimerValue.setVisibility(isChecked ? View.VISIBLE : View.GONE));
            npNumberOfQuestion.setOnValueChangedListener(picker -> npNumberOfSkip.setMaxValue(npNumberOfQuestion.getMaxValue()));
            rlEnableTimer.setOnClickListener(v -> cbEnableTimer.setChecked(!cbEnableTimer.isChecked()));

            if (customMode != null) {
                twDifficultyLevel.setCheckedTogglePosition(customMode.getDifficulty());
                npNumberOfQuestion.setValue(customMode.getNumberOfQuestions());
                npNumberOfSkip.setMaxValue(customMode.getNumberOfQuestions());
                npNumberOfSkip.setValue(customMode.getSkipNumbers());
                cbEnableTimer.setChecked(customMode.getTimerValue() > 0);
                if (customMode.getTimerValue() > 0) {
                    npTimerSecondValue.setValue(customMode.getTimerValue());
                }
            }

            tvTitle.setText(title);
            btnOk.setText(positiveButton);
            btnCancel.setText(negativeButton);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    optionsDialog.dismiss();
                    customMode.setDifficulty(twDifficultyLevel.getCheckedTogglePosition());
                    customMode.setSkipNumbers(npNumberOfSkip.getValue());
                    customMode.setNumberOfQuestions(npNumberOfQuestion.getValue());
                    customMode.setTimerValue(cbEnableTimer.isChecked() ? npTimerSecondValue.getValue() : 0);
                    if (cbSaveSettings.isChecked()) {
                        GameSettings.saveCustomMode(customMode);
                    }
                    if (listener != null) {
                        listener.performPositiveAction();
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    optionsDialog.dismiss();

                    if (listener != null)
                        listener.performNegativeAction();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "OPTIONS DIALOG: " + e.getMessage());
        }

        return this;
    }

    /**
     * Method to init the initialized dialog
     */
    public void show() {

        // Check if activity lives
        //May throw BadTokenException if activity is finished
        try {
            if (activity != null)
                // Check if dialog contains data
                if (optionsDialog != null)
                    // Show the Dialog
                    optionsDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Listener to listen to the events when a main_button
     * on the Options Dialog was pressed
     * <p>
     * Developer: Rishabh
     * Dated: 19/05/15.
     */
    public interface Listener {

        /**
         * Override this method to perform operations
         * after OK button was pressed
         */
        void performPositiveAction();


        /**
         * Override this method to perform operations
         * after CANCEL button was pressed
         */
        void performNegativeAction();
    }

    /**
     * Class to build the SettingsDialog
     */
    public static class Builder {

        private final SettingsDialog settingsDialog = new SettingsDialog();

        /**
         * Constructor to initialize the SettingsDialog
         * (for an Activity)
         */
        public Builder(Activity activity) {

            settingsDialog.activity = activity;

            if (activity instanceof Listener)
                settingsDialog.listener = (Listener) activity;
        }

        /**
         * Constructor to initialize the SettingsDialog
         * (for a Fragment)
         */
        public Builder(Fragment fragment) {

            settingsDialog.activity = fragment.getActivity();

            if (fragment instanceof Listener)
                settingsDialog.listener = (Listener) fragment;
        }

        /**
         * Method to set purpose code to identify the Dialog
         */
        public Builder purpose(int purposeCode) {
            settingsDialog.purposeCode = purposeCode;
            return this;
        }

        /**
         * Method to set a custom listener to listen
         * the events from SettingsDialog
         */
        public Builder listener(Listener listener) {
            settingsDialog.listener = listener;
            return this;
        }

        /**
         * Method to set a backpack containing the data for
         * the intended action
         */
        public Builder backpack(Bundle backpack) {
            settingsDialog.backpack = backpack;
            return this;
        }

        /**
         * Method to set title to the Title
         */
        public Builder title(int resourceId) {
            return title(settingsDialog.activity.getString(resourceId));
        }

        /**
         * Method to set title to the Title
         */
        Builder title(String title) {
            settingsDialog.title = title;
            return this;
        }

        /**
         * Method to set message to the Dialog
         */
        public Builder message(int resourceId) {
            return message(settingsDialog.activity.getString(resourceId));
        }

        /**
         * Method to set message to the Dialog
         */
        Builder message(String message) {
            settingsDialog.message = message;
            return this;
        }

        /**
         * Method to set text to the Positive Button
         */
        public Builder positiveButton(int resourcedId) {
            return positiveButton(settingsDialog.activity.getString(resourcedId));
        }

        /**
         * Method to set text to the Positive Button
         */
        Builder positiveButton(String buttonText) {
            settingsDialog.positiveButton = buttonText;
            return this;
        }

        public Builder customMode(CustomMode customMode) {
            settingsDialog.customMode = customMode;
            return this;
        }

        /**
         * Method to set Text to the Negative Button
         */
        public Builder negativeButton(int resourceId) {
            return negativeButton(settingsDialog.activity.getString(resourceId));
        }

        /**
         * Method to set Text to the Negative Button
         */
        Builder negativeButton(String cancel) {
            settingsDialog.negativeButton = cancel;
            return this;
        }

        /**
         * Method to init the Options Dialog
         */
        public SettingsDialog build() {
            settingsDialog.title = Utils.assign(settingsDialog.title, settingsDialog.activity.getString(R.string.empty));
            settingsDialog.message = Utils.assign(settingsDialog.message, settingsDialog.activity.getString(R.string.settings));
            settingsDialog.positiveButton = Utils.assign(settingsDialog.positiveButton, settingsDialog.activity.getString(R.string.apply));
            settingsDialog.negativeButton = Utils.assign(settingsDialog.negativeButton, settingsDialog.activity.getString(R.string.cancel_text));
            return settingsDialog.init();
        }
    }
}
