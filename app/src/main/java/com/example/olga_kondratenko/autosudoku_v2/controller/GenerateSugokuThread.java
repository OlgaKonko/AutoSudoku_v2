package com.example.olga_kondratenko.autosudoku_v2.controller;


import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.SudokuGenerator;

public class GenerateSugokuThread extends Thread {

    private static volatile GenerateSugokuThread instance;

    private GenerateSugokuThread() {
        setDaemon(true);
    }

    public static GenerateSugokuThread getInstance() {
        if (instance == null) {
            synchronized (GenerateSugokuThread.class) {
                if (instance == null)
                    instance = new GenerateSugokuThread();
            }
        }
        return instance;
    }


    public void run() {
        while (true) {
            if (!GeneratedSudokuPull.isFill()) {
                long seed = System.currentTimeMillis();
                GeneratedSudokuPull.setSudoku(SudokuGenerator.generateSudoku(seed));
            } else {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
