package com.example.olga_kondratenko.autosudoku_v2.controller;


import com.example.olga_kondratenko.autosudoku_v2.model.Instrument;
import com.example.olga_kondratenko.autosudoku_v2.model.SudokuModel;

import java.util.List;

import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.BLOCK_SIZE;
import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.QUANTITY;

public class SudokuActionsController {

    private int currentCellX;
    private int currentCellY;

    public SudokuModel getSudokuModel(){
        return Controller.getFindController().getSudokuModel();
    }
    public void onCellClick(int x, int y) {
        Controller.getFindController().actionDone();
        currentCellX = x;
        currentCellY = y;
        if (!getSudokuModel().isCellGiven(currentCellX, currentCellY)) {
            Controller.getViewController().markCellFocused(currentCellX, currentCellY);
            Controller.getViewController().showNumbersField();
            Controller.getViewController().markNumberField();
            markUsedNumbers();
            if (Controller.getInstrumentController().getInstrument() == Instrument.PENCIL) {
                Controller.getViewController().markNumberField(getSudokuModel().getPencilMarks(currentCellX, currentCellY));
            }
        }
        else {
            Controller.getViewController().hideNumberField();
        }
    }

    public void markNumber(int indexNumber) {
        Controller.getFindController().actionDone();
        int solutionNumber = indexNumber + 1;
        if (getSudokuModel().isCellGiven(currentCellX, currentCellY)) {
            Controller.getViewController().hideNumberField();
        } else {
            if (Controller.getInstrumentController().getInstrument() == Instrument.PEN) {
                markPen(solutionNumber);
            } else if (Controller.getInstrumentController().getInstrument() == Instrument.PENCIL) {
                markPencil(indexNumber);
            }
        }
        Controller.getController().saveSudoku();
    }

    private void markPen(int solutionNumber) {
        Controller.getViewController().markNumberField();
        if (getSudokuModel().getCellSolution(currentCellX, currentCellY) != solutionNumber) {
            getSudokuModel().setCellSolution(currentCellX, currentCellY, solutionNumber, false);
            Controller.getViewController().markPenNumber(currentCellX, currentCellY, solutionNumber);
            unmarkPencilOnBlock(solutionNumber-1);
        } else {
            getSudokuModel().setCellSolution(currentCellX, currentCellY, 0, false);
            if (getSudokuModel().getPencilMarksNumber(currentCellX, currentCellY) > 0) {
                Controller.getViewController().displayPencilMarkFields(currentCellX, currentCellY, getSudokuModel().getPencilMarks(currentCellX, currentCellY));
                Controller.getViewController().markNumberField(getSudokuModel().getPencilMarks(currentCellX, currentCellY));
            } else {
                Controller.getViewController().markEmptyField(currentCellX, currentCellY);
            }
        }
        penFillActions();
    }

    private void unmarkPencilOnBlock(int indexNumber){
        int startX = (currentCellX - (currentCellX % BLOCK_SIZE));
        int startY = (currentCellY - (currentCellY % BLOCK_SIZE));


        for (int index = 0; index < QUANTITY; index++) {
            int newCellX = (startX + index / BLOCK_SIZE);
            int newCellY = (startY + index % BLOCK_SIZE);
            if (getSudokuModel().isPencilMarkPresent(newCellX, newCellY, indexNumber)){
                getSudokuModel().setPencilNumber(newCellX, newCellY, indexNumber, false);
                if (getSudokuModel().getCellSolution(newCellX, newCellY) == 0){
                    Controller.getViewController().removePencilMark(newCellX, newCellY, indexNumber);
                    if (getSudokuModel().getPencilMarksNumber(newCellX, newCellY) == 0) {
                        Controller.getViewController().markEmptyField(newCellX, newCellY);
                    }
                }
            }
        }
    }

