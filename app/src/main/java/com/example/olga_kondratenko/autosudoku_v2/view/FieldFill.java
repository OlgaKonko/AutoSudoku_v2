package com.example.olga_kondratenko.autosudoku_v2.view;

import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;

public class FieldFill implements Runnable {
    @Override
    public void run() {
        Controller.getController().fieldDrowActions();
    }
}
