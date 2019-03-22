package com.example.olga_kondratenko.autosudoku_v2.controller;


import android.content.Context;

import com.example.olga_kondratenko.autosudoku_v2.model.Statistic;
import com.example.olga_kondratenko.autosudoku_v2.model.SudokuModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StatisticController {

    public void onVictory(){
    long time = Controller.getViewController().getTimer();
    if (Statistic.get().minTimeSpend>time || Statistic.get().minTimeSpend==0){
        Statistic.get().minTimeSpend = time;
    }
    Statistic.get().totalTimeSpend+=time;
    Statistic.get().totalSolved++;
    try {
        Controller.getModuleSwitcher().fileManager.saveStatistic();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}

    public void onNewGenerete(){
        long time = Controller.getViewController().getTimer();
        Statistic.get().totalTimeSpend+=time;
        Statistic.get().totalGenerated++;
        try {
            Controller.getModuleSwitcher().fileManager.saveStatistic();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
