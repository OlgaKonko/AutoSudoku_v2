package com.example.olga_kondratenko.autosudoku_v2.controller.background;

import android.os.AsyncTask;

import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;
import com.example.olga_kondratenko.autosudoku_v2.model.Statistic;

public class SudokuCreateWaiterController extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
      Controller.getModuleSwitcher().showSpinner();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        GeneratedSudokuPull.waitUntilSudokuCreated();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Controller.getModuleSwitcher().hideSpinner();
        Controller.getFindController().setSudoku(GeneratedSudokuPull.getSudoku());
        Controller.getFindController().setFirstActionDone(false);
    }
}
