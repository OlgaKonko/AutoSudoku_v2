package com.example.olga_kondratenko.autosudoku_v2.view;


import com.example.olga_kondratenko.autosudoku_v2.model.Instrument;

import java.util.List;

public interface ViewController {
    void setInstrument(Instrument instrument);
    void markGivenValue(int x, int y, int number);
    void markUsualValue(int x, int y);

    void showNumbersField();
    void hideNumberField();
    void markNumberField();
    void markNumberField(List<Boolean> pencilMarks);
    void markCellFocused(int x, int y);

    void markPenNumber(int x, int y, int number);
    void markEmptyField(int x, int y);

    void displayPencilMarkFields(int x, int y, List<Boolean> pencilMarks);
    void markPencil(int x, int y, int number);
    void removePencilMark(int x, int y, int number);

    void markAsSolved(int x, int y, int level);
    void showVictory();

    void markNumberAsUsed(int index);
    void markNumberAsOverUsed(int index);
    void markNumberAsNormal(int index);

    void startTimer();
    void stopTimer();
    void resetTimer();

    long getTimer();
    void setTime(long time);
}
