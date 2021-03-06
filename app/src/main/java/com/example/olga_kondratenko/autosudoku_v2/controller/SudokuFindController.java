package com.example.olga_kondratenko.autosudoku_v2.controller;


import com.example.olga_kondratenko.autosudoku_v2.controller.background.SudokuCreateWaiterController;
import com.example.olga_kondratenko.autosudoku_v2.model.Options;
import com.example.olga_kondratenko.autosudoku_v2.model.Statistic;
import com.example.olga_kondratenko.autosudoku_v2.model.SudokuModel;
import com.example.olga_kondratenko.autosudoku_v2.model.Level;
import com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.model.Sudoku;

import java.util.Random;

import static com.example.olga_kondratenko.autosudoku_v2.sudoku_generator.constants.Constants.SUDOKU_SIZE;

public class SudokuFindController {
    private Sudoku sudoku;
    private SudokuModel sudokuModel;
    private boolean isFirstActionDone = false;
    private boolean newGameNeeded = true;

    public SudokuModel getSudokuModel() {
        return sudokuModel;
    }

    public void setSudoku(Sudoku sudoku){
        this.sudoku = sudoku;
        clearField();
        drowNewSudoku();
    }

    public void setFirstActionDone(boolean firstActionDone) {
        isFirstActionDone = firstActionDone;
    }


    public void actionDone() {
        if (!isFirstActionDone){
            Controller.getViewController().startTimer();
            Statistic.get().totalGenerated++;
            isFirstActionDone = true;
        }
    }

    public void loadSudoku(){
        try {
            sudokuModel = Controller.getModuleSwitcher().fileManager.loadSudoku(Options.get().level);
            long time = Controller.getModuleSwitcher().fileManager.loadTimer();
            Controller.getViewController().setTime(time);
            newGameNeeded = false;
            isFirstActionDone = true;
        } catch (Exception e) {
            newGameNeeded = true;
        }
    }
    public void generateSudoku(){
        newGameNeeded = true;
        Controller.getViewController().resetTimer();
        Controller.getModuleSwitcher().showSpinner();
        new SudokuCreateWaiterController().execute();
    }

    public void setSudoku(){
        loadSudoku();
       if (newGameNeeded) {
          generateSudoku();
       }else {
           drowSavedSudoku();
       }
    }

    public void clearField(){
        for (int x = 0; x < SUDOKU_SIZE; x++) {
            for (int y = 0; y < SUDOKU_SIZE; y++) {
                Controller.getViewController().markUsualValue(x,y);
                Controller.getViewController().markEmptyField(x, y);
            }
        }
    }

    public void  drowNewSudoku(){
        Controller.getViewController().resetFocus();
        sudokuModel = new SudokuModel();
        for (int x = 0; x < SUDOKU_SIZE; x++){
            for (int y = 0; y < SUDOKU_SIZE; y++) {
                int number = sudoku.get(x, y);
                sudokuModel.setCellRightSolution(x,y, sudoku.getSolved(x,y));
                if (number > 0) {
                    Controller.getViewController().markGivenValue(x, y, number);
                    sudokuModel.setCellSolution(x, y, number, true);
                }
                else  {
                    Controller.getViewController().markUsualValue(x,y);
                    Controller.getViewController().markEmptyField(x,y);
                }
                Controller.getActionsController().penFillActions(x,y);
            }
        }
        drowHintsByLevel();
        Controller.getViewController().hideNumberField();
    }

    public void drowHintsByLevel(){
        Level level = Options.get().level;
        sudokuModel.setLevel(level);
       for (int index = 0; index < level.getHintQuantity(); index++){
           markHint(false);
       }
    }

    public void markHint(boolean isAsced) {
        Random random = new Random();
        boolean hintAdd = false;
        do {
            int x = random.nextInt(SUDOKU_SIZE);
            int y = random.nextInt(SUDOKU_SIZE);
            if(sudokuModel.getCellSolution(x,y)==0){
                sudokuModel.setCellSolution(x, y, sudokuModel.getCellRightSolution(x,y), true);
                if(isAsced){
                    Controller.getViewController().markHintValue(x, y, sudokuModel.getCellSolution(x, y));
                }
                else {
                    Controller.getViewController().markGivenValue(x, y, sudokuModel.getCellSolution(x, y));
                }
                Controller.getActionsController().penFillActions(x,y);
                hintAdd = true;
            }
        }
        while (!hintAdd);
    }

    public void  drowSavedSudoku() {
        for (int x = 0; x < SUDOKU_SIZE; x++) {
            for (int y = 0; y < SUDOKU_SIZE; y++) {
                if (sudokuModel.isCellGiven(x, y)) {
                    Controller.getViewController().markGivenValue(x, y, sudokuModel.getCellSolution(x, y));
                } else {
                    Controller.getViewController().markAsSolved(x, y, sudokuModel.getSolvedLevel(x, y));
                    if (sudokuModel.getCellSolution(x, y) != 0) {
                        Controller.getViewController().markPenNumber(x, y, sudokuModel.getCellSolution(x, y));
                    } else {
                        if (sudokuModel.getPencilMarksNumber(x, y) > 0) {
                            Controller.getViewController().displayPencilMarkFields(x, y, sudokuModel.getPencilMarks(x, y));
                        } else {
                            Controller.getViewController().markEmptyField(x, y);
                        }
                    }
                }

            }
            Controller.getViewController().hideNumberField();
        }
        if (!sudokuModel.checkVictory()) {
            Controller.getViewController().startTimer();
        }
        else {
            Controller.getViewController().showVictory();
        }
    }
}
