package com.mathgame.util;

public interface Codes {
    interface SnackBarType {
        int ERROR   = 0;
        int SUCCESS = 1;
        int MESSAGE = 2;
    }

    interface PlayerType {
        int SINGLE = 1;
        int DUAL   = 2;
    }

    interface TimerType {
        int NONE         = 1;
        int PER_TEST     = 2;
        int PER_QUESTION = 3;
    }

    interface GameType {
        int MULTIPLE_CHOICE = 0;
        int YES_NO         = 1;
        int MANUAL_INPUT   = 2;
    }
}
