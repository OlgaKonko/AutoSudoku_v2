package com.example.olga_kondratenko.autosudoku_v2.sudoku_generator;

import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.cleaner.SudokuCleaner;
import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.model.Sudoku;

import java.util.Random;

import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.QUANTITY;

public class SudokuGenerator {

    public static Sudoku generateSudoku(long seed) {
        Sudoku sudoku = new Sudoku(seed);
        /*for (int rowIndex= 0; rowIndex<QUANTITY; rowIndex++){
            for (int columnIndex= 0; columnIndex<QUANTITY; columnIndex++){
                System.out.print(sudoku.get(rowIndex, columnIndex)+" ");
                }
                System.out.println();
            }
            System.out.println();
            */
        return compliteSudoku(sudoku);
    }

    public static Sudoku generateTestSudoku(long seed) {
        Sudoku sudoku = new Sudoku(seed);
        Random random = new Random(seed);
        for (int i=0; i<5; i++){

            sudoku.set(random.nextInt(QUANTITY), random.nextInt(QUANTITY), 0);

        }
        return sudoku;
    }

    public static Sudoku compliteSudoku(Sudoku sudoku) {
        SudokuCleaner cleaner = new SudokuCleaner(sudoku);
        cleaner.clearSells();
        return sudoku;
    }

}
