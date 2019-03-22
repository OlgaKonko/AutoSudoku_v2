package com.example.olga_kondratenko.autosudoku_v2.model;

import java.io.Serializable;
import java.util.Map;

public class Statistic implements Serializable{
    private static Statistic instance;

    public int totalSolved;
    public int totalGenerated;
    public int totalSkipped;
    public long totalTimeSpend;
    public long totalTimeWasted;
    public long minTimeSpend;
  //  public int avarageTime;

    private Statistic() {
        totalGenerated = 0;
        totalSolved = 0;
        totalSkipped = 0;
        totalTimeSpend = 0;
        minTimeSpend = 0;
        totalTimeWasted =0;
       // avarageTime = 0;
    }

    public static Statistic get(){
        if (instance == null){
            instance = new Statistic();
        }
        return instance;
    }

    public static void reset(){
       instance = new Statistic();
    }

    public static void set(Statistic newStatistic){
       instance = newStatistic;
    }
}