    private void markPencil(int indexNumber) {
        if (getSudokuModel().getCellSolution(currentCellX, currentCellY) != 0) {
            getSudokuModel().setCellSolution(currentCellX, currentCellY, 0, false);
            Controller.getViewController().markEmptyField(currentCellX, currentCellY);
            penFillActions();
        }
        if (getSudokuModel().isPencilMarkPresent(currentCellX, currentCellY, indexNumber)) {
            getSudokuModel().setPencilNumber(currentCellX, currentCellY, indexNumber, false);
            Controller.getViewController().removePencilMark(currentCellX, currentCellY, indexNumber);
            if (getSudokuModel().getPencilMarksNumber(currentCellX, currentCellY) == 0) {
                Controller.getViewController().markEmptyField(currentCellX, currentCellY);
            }
        } else {
            getSudokuModel().setPencilNumber(currentCellX, currentCellY, indexNumber, true);
            Controller.getViewController().markPencil(currentCellX, currentCellY, indexNumber);
        }
        Controller.getViewController().markNumberField(getSudokuModel().getPencilMarks(currentCellX, currentCellY));
    }

    public void penFillActions() {
        penFillActions(currentCellX, currentCellY);
    }

    public void penFillActions(int x, int y) {
        markSolvedLevel(x,y);
        markUsedNumbers();
        checkVictory();
    }

    private void markSolvedLevel(int x, int y) {
        boolean wasBlockSolved = getSudokuModel().checkCellBlockSolved(x, y);
        boolean wasColumnSolved = getSudokuModel().checkCellColumnSolved(x, y);
        boolean wasRowSolved = getSudokuModel().checkCellRowSolved(x, y);

        boolean isBlockSolved = getSudokuModel().checkCellFillBlock(x, y);
        boolean isColumnSolved = getSudokuModel().checkCellFillColumn(x, y);
        boolean isRowSolved = getSudokuModel().checkCellFillRow(x, y);

        if (wasBlockSolved != isBlockSolved) {
            int startX = (x - (x % BLOCK_SIZE));
            int startY = (y - (y % BLOCK_SIZE));

            for (int index = 0; index < QUANTITY; index++) {
                int newCellX = (startX + index / BLOCK_SIZE);
                int newCellY = (startY + index % BLOCK_SIZE);
                if (!getSudokuModel().isCellGiven(newCellX, newCellY)) {
                    int level = getSudokuModel().getSolvedLevel(newCellX, newCellY);
                    Controller.getViewController().markAsSolved(newCellX, newCellY, level);
                }
            }
        }

        if (wasColumnSolved != isColumnSolved) {
            for (int index = 0; index < QUANTITY; index++) {
                if (!getSudokuModel().isCellGiven(index, y)) {
                    int level = getSudokuModel().getSolvedLevel(index, y);
                    Controller.getViewController().markAsSolved(index, y, level);
                }
            }
        }

        if (wasRowSolved != isRowSolved) {
            for (int index = 0; index < QUANTITY; index++) {
                if (!getSudokuModel().isCellGiven(x, index)) {
                    int level = getSudokuModel().getSolvedLevel(x, index);
                    Controller.getViewController().markAsSolved(x, index, level);
                }
            }
        }
    }

    private void markUsedNumbers() {
        List<Integer> solutionsTotal =  getSudokuModel().getSolutionsTotal();
        for (int index = 0; index < QUANTITY; index++) {
           if (solutionsTotal.get(index)==QUANTITY){
               Controller.getViewController().markNumberAsUsed(index);
           }
           else {
               if(solutionsTotal.get(index)>QUANTITY){
                   Controller.getViewController().markNumberAsOverUsed(index);
               }
               else {
                   Controller.getViewController().markNumberAsNormal(index);
               }
           }
        }
    }

    private void checkVictory() {
        if (getSudokuModel().checkVictory()) {
            Controller.getViewController().stopTimer(true);
            Controller.getStatisticController().onVictory();
            Controller.getViewController().showVictory();
        }
    }
}
