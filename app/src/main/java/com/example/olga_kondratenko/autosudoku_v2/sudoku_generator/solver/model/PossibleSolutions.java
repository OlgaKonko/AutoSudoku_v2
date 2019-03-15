package com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.solver.model;

import static com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.constants.Constants.SUDOKU_SIZE;

public class PossibleSolutions {
    public int[] solutions;
    public boolean solved;
    public int solution;

    private static int POSSIBLE = 1;
    private static int IMPOSSIBLE = 0;

    public PossibleSolutions(int solution) {
        if (solution != 0) {
            solved = true;
            this.solution = solution;
        } else {
            solved = false;
        }
        setSolutions();

    }

    public void clear(int impossible) {
        solutions[impossible] = IMPOSSIBLE;
    }

    public boolean checkIfSolved() {
        if (!solved) {
            int possibleSolutionsCount = 0;
            int possibleSolution = -1;
            for (int i = 0; i < SUDOKU_SIZE; i++) {
                if (solutions[i] == POSSIBLE) {
                    possibleSolutionsCount++;
                    possibleSolution = i + 1;
                }
            }
            if (possibleSolutionsCount == 1) {
                solution = possibleSolution;
                solved = true;
            }
        }
        return solved;
    }

    public void markImpossible(int number) {
        solutions[number - 1] = IMPOSSIBLE;
    }

    private void setSolutions() {
        solutions = new int[SUDOKU_SIZE];
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            solutions[i] = solved ? IMPOSSIBLE : POSSIBLE;
        }

        if (solved) {
            solutions[solution - 1] = POSSIBLE;
        }
    }
}
