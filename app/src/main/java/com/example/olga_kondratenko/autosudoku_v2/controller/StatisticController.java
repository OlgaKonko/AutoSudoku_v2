package com.example.olga_kondratenko.autosudoku_v2.controller;

import com.example.olga_kondratenko.autosudoku_v2.model.Statistic;

public class StatisticController {

    public void reset(){
      Statistic.reset();
        try {
            Controller.getModuleSwitcher().fileManager.saveStatistic();
        } catch (Exception e) {

        }
    }

    public void onVictory(){
    long time = Controller.getViewController().getTimer();
    if (Statistic.get().minTimeSpend>time || Statistic.get().minTimeSpend==0){
        Statistic.get().minTimeSpend = time;
    }
    Statistic.get().totalTimeSpend+=time;
    Statistic.get().totalSolved++;
    try {
        Controller.getModuleSwitcher().fileManager.saveStatistic();
    } catch (Exception e) {

    }
}

    public void onNewGenerete(){
        long time = Controller.getViewController().getTimer();
        Statistic.get().totalTimeSpend+=time;
        Statistic.get().totalGenerated++;
        try {
            Controller.getModuleSwitcher().fileManager.saveStatistic();
        } catch (Exception e) {

        }
    }


}
