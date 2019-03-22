package com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.model;

import java.util.Random;

import static com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.constants.Constants.SUDOKU_SIZE;
public class Sudoku {
    int[][] field;
    int[][] solvedField;
    public long seed;
    long realSeed;
    Random random;
    boolean isCorrectSudoku;

    public Sudoku(long seed) {
        field = new int[SUDOKU_SIZE][SUDOKU_SIZE];
        solvedField = new int[SUDOKU_SIZE][SUDOKU_SIZE];
        this.seed = seed;
        realSeed = seed;
        random = new Random(realSeed);
        correctFill();
    }

    public int get(int x, int y) {
        return field[x][y];
    }

    public int getSolved(int x, int y) {
        return solvedField[x][y];
    }

    public void set(int x, int y, int number) {
        field[x][y] = number;
    }

    public void clearSell(int x, int y) {
        field[x][y] = 0;
    }

    private void correctFill() {
        isCorrectSudoku = false;
        while (!isCorrectSudoku) {
            isCorrectSudoku = true;
            realSeed++;
            random.setSeed(realSeed);
            fill();
        }
        endFill();
    }

    private void fill() {
        startFill();

        for (int x = 0; x < SUDOKU_SIZE; x++)
            for (int y = 0; y < SUDOKU_SIZE; y++) {
                field[x][y] = findNumber(x, y);
            }
    }

    private int findNumber(int x, int y) {
        int number = random.nextInt(SUDOKU_SIZE) + 1;
        int count = 0;
        while (isNumberRepeat(x, y, number) && count <= SUDOKU_SIZE) {
            count++;
            number++;
            if (number > SUDOKU_SIZE)
                number = 1;
        }

        if (count > SUDOKU_SIZE) {
            isCorrectSudoku = false;
            return -1;
        }

        return number;
    }

    private boolean isNumberRepeat(int x, int y, int number) {
        int xSquare = (x / 3) * 3;
        int ySquare = (y / 3) * 3;
        boolean repeat = false;
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            if ((field[x][i] == number) || (field[i][y] == number) || (field[xSquare + i % 3][ySquare + i / 3] == number)) {
                repeat = true;
            }


        }

        return repeat;
    }

    public void startFill() {
        for (int x = 0; x < SUDOKU_SIZE; x++)
            for (int y = 0; y < SUDOKU_SIZE; y++) {
                field[x][y] = 0;
            }
    }

    public void endFill() {
        for (int x = 0; x < SUDOKU_SIZE; x++)
            for (int y = 0; y < SUDOKU_SIZE; y++) {
                solvedField[x][y] = field[x][y];
            }
    }
}
