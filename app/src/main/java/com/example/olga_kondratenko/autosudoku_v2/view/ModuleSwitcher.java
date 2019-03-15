package com.example.olga_kondratenko.autosudoku_v2.view;


import android.content.Context;
import android.content.Intent;

import com.example.olga_kondratenko.autosudoku_v2.model.SudokuModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ModuleSwitcher {

    Context packageContext;
    String savedSudokuFileName ="Saved sudoku 2";

    public ModuleSwitcher(Context packageContext) {
        this.packageContext = packageContext;
    }

    public void showMenu(){

        Intent intent = new Intent(packageContext, MenuActivity.class);
        packageContext.startActivity(intent);

    }

    public void showGame() {
        Intent intent = new Intent(packageContext, MainFieldActivity.class);
        packageContext.startActivity(intent);
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
}
