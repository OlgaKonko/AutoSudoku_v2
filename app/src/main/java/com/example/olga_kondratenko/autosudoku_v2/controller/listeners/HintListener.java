package com.example.olga_kondratenko.autosudoku_v2.controller.listeners;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;

public class HintListener implements OnClickListener {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        Controller.getFindController().markHint();
        Controller.getStatisticController().onHint();
    }
}
