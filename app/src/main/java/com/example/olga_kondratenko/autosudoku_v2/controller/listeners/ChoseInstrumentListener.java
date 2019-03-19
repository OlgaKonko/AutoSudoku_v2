package com.example.olga_kondratenko.autosudoku_v2.controller.listeners;


import android.view.View;

import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;
import com.example.olga_kondratenko.autosudoku_v2.model.Instrument;

public class ChoseInstrumentListener implements View.OnClickListener {

    Instrument instrument;

    public ChoseInstrumentListener(Instrument instrument) {
        this.instrument = instrument;
    }

    @Override
    public void onClick(View view) {
        Controller.getInstrumentController().setInstrument(instrument);
    }
}
