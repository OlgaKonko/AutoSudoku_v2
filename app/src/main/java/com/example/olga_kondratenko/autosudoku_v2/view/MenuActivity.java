package com.example.olga_kondratenko.autosudoku_v2.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.olga_kondratenko.autosudoku_v2.R;
import com.example.olga_kondratenko.autosudoku_v2.controller.listeners.GameStartListener;

public class MenuActivity extends Activity {
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        startGameButton = findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(new GameStartListener());
    }



}
