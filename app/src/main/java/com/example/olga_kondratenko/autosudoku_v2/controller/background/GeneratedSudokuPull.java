package com.example.olga_kondratenko.autosudoku_v2.controller.background;


import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.model.Sudoku;

import java.util.LinkedList;

public class GeneratedSudokuPull{
    private static LinkedList<Sudoku> generatedSudoku = new LinkedList<>();
    private static int capacity = 5;

    public GeneratedSudokuPull() {
       GenerateSugokuThread.getInstance().start();
    }

    public static Sudoku getSudoku(){
      waitUntilSudokuCreated();
       Sudoku sudoku = generatedSudoku.get(0);
       generatedSudoku.remove(0);
       return sudoku;
    }

    public static void waitUntilSudokuCreated(){
        while (generatedSudoku.size()==0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setSudoku(Sudoku newSudoku){
       generatedSudoku.add(newSudoku);
    }

    public static boolean isFill(){
        return generatedSudoku.size()==capacity;
    }
}
