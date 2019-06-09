package com.mathgame.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.plugin.CountDownTimerWithPause;
import com.mathgame.util.AudioUtils;
import com.mathgame.util.Utils;
import com.rey.material.widget.ImageButton;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Developer: Rishabh
 * Dated: 23/07/15.
 */
public class GameCountdownDialog {

    private static final String TAG = GameCountdownDialog.class.getSimpleName();

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
     * The message to be set on the Dialog
     */
    private String message;

    /**
     * The text to be set on the Action Button
     */
    private String actionButton;

    private int                     timerValue = 4;
    /**
     * The data to sent via the Dialog from the
     * remote parts of the Activity to other
     * parts
     */
    private Bundle                  backpack;
    private CountDownTimerWithPause countDownTimer;

    /**
     * Method to create and display the alert alertDialog
     *
     * @return
     */
    private GameCountdownDialog init() {

        try {

            alertDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            alertDialog.setContentView(R.layout.dialog_game_countdown);

            Window dialogWindow = alertDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = 0.9f;

            dialogWindow.getAttributes().windowAnimations = R.style.CustomDialog;

            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);

            TextView tvMessage = alertDialog.findViewById(R.id.tvMessage);
            ImageButton ibCancel = alertDialog.findViewById(R.id.ibCancel);
            TextView tvCountdown = alertDialog.findViewById(R.id.tvCountdown);

            tvMessage.setText(Utils.fromHtml(message));
            ibCancel.setOnClickListener(v -> {
                dismiss();

                if (listener != null)
                    listener.performNegativeAction(purpose, backpack);
            });

            Animation zoomIn = AnimationUtils.loadAnimation(activity, R.anim.zoom_in);
            Animation zoomOut = AnimationUtils.loadAnimation(activity, R.anim.zoom_out);

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            tvCountdown.startAnimation(zoomIn);
            AudioUtils.playAudio(activity,R.raw.game_countdown);
            countDownTimer = new CountDownTimerWithPause(timerValue * 1000, 500, true) {
                @Override
                public void onTick(long millis) {
                    String hms = String.format(Locale.ENGLISH, "%d", TimeUnit.MILLISECONDS.toSeconds(millis));
                    tvCountdown.setText(hms);
                }

                @Override
                public void onFinish() {
                    tvCountdown.setText(String.valueOf(0));
                    dismiss();
                    if (listener != null)
                        listener.performPositiveAction(purpose, backpack);
                }
            };
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
                    countDownTimer.create();
                    alertDialog.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
    }

    /**
     * Method to dismiss the AlertDialog, if possible
     */
    private void dismiss() {
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
                        countDownTimer.cancel();
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
    public interface Listener {

        /**
         * Override this method to listen to
         * the events fired from AlertDialog
         */
        void performPositiveAction(int purpose, Bundle backpack);

        void performNegativeAction(int purpose, Bundle backpack);
    }


    /**
     * Class to help building the Alert Dialog
     */
    public static class Builder {

        private final GameCountdownDialog alertDialog = new GameCountdownDialog();

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
         * Set the actionButton for the AlertDialog
         */
        Builder timerValue(int timer) {
            alertDialog.timerValue = timer;
            return this;
        }

        /**
         * Method to build an AlertDialog and display
         * it on the screen. The instance returned can
         * be used to manipulate the alertDialog in future.
         */
        public GameCountdownDialog build() {
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


