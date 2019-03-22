package com.example.olga_kondratenko.autosudoku_v2.model;


import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;

import java.io.Serializable;

public class Options implements Serializable {
    private static Options instance;
    public Level level;

    public Options() {
        level = Level.EASY;
    }

    public static Options get(){
        if (instance == null){
            try {
                Controller.getModuleSwitcher().fileManager.loadOptions();
            } catch (Exception e) {
                instance = new Options();
            }
        }
        return instance;
    }

    public static void reset(){
        instance = new Options();
    }

    public static void set(Options newOptions){
        instance = newOptions;
    }
}
