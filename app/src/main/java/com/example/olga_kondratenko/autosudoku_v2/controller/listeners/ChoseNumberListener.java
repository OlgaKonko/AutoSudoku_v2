package com.example.olga_kondratenko.autosudoku_v2.controller.listeners;


import android.view.View;

import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;

public class ChoseNumberListener implements View.OnClickListener  {
    private int indexNumber;

    public ChoseNumberListener(int number) {
        indexNumber = number;
    }

    @Override
    public void onClick(View view) {
       Controller.getActionsController().markNumber(indexNumber);
    }
}
