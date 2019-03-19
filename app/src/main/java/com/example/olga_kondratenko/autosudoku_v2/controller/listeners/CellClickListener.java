package com.example.olga_kondratenko.autosudoku_v2.controller.listeners;


import android.view.View;

import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;

public class CellClickListener implements View.OnClickListener  {
    private int x = 0;
    private int y = 0;

    public CellClickListener(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void onClick(View view) {
       Controller.getActionsController().onCellClick(x, y);
    }

}
