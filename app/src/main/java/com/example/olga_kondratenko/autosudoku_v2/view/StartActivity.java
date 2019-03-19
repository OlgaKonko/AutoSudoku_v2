package com.example.olga_kondratenko.autosudoku_v2.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      Controller.getController().setModuleController(new ModuleSwitcher(this));
      Controller.getChangeController().start();
      finish();
    }
}
