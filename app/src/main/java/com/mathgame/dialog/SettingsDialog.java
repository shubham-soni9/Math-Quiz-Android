package com.mathgame.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.mathgame.R;
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

    private Bundle backpack;

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


            tvTitle.setText(title);

            Button btnOk = optionsDialog.findViewById(R.id.btnOk);
            Button btnCancel = optionsDialog.findViewById(R.id.btnCancel);

            btnOk.setText(positiveButton);
            btnCancel.setText(negativeButton);


            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    optionsDialog.dismiss();

                    if (listener != null)
                        listener.performPositiveAction(purposeCode, backpack);
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    optionsDialog.dismiss();

                    if (listener != null)
                        listener.performNegativeAction(purposeCode, backpack);
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
         *
         */
        void performPositiveAction(int purpose, Bundle backpack);


        /**
         * Override this method to perform operations
         * after CANCEL button was pressed
         *
         */
        void performNegativeAction(int purpose, Bundle backpack);
    }

    /**
     * Class to build the SettingsDialog
     */
    public static class Builder {

        private SettingsDialog settingsDialog = new SettingsDialog();

        /**
         * Constructor to initialize the SettingsDialog
         * (for an Activity)
         *
         */
        public Builder(Activity activity) {

            settingsDialog.activity = activity;

            if (activity instanceof Listener)
                settingsDialog.listener = (Listener) activity;
        }

        /**
         * Constructor to initialize the SettingsDialog
         * (for a Fragment)
         *
         */
        public Builder(Fragment fragment) {

            settingsDialog.activity = fragment.getActivity();

            if (fragment instanceof Listener)
                settingsDialog.listener = (Listener) fragment;
        }

        /**
         * Method to set purpose code to identify the Dialog
         *
         */
        public Builder purpose(int purposeCode) {
            settingsDialog.purposeCode = purposeCode;
            return this;
        }

        /**
         * Method to set a custom listener to listen
         * the events from SettingsDialog
         *
         */
        public Builder listener(Listener listener) {
            settingsDialog.listener = listener;
            return this;
        }

        /**
         * Method to set a backpack containing the data for
         * the intended action
         *
         */
        public Builder backpack(Bundle backpack) {
            settingsDialog.backpack = backpack;
            return this;
        }

        /**
         * Method to set title to the Title
         *
         */
        public Builder title(int resourceId) {
            return title(settingsDialog.activity.getString(resourceId));
        }

        /**
         * Method to set title to the Title
         *
         */
        public Builder title(String title) {
            settingsDialog.title = title;
            return this;
        }

        /**
         * Method to set message to the Dialog
         *
         */
        public Builder message(int resourceId) {
            return message(settingsDialog.activity.getString(resourceId));
        }

        /**
         * Method to set message to the Dialog
         *
         */
        public Builder message(String message) {
            settingsDialog.message = message;
            return this;
        }

        /**
         * Method to set text to the Positive Button
         *
         */
        public Builder positiveButton(int resourcedId) {
            return positiveButton(settingsDialog.activity.getString(resourcedId));
        }

        /**
         * Method to set text to the Positive Button
         *
         */
        public Builder positiveButton(String buttonText) {
            settingsDialog.positiveButton = buttonText;
            return this;
        }

        /**
         * Method to set Text to the Negative Button
         *
         */
        public Builder negativeButton(int resourceId) {
            return negativeButton(settingsDialog.activity.getString(resourceId));
        }

        /**
         * Method to set Text to the Negative Button
         *
         */
        public Builder negativeButton(String cancel) {
            settingsDialog.negativeButton = cancel;
            return this;
        }

        /**
         * Method to init the Options Dialog
         *
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
