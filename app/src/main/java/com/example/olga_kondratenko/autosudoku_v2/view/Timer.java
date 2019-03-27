package com.example.olga_kondratenko.autosudoku_v2.view;


public class Timer implements Runnable{
        long startTime=0;
        boolean isStarted=false;
        long pauseStart =0;
        long pauseTime;

    @Override
public void run() {
            isStarted = true;
        long millis = System.currentTimeMillis() - startTime-pauseTime;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        MainFieldActivity.instance.timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
        MainFieldActivity.instance.timerHandler.postDelayed(this, 500);
        }

public void resetTimer(){
                startTime =System.currentTimeMillis();
                pauseTime = 0;
        }

        public void setTimer(long time){
                startTime =System.currentTimeMillis()-time;
                int seconds = (int) (time / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                MainFieldActivity.instance.timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
        }

        public long getTime(){
                return System.currentTimeMillis()-startTime-pauseTime;
        }

        public void saveCurrentTime() {
                pauseStart = System.currentTimeMillis();
        }

        public void setPauseTime() {
                pauseTime = pauseTime+ System.currentTimeMillis()- pauseStart;
        }
}