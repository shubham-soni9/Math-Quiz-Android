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
import com.mathgame.util.Utils;

/**
 * Developer: Rishabh
 * Dated: 23/07/15.
 */
public class AlertDialog {

    private static final String TAG = AlertDialog.class.getSimpleName();

    private Dialog alertDialog;

    /**
     * The instance of the Activity on which the
     * AlertDialog will be displayed
     */
    private Activity activity;

    /**
     * The receiver to which the AlertDialog
     * will return the CallBacks
     * <p/>
     * Note: listener should be an instance of
     * AlertDialog.Listener
     */
    private Listener listener;

    /**
     * The code to differentiate the various CallBacks
     * from different Dialogs
     */
    private int purpose;

    /**
     * The title to be set on the Dialog
     */
    private String title;

    /**
     * The message to be set on the Dialog
     */
    private String message;

    /**
     * The text to be set on the Action Button
     */
    private String actionButton;

    /**
     * The data to sent via the Dialog from the
     * remote parts of the Activity to other
     * parts
     */
    private Bundle backpack;

    /**
     * Method to create and display the alert alertDialog
     *
     * @return
     */
    private AlertDialog init() {

        try {

            alertDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            alertDialog.setContentView(R.layout.dialog_alert);

            Window dialogWindow = alertDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = 0.6f;

            dialogWindow.getAttributes().windowAnimations = R.style.CustomDialog;

            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);

            TextView tvTitle = alertDialog.findViewById(R.id.tvTitle);
            TextView tvMessage = alertDialog.findViewById(R.id.tvMessage);
            Button btnAction = alertDialog.findViewById(R.id.btnAction);

            if (title != null) {
                tvTitle.setText(title);
                tvTitle.setTextAppearance(activity, R.style.CustomTextAppearance_Bold);
                tvTitle.setVisibility(View.VISIBLE);
            }
            tvMessage.setText(Utils.fromHtml(message));
            btnAction.setText(actionButton);
            btnAction.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();

                    if (listener != null)
                        listener.performPostAlertAction(purpose, backpack);
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }

        return this;
    }

    /**
     * Method to init the initialized alertDialog
     */
    public void show() {

        // Check if activity lives
        if (activity != null)
            // Check if alertDialog contains data
            if (alertDialog != null) {
                try {
                    // Show the Dialog
                    alertDialog.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
    }

    /**
     * Method to dismiss the AlertDialog, if possible
     */
    public void dismiss() {

        // Check if activity lives
        if (activity != null)
            // Check if the Dialog is null
            if (alertDialog != null)
                // Check whether the alertDialog is visible
                if (alertDialog.isShowing()) {
                    try {
                        // Dismiss the Dialog
                        alertDialog.dismiss();
                        alertDialog = null;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
    }

    public boolean isShowing() {
        // Check if activity lives
        if (activity != null)
            // Check if the Dialog is null
            if (alertDialog != null)
                // Check whether the alertDialog is visible
                try {
                    return alertDialog.isShowing();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        return false;
    }


    /**
     * Interfaces the events from the AlertDialog
     * to the Calling Context
     */
    interface Listener {

        /**
         * Override this method to listen to
         * the events fired from AlertDialog
         */
        void performPostAlertAction(int purpose, Bundle backpack);
    }

    /**
     * Class to help building the Alert Dialog
     */
    public static class Builder {

        private final AlertDialog alertDialog = new AlertDialog();

        /**
         * Constructor to build a alertDialog for Activity
         */
        public Builder(Activity activity) {

            alertDialog.activity = activity;

            if (activity instanceof Listener)
                alertDialog.listener = (Listener) activity;
        }

        /**
         * Constructor to build a alertDialog for Fragment
         */
        public Builder(Fragment fragment) {

            alertDialog.activity = fragment.getActivity();

            if (fragment instanceof Listener)
                alertDialog.listener = (Listener) fragment;
        }

        /**
         * Sets the a unique purpose code to differentiate
         * between the CallBacks
         */
        public Builder purpose(int purpose) {
            alertDialog.purpose = purpose;
            return this;
        }

        /**
         * Sets the a custom listener to receive the CallBacks
         */
        public Builder listener(Listener listener) {
            alertDialog.listener = listener;
            return this;
        }

        /**
         * Set the data to be sent via callback
         */
        public Builder backpack(Bundle backpack) {
            alertDialog.backpack = backpack;
            return this;
        }

        /**
         * Set the message for the AlertDialog
         */
        public Builder title(int resourceId) {
            return title(alertDialog.activity.getString(resourceId));
        }

        /**
         * Set the message for the AlertDialog
         */
        Builder title(String title) {
            alertDialog.title = title;
            return this;
        }

        /**
         * Set the message for the AlertDialog
         */
        public Builder message(int resourceId) {
            return message(alertDialog.activity.getString(resourceId));
        }

        /**
         * Set the message for the AlertDialog
         */
        Builder message(String message) {
            alertDialog.message = message;
            return this;
        }

        /**
         * Set the actionButton for the AlertDialog
         */
        public Builder button(int resourceId) {
            return button(alertDialog.activity.getString(resourceId));
        }

        /**
         * Set the actionButton for the AlertDialog
         */
        Builder button(String button) {
            alertDialog.actionButton = button;
            return this;
        }

        /**
         * Method to build an AlertDialog and display
         * it on the screen. The instance returned can
         * be used to manipulate the alertDialog in future.
         */
        public AlertDialog build() {
            alertDialog.message = Utils.assign(alertDialog.message, alertDialog.activity.getString(R.string.message));
            alertDialog.actionButton = Utils.assign(alertDialog.actionButton, alertDialog.activity.getString(R.string.ok_text));
            return alertDialog.init();
        }

        /**
         * Method to retrieve a String Resource
         */
        private String getString(int resourceId) {
            return alertDialog.activity.getString(resourceId);
        }
    }
}


