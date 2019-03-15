package com.example.olga_kondratenko.autosudoku_v2.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.QUANTITY;

public class SudokuCellModel implements Serializable{
    boolean given = false;
    int solution = 0;
    List<Boolean> pencilMarks = new ArrayList<>(QUANTITY);

    boolean isBlockSolved = false;
    boolean isColumnSolved = false;
    boolean isRowSolved = false;

    public SudokuCellModel() {
        for (int index = 0; index < QUANTITY; index++) {
            pencilMarks.add(false);
        }
    }

    public int getPenMarksNumber() {
        int number =0;
        for (boolean pencilMark:pencilMarks
                ) {
            if (pencilMark)
               number++;
        }
        return number;
    }

    public int getSolvedLevel() {
        int number =0;
        if (solution>0){
        if (isBlockSolved){
            number++;
        }

        if (isColumnSolved){
            number++;
        }

        if (isRowSolved){
            number++;
        }}
        return number;
    }

    public boolean hasPenMarks() {
        for (boolean pencilMark:pencilMarks
             ) {
            if (pencilMark)
                return true;
        }
        return false;
    }
}
