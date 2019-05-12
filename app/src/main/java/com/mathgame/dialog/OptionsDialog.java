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
public class OptionsDialog {

    private static final String TAG = OptionsDialog.class.getSimpleName();

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
    private OptionsDialog init() {

        try {

            optionsDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            optionsDialog.setContentView(R.layout.dialog_options);

            Window dialogWindow = optionsDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = 0.6f;

            dialogWindow.getAttributes().windowAnimations = R.style.CustomDialog;

            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            optionsDialog.setCancelable(true);
            optionsDialog.setCanceledOnTouchOutside(true);

            TextView tvTitle = optionsDialog.findViewById(R.id.tvTitle);
            TextView tvMessage = optionsDialog.findViewById(R.id.tvMessage);


            tvTitle.setText(title);
            tvMessage.setText(Utils.fromHtml(message));

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
         * @param purpose
         */
        void performPositiveAction(int purpose, Bundle backpack);


        /**
         * Override this method to perform operations
         * after CANCEL button was pressed
         *
         * @param purpose
         */
        void performNegativeAction(int purpose, Bundle backpack);
    }

    /**
     * Class to build the OptionsDialog
     */
    public static class Builder {

        private OptionsDialog optionsDialog = new OptionsDialog();

        /**
         * Constructor to initialize the OptionsDialog
         * (for an Activity)
         *
         * @param activity
         */
        public Builder(Activity activity) {

            optionsDialog.activity = activity;

            if (activity instanceof Listener)
                optionsDialog.listener = (Listener) activity;
        }

        /**
         * Constructor to initialize the OptionsDialog
         * (for a Fragment)
         *
         */
        public Builder(Fragment fragment) {

            optionsDialog.activity = fragment.getActivity();

            if (fragment instanceof Listener)
                optionsDialog.listener = (Listener) fragment;
        }

        /**
         * Method to set purpose code to identify the Dialog
         *
         */
        public Builder purpose(int purposeCode) {
            optionsDialog.purposeCode = purposeCode;
            return this;
        }

        /**
         * Method to set a custom listener to listen
         * the events from OptionsDialog
         *
         */
        public Builder listener(Listener listener) {
            optionsDialog.listener = listener;
            return this;
        }

        /**
         * Method to set a backpack containing the data for
         * the intended action
         *
         */
        public Builder backpack(Bundle backpack) {
            optionsDialog.backpack = backpack;
            return this;
        }

        /**
         * Method to set title to the Title
         *
         */
        public Builder title(int resourceId) {
            return title(optionsDialog.activity.getString( resourceId));
        }

        /**
         * Method to set title to the Title
         *
         */
        public Builder title(String title) {
            optionsDialog.title = title;
            return this;
        }

        /**
         * Method to set message to the Dialog
         *
         */
        public Builder message(int resourceId) {
            return message(optionsDialog.activity.getString( resourceId));
        }

        /**
         * Method to set message to the Dialog
         *
         */
        public Builder message(String message) {
            optionsDialog.message = message;
            return this;
        }

        /**
         * Method to set text to the Positive Button
         *
         */
        public Builder positiveButton(int resourcedId) {
            return positiveButton(optionsDialog.activity.getString(resourcedId));
        }

        /**
         * Method to set text to the Positive Button
         *
         */
        public Builder positiveButton(String buttonText) {
            optionsDialog.positiveButton = buttonText;
            return this;
        }

        /**
         * Method to set Text to the Negative Button
         *
         */
        public Builder negativeButton(int resourceId) {
            return negativeButton(optionsDialog.activity.getString( resourceId));
        }

        /**
         * Method to set Text to the Negative Button
         *
         */
        public Builder negativeButton(String cancel) {
            optionsDialog.negativeButton = cancel;
            return this;
        }

        /**
         * Method to init the Options Dialog
         *
         */
        public OptionsDialog build() {

            // Check for null and assign default values
            optionsDialog.title = Utils.assign(optionsDialog.title, optionsDialog.activity.getString(R.string.empty));
            optionsDialog.message = Utils.assign(optionsDialog.message, optionsDialog.activity.getString( R.string.are_you_sure_text));
            optionsDialog.positiveButton = Utils.assign(optionsDialog.positiveButton, optionsDialog.activity.getString( R.string.yes_text));
            optionsDialog.negativeButton = Utils.assign(optionsDialog.negativeButton, optionsDialog.activity.getString(R.string.no_text));

            return optionsDialog.init();
        }
    }
}
