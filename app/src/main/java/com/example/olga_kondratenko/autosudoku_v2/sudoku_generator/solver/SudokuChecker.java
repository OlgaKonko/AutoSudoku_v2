package com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.solver;


import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.model.Sudoku;

public class SudokuChecker {

    public static boolean canBeSolved(Sudoku sudoku) {
        SudokuSolver solver = new SudokuSolver(sudoku);
        return solver.solve();
    }
}
