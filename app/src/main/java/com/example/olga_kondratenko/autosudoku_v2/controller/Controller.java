package com.example.olga_kondratenko.autosudoku_v2.controller;


import com.example.olga_kondratenko.autosudoku_v2.model.Level;
import com.example.olga_kondratenko.autosudoku_v2.view.MainFieldActivity;
import com.example.olga_kondratenko.autosudoku_v2.view.ModuleSwitcher;
import com.example.olga_kondratenko.autosudoku_v2.view.ViewController;

import java.io.IOException;

public class Controller {
    private static Controller instance;
    private SudokuActionsController actionsController;
    private InstrumentActionsController instrumentController;
    private SudokuFindController findController;
    private ActivityChangeController changeController;
    private StatisticController statisticController;

    private ModuleSwitcher moduleSwitcher;
    private ViewController viewController;

    private Controller() {
        actionsController = new SudokuActionsController();
        instrumentController = new InstrumentActionsController();
        findController = new SudokuFindController();
        changeController = new ActivityChangeController();
        statisticController = new StatisticController();
    }

    public static Controller getController() {
        if (instance == null) {
            instance = new Controller();

        }
        return instance;
    }

    public static SudokuActionsController getActionsController() {
        return instance.actionsController;
    }

    public static InstrumentActionsController getInstrumentController() {
        return instance.instrumentController;
    }

    public static StatisticController getStatisticController() {
        return instance.statisticController;
    }

    public static SudokuFindController getFindController() {
        return instance.findController;
    }

    public static ModuleSwitcher getModuleSwitcher() {
        return instance.moduleSwitcher;
    }

    public static ViewController getViewController() {
        return instance.viewController;
    }

    public static ActivityChangeController getChangeController() {
        return instance.changeController;
    }

    public void setModuleController(ModuleSwitcher switcher) {
        moduleSwitcher = switcher;
    }

    public void setView(ViewController viewController) {
        this.viewController = viewController;
    }


    public void fieldDrowActions() {
        viewController = MainFieldActivity.instance;
        getFindController().setSudoku();
    }


    public void saveSudoku() {
        try {
            moduleSwitcher.fileManager.saveTimer(getViewController().getTimer());
            moduleSwitcher.fileManager.saveSudoku(getFindController().getSudokuModel());
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

    }
}
