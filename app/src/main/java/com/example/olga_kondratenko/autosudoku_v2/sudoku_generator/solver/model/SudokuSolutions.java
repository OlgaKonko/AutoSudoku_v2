package com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.solver.model;


import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.model.Sudoku;

import static com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.constants.Constants.SUDOKU_SIZE;

public class SudokuSolutions {
    PossibleSolutions[][] field;
    Sudoku sudoku;
    public int solvedQuantity = 0;

    public SudokuSolutions(Sudoku sudoku) {
        this.sudoku = sudoku;
        startFill();
    }

    public void markImpossibleSolutions() {
        for (int x = 0; x < SUDOKU_SIZE; x++)
            for (int y = 0; y < SUDOKU_SIZE; y++)
                if (field[x][y].solved) {
                    markDependeSells(x, y);
                }
    }

    public void markSolved() {
        solvedQuantity = 0;
        for (int x = 0; x < SUDOKU_SIZE; x++)
            for (int y = 0; y < SUDOKU_SIZE; y++) {
                if (field[x][y].checkIfSolved()) {
                    solvedQuantity++;
                }
            }

    }

    public void applySolution() {
        for (int x = 0; x < SUDOKU_SIZE; x++)
            for (int y = 0; y < SUDOKU_SIZE; y++)
                if (field[x][y].solved) {
                    sudoku.set(x, y, field[x][y].solution);
                }
    }

    private void startFill() {
        field = new PossibleSolutions[SUDOKU_SIZE][SUDOKU_SIZE];
        for (int x = 0; x < SUDOKU_SIZE; x++)
            for (int y = 0; y < SUDOKU_SIZE; y++) {
                field[x][y] = new PossibleSolutions(sudoku.get(x, y));
            }
    }

    private void markDependeSells(int x, int y) {
        int number = field[x][y].solution;
        int xSquare = (x / 3) * 3;
        int ySquare = (y / 3) * 3;
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            field[x][i].markImpossible(number);
            field[i][y].markImpossible(number);
            field[xSquare + i % 3][ySquare + i / 3].markImpossible(number);
        }
    }
}
