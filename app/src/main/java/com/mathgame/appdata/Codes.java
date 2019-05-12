package com.mathgame.appdata;

import com.mathgame.R;

public interface Codes {
    enum PlayerType {
        SINGLE(R.string.single_player, 1),
        DUAL(R.string.dual_player, 2);
        final        int label;
        public final int value;

        PlayerType(int label, int value) {
            this.label = label;
            this.value = value;
        }

        public static PlayerType get(int value) {
            PlayerType status = SINGLE;
            for (PlayerType playerType : values()) {
                if (playerType.value == value) {
                    status = playerType;
                    break;
                }
            }
            return status;
        }
    }

    enum TimerType {
        NONE(R.string.none, 0),
        PER_TEST(R.string.per_test, 1),
        PER_QUESTION(R.string.per_question, 2);

        final int label;
        final int value;

        TimerType(int label, int value) {
            this.label = label;
            this.value = value;
        }

        public static TimerType get(int value) {
            TimerType status = NONE;
            for (TimerType timerType : values()) {
                if (timerType.value == value) {
                    status = timerType;
                    break;
                }
            }
            return status;
        }
    }

    enum GameType {
        MULTIPLE_CHOICE(R.string.multiple_choice, 0),
        YES_NO(R.string.yes_no, 1),
        MANUAL_INPUT(R.string.manual_input, 2);

        public final int label;
        public final int value;

        GameType(int label, int value) {
            this.label = label;
            this.value = value;
        }

        public static GameType get(int value) {
            GameType status = MULTIPLE_CHOICE;
            for (GameType gameType : values()) {
                if (gameType.value == value) {
                    status = gameType;
                    break;
                }
            }
            return status;
        }
    }

    interface SnackBarType {
        int ERROR   = 0;
        int SUCCESS = 1;
        int MESSAGE = 2;
    }

    interface RequestCode {
        int OPEN_ADD_CUSTOM_MODE_ACTIVITY = 100;
        int OPEN_SINGLE_GAME_ACTIVITY     = 101;
        int OPEN_GAME_TYPE_ACTIVITY       = 102;
    }

    interface SettingsIds {
        int ADDITION      = 501;
        int SUBTRACTION   = 502;
        int MULTIPLICATON = 503;
        int DIVISION      = 504;
        int SQUARE_ROOT   = 505;
        int PERCENTAGE    = 506;
    }
}
