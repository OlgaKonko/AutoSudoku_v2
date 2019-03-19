package com.example.olga_kondratenko.autosudoku_v2.model;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Statistic implements Serializable{
    private static Statistic instance;

    public int totalSolved;
    public int totalGenerated;
    public int totalSkipped;
    public int totalTimeSpend;
    public int minTimeSpend;
    public int avarageTime;


    public Statistic() {
        totalGenerated = 0;
        totalSolved = 0;
        totalSkipped = 0;
        totalTimeSpend = 0;
        minTimeSpend = 0;
        avarageTime = 0;
    }

    public static Statistic get(){
        if (instance == null){
            instance = new Statistic();
        }
        return instance;
    }

    public static void set(Statistic newStatistic){
       instance = newStatistic;
    }
}
