package com.example.olga_kondratenko.autosudoku_v2.controller.listeners;


import android.view.View;

import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;

public class OneMoreSudokuListener implements View.OnClickListener  {
    @Override
    public void onClick(View v) {
        Controller.getViewController().hideVictory();
        Controller.getStatisticController().onNewGenerete();
        Controller.getFindController().generateSudoku();
    }
}
