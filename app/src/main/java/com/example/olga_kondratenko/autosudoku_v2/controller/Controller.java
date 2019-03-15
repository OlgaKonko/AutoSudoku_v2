package com.example.olga_kondratenko.autosudoku_v2.controller;


import com.example.olga_kondratenko.autosudoku_v2.model.Instrument;
import com.example.olga_kondratenko.autosudoku_v2.model.SudokuModel;
import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.SudokuGenerator;
import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.model.Sudoku;
import com.example.olga_kondratenko.autosudoku_v2.view.MainFieldActivity;
import com.example.olga_kondratenko.autosudoku_v2.view.ModuleSwitcher;
import com.example.olga_kondratenko.autosudoku_v2.view.ViewController;

import java.io.IOException;

import static com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.constants.Constants.SUDOKU_SIZE;
import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.BLOCK_SIZE;
import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.QUANTITY;

public class Controller {
    private static Controller instance;
    private ModuleSwitcher moduleSwitcher;
    private ViewController viewController;
    private SudokuModel sudokuModel;
    private Sudoku sudoku;
    private int currentCellX;
    private int currentCellY;

    private Instrument instrument;

    private Controller() {
    }

    public static Controller getController() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;

    }

    public Controller setModuleController(ModuleSwitcher switcher) {
        moduleSwitcher = switcher;
        return this;
    }

    public Controller setView(ViewController viewController) {
        this.viewController = viewController;
        return this;
    }

    public Controller start() {
        new GeneratedSudokuPull();
        showMenu();
        return this;
    }

    public void getSudoku() {
        try {
            sudokuModel = moduleSwitcher.loadSudoku();
        } catch (Exception e) {
            sudoku = GeneratedSudokuPull.getSudoku();
        }
    }

    public Controller setSudoku() {
        getSudoku();
        return this;
    }

    public Controller showMenu() {
        moduleSwitcher.showMenu();
        return this;
    }

    public Controller startGame() {
        instrument = Instrument.PEN;
        moduleSwitcher.showGame();
        return this;
    }

    public Controller fieldDrowActions() {
        viewController = MainFieldActivity.instance;
        setInstrument(instrument);
        setSudoku();
        fillField();
        return this;
    }

    public Controller newSudokuStart() {
        sudoku = GeneratedSudokuPull.getSudoku();
        drowNewSudoku();
        return this;
    }

    public Controller fillField() {
        setInstrument(instrument);

        if (sudokuModel == null) {
            sudokuModel = new SudokuModel();

            for (int x = 0; x < SUDOKU_SIZE; x++)
                for (int y = 0; y < SUDOKU_SIZE; y++) {
                    int number = sudoku.get(x, y);
                    if (number > 0) {

                        viewController.markGivenValue(x, y, number);
                        sudokuModel.setCellSolution(x, y, number, true);
                    }
                    currentCellX = x;
                    currentCellY = y;

                    penFillActions();
                }
        } else
            {
            for (int x = 0; x < SUDOKU_SIZE; x++)
                for (int y = 0; y < SUDOKU_SIZE; y++) {
                    if (sudokuModel.isCellGiven(x, y)) {
                        viewController.markGivenValue(x, y, sudokuModel.getCellSolution(x, y));
                    } else {
                        viewController.markAsSolved(x, y, sudokuModel.getSolvedLevel(x, y));
                        if (sudokuModel.getCellSolution(x,y)!=0){
                            viewController.markPenNumber(x, y, sudokuModel.getCellSolution(x, y));
                        }
                        else {
                            if (sudokuModel.getPencilMarksNumber(x,y)>0){
                                viewController.displayPencilMarkFields(x, y, sudokuModel.getPencilMarks(x,y));
                            }
                            else {
                                viewController.markEmptyField(x,y);
                            }
                        }
                    }

                }

        }
        return this;
    }

    private void drowNewSudoku(){
        sudokuModel = new SudokuModel();

        for (int x = 0; x < SUDOKU_SIZE; x++)
            for (int y = 0; y < SUDOKU_SIZE; y++) {
                int number = sudoku.get(x, y);
                if (number > 0) {

                    viewController.markGivenValue(x, y, number);
                    sudokuModel.setCellSolution(x, y, number, true);
                }
                else {
                    viewController.markUsualValue(x,y);
                   viewController.markEmptyField(x,y);
                }
                currentCellX = x;
                currentCellY = y;

                penFillActions();
            }
    }

    public Controller setInstrument(Instrument instrument) {
        if (this.instrument != instrument) {
            this.instrument = instrument;
            viewController.setInstrument(instrument);
        }
        return this;
    }

    public Controller onCellClick(int x, int y) {
        currentCellX = x;
        currentCellY = y;
        if (!sudokuModel.isCellGiven(currentCellX, currentCellY)) {
            viewController.markCellFocused(currentCellX, currentCellY);
            viewController.showNumbersField();
            viewController.markNumberField();
            if (instrument == Instrument.PENCIL) {
                viewController.markNumberField(sudokuModel.getPencilMarks(currentCellX, currentCellY));
            }
        }
        return this;
    }

    public Controller markNumber(int indexNumber) {
        int solutionNumber = indexNumber + 1;
        if (sudokuModel.isCellGiven(currentCellX, currentCellY)) {
            viewController.hideNumberField();
        } else {
            if (instrument == Instrument.PEN) {
                markPen(solutionNumber);
            } else if (instrument == Instrument.PENCIL) {
                markPencil(indexNumber);
            }
        }
        saveSudoku();
        return this;
    }

    private void markPen(int solutionNumber) {
        viewController.markNumberField();
        if (sudokuModel.getCellSolution(currentCellX, currentCellY) != solutionNumber) {
            sudokuModel.setCellSolution(currentCellX, currentCellY, solutionNumber, false);
            viewController.markPenNumber(currentCellX, currentCellY, solutionNumber);
        } else {
            sudokuModel.setCellSolution(currentCellX, currentCellY, 0, false);
            if (sudokuModel.getPencilMarksNumber(currentCellX, currentCellY) > 0) {
                viewController.displayPencilMarkFields(currentCellX, currentCellY, sudokuModel.getPencilMarks(currentCellX, currentCellY));
                viewController.markNumberField(sudokuModel.getPencilMarks(currentCellX, currentCellY));
            } else {
                viewController.markEmptyField(currentCellX, currentCellY);
            }
        }
        penFillActions();
    }

    private void markPencil(int indexNumber) {
        if (sudokuModel.getCellSolution(currentCellX, currentCellY) != 0) {
            sudokuModel.setCellSolution(currentCellX, currentCellY, 0, false);
            viewController.markEmptyField(currentCellX, currentCellY);
            penFillActions();
        }
        if (sudokuModel.isPencilMarkPresent(currentCellX, currentCellY, indexNumber)) {
            sudokuModel.setPencilNumber(currentCellX, currentCellY, indexNumber, false);
            viewController.removePencilMark(currentCellX, currentCellY, indexNumber);
            if (sudokuModel.getPencilMarksNumber(currentCellX, currentCellY) == 0) {
                viewController.markEmptyField(currentCellX, currentCellY);
            }
        } else {
            sudokuModel.setPencilNumber(currentCellX, currentCellY, indexNumber, true);
            viewController.markPencil(currentCellX, currentCellY, indexNumber);
        }
        viewController.markNumberField(sudokuModel.getPencilMarks(currentCellX, currentCellY));
    }

    private void penFillActions() {
        markSolvedLevel();
        checkVictory();

    }

    private void markSolvedLevel() {
        boolean wasBlockSolved = sudokuModel.checkCellBlockSolved(currentCellX, currentCellY);
        boolean wasColumnSolved = sudokuModel.checkCellColumnSolved(currentCellX, currentCellY);
        boolean wasRowSolved = sudokuModel.checkCellRowSolved(currentCellX, currentCellY);

        boolean isBlockSolved = sudokuModel.checkCellFillBlock(currentCellX, currentCellY);
        boolean isColumnSolved = sudokuModel.checkCellFillColumn(currentCellX, currentCellY);
        boolean isRowSolved = sudokuModel.checkCellFillRow(currentCellX, currentCellY);

        System.out.println("!!! Cell " + currentCellX + " " + currentCellY);
        System.out.println("!!! Block " + wasBlockSolved + " " + isBlockSolved);
        System.out.println("!!! Column " + wasColumnSolved + " " + isColumnSolved);
        System.out.println("!!! Row " + wasRowSolved + " " + isRowSolved);
        System.out.println();

        if (wasBlockSolved != isBlockSolved) {
            int startX = (currentCellX - (currentCellX % BLOCK_SIZE));
            int startY = (currentCellY - (currentCellY % BLOCK_SIZE));

            for (int index = 0; index < QUANTITY; index++) {
                int newCellX = (startX + index / BLOCK_SIZE);
                int newCellY = (startY + index % BLOCK_SIZE);
                if (!sudokuModel.isCellGiven(newCellX, newCellY)) {
                    int level = sudokuModel.getSolvedLevel(newCellX, newCellY);
                    viewController.markAsSolved(newCellX, newCellY, level);
                }
            }
        }

        if (wasColumnSolved != isColumnSolved) {
            for (int index = 0; index < QUANTITY; index++) {
                if (!sudokuModel.isCellGiven(index, currentCellY)) {
                    int level = sudokuModel.getSolvedLevel(index, currentCellY);
                    viewController.markAsSolved(index, currentCellY, level);
                }
            }
        }

        if (wasRowSolved != isRowSolved) {
            for (int index = 0; index < QUANTITY; index++) {
                if (!sudokuModel.isCellGiven(currentCellX, index)) {
                    int level = sudokuModel.getSolvedLevel(currentCellX, index);
                    viewController.markAsSolved(currentCellX, index, level);
                }
            }
        }
    }

    private void checkVictory() {
        if (sudokuModel.checkVictory()) {
            System.out.println("Wictory!");
            viewController.showVictory();
        }
    }

    private void saveSudoku() {
        try {
            moduleSwitcher.saveSudoku(sudokuModel);
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

    }
}
