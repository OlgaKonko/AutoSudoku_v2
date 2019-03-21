package com.example.olga_kondratenko.autosudoku_v2.view;


public class Timer implements Runnable{
        long startTime=0;

    @Override
public void run() {
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        MainFieldActivity.instance.timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
        MainFieldActivity.instance.timerHandler.postDelayed(this, 500);
        }

public void resetTimer(){
                startTime =System.currentTimeMillis();
        }

        public void setTimer(long time){
                startTime =System.currentTimeMillis()-time;
                int seconds = (int) (time / 1000);
                int minutes = seconds / 60;
                MainFieldActivity.instance.timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
        }

        public long getTime(){
                return System.currentTimeMillis()-startTime;
        }
}