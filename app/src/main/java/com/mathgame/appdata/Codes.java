package com.mathgame.appdata;

import com.mathgame.R;

public interface Codes {
    interface SnackBarType {
        int ERROR   = 0;
        int SUCCESS = 1;
        int MESSAGE = 2;
    }

    enum PlayerType {
        SINGLE(R.string.single_player, 1),
        DUAL(R.string.dual_player, 2);
        public int label, value;

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

        public int label, value;

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

        public int label, value;

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

    interface RequestCode {
        int OPEN_ADD_CUSTOM_MODE_ACTIVITY = 100;
    }
}
