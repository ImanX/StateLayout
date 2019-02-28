package com.github.imanx;

/**
 * Created by ImanX.
 * sample | Copyrights 2019 ZarinPal Crop.
 */
public enum State {
    Loading(0),
    Failure(1),
    Empty(2),
    Normal(3);

    final int state;

    State(int state) {
        this.state = state;
    }

    public static State getState(int d) {
        for (State i : State.values()) {
            if (i.ordinal() == d) return i;
        }
        return State.Loading;
    }
}
