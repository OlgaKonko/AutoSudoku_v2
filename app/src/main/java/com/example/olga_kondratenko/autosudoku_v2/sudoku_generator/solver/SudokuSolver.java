package com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.solver;


import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.model.Sudoku;
import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.solver.model.SudokuSolutions;

import static com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.constants.Constants.SUDOKU_SIZE;

public class SudokuSolver {
    Sudoku sudoku;
    SudokuSolutions sudokuSolutions;

    public SudokuSolver(Sudoku sudoku) {
        this.sudoku = sudoku;
        sudokuSolutions = new SudokuSolutions(sudoku);
    }

    public boolean solve() {
        int solvedQuantity = 0;
        sudokuSolutions.markImpossibleSolutions();
        sudokuSolutions.markSolved();
        int newSolvedQuantity = sudokuSolutions.solvedQuantity;

        while (newSolvedQuantity > solvedQuantity) {
            solvedQuantity = newSolvedQuantity;
            sudokuSolutions.markImpossibleSolutions();
            sudokuSolutions.markSolved();
            newSolvedQuantity = sudokuSolutions.solvedQuantity;
        }
        return (sudokuSolutions.solvedQuantity == SUDOKU_SIZE * SUDOKU_SIZE);

    }

    public void applySolution() {
        sudokuSolutions.applySolution();
    }
}
