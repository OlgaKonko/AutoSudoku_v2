package com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.cleaner;

import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.model.Sudoku;

import java.util.Random;


import static com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.constants.Constants.SUDOKU_SIZE;
import static com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.solver.SudokuChecker.canBeSolved;

public class SudokuCleaner {
    private Sudoku sudoku;
    private Random random;
    private Sell deletedSell;
    private int MAX_TRY = 81;

    public SudokuCleaner(Sudoku sudoku) {
        this.sudoku = sudoku;
        random = new Random(sudoku.seed);

    }

    public void clearSells() {
        int tries = 0;
        while (canBeSolved(sudoku) && tries < MAX_TRY) {
            findSellToDelete();
            sudoku.clearSell(deletedSell.x, deletedSell.y);
            if (!canBeSolved(sudoku)) {
                refillSell();
                tries++;
            } else {
                tries = 0;
            }
        }
    }

    private void refillSell() {
        sudoku.set(deletedSell.x, deletedSell.y, deletedSell.number);
    }

    private void findSellToDelete() {
        int x;
        int y;
        do {
            x = random.nextInt(SUDOKU_SIZE);
            y = random.nextInt(SUDOKU_SIZE);
        }
        while (sudoku.get(x, y) == 0);
        deletedSell = new Sell(x, y, sudoku.get(x, y));
    }
}
