package com.example.olga_kondratenko.autosudoku_v2.controller.listeners;


import android.view.View;

import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;

public class GameStartListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        Controller.getController().startGame();
    }
}
