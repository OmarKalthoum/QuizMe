package com.hkr.quizme.timers;

import android.util.Log;

public class UpdatingTimer implements Runnable {
    private boolean hasAnswered;
    private int timer;
    private TimerListener timerListener;

    public UpdatingTimer(TimerListener timerListener, int time) {
        hasAnswered = false;
        timer = time;
        this.timerListener = timerListener;
    }

    @Override
    public void run() {
        while (!hasAnswered) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                Log.e("QuestionTimer::", exception.toString());
            }
            timer -= 1;
            if (timer <= 0) {
                stop();
                break;
            }
            timerListener.onTimerUpdate(timer);
        }
    }

    public void stop() {
        hasAnswered = true;
        timerListener.onTimerStop();
    }
}
