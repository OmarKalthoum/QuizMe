package com.hkr.quizme.timers;

public interface TimerListener {
    void onTimerUpdate(int timeLeft);
    void onTimerStop();
}
