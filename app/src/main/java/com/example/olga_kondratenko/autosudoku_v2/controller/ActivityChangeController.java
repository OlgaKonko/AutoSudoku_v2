package com.example.olga_kondratenko.autosudoku_v2.controller;


import com.example.olga_kondratenko.autosudoku_v2.controller.background.GeneratedSudokuPull;
import com.example.olga_kondratenko.autosudoku_v2.model.Instrument;
import com.example.olga_kondratenko.autosudoku_v2.view.ModuleSwitcher;

public class ActivityChangeController {

    private ModuleSwitcher getModuleSwitcher(){
       return Controller.getModuleSwitcher();
    }

    public void showMenu() {
        getModuleSwitcher().showMenu();
    }

    public void start() {
        new GeneratedSudokuPull();
        showMenu();
    }

    public void startGame() {
        Controller.getInstrumentController().initInstrument(Instrument.PEN);
        getModuleSwitcher().showGame();
    }
}
