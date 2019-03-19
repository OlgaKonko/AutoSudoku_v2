package com.example.olga_kondratenko.autosudoku_v2.controller;


import com.example.olga_kondratenko.autosudoku_v2.model.Instrument;

public class InstrumentActionsController {

    private Instrument instrument;

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        if (this.instrument != instrument) {
            this.instrument = instrument;
            Controller.getViewController().setInstrument(instrument);
        }
    }

    public void initInstrument(Instrument instrument) {
            this.instrument = instrument;
    }
}
