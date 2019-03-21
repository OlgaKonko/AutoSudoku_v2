package com.example.olga_kondratenko.autosudoku_v2.view;


import android.content.Context;

import com.example.olga_kondratenko.autosudoku_v2.model.Statistic;
import com.example.olga_kondratenko.autosudoku_v2.model.SudokuModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {
    Context packageContext;
    String savedSudokuFileName ="Saved sudoku";
    String statisticFileName ="Statistic";
    String timerFileName ="Timer";

    public FileManager(Context packageContext) {
        this.packageContext = packageContext;
    }

    public SudokuModel loadSudoku() throws IOException, ClassNotFoundException {
        FileInputStream fis = packageContext.openFileInput(savedSudokuFileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        SudokuModel simpleClass = (SudokuModel) is.readObject();
        is.close();
        fis.close();
        return simpleClass;
    }

    public void saveSudoku(SudokuModel sudokuModel) throws IOException, ClassNotFoundException {
        FileOutputStream fos = packageContext.openFileOutput(savedSudokuFileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(sudokuModel);
        os.close();
        fos.close();
    }

    public long loadTimer() throws IOException, ClassNotFoundException {
        FileInputStream fis = packageContext.openFileInput(timerFileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        long time = (long) is.readObject();
        is.close();
        fis.close();
        return time;
    }

    public void saveTimer(long time) throws IOException, ClassNotFoundException {
        FileOutputStream fos = packageContext.openFileOutput(timerFileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(time);
        os.close();
        fos.close();
    }

    public void loadStatistic() throws IOException, ClassNotFoundException {
        FileInputStream fis = packageContext.openFileInput(statisticFileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Statistic simpleClass = (Statistic) is.readObject();
        is.close();
        fis.close();
        Statistic.set(simpleClass);
    }

    public void saveStatistic() throws IOException, ClassNotFoundException {
        FileOutputStream fos = packageContext.openFileOutput(statisticFileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(Statistic.get());
        os.close();
        fos.close();
    }
}
