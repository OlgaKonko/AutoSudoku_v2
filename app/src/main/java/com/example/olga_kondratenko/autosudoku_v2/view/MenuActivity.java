package com.example.olga_kondratenko.autosudoku_v2.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.olga_kondratenko.autosudoku_v2.R;
import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;
import com.example.olga_kondratenko.autosudoku_v2.controller.listeners.GameStartListener;
import com.example.olga_kondratenko.autosudoku_v2.model.Level;
import com.example.olga_kondratenko.autosudoku_v2.model.Options;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends Activity {
    private Button startGameButton;
    private Map<RadioButton, Level> levelButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        startGameButton = findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(new GameStartListener());
setLevelButtons();

    }
    public void setLevelButtons() {
        levelButtons = new HashMap<>(4);
        levelButtons.put(findViewById(R.id.easy_level_button), Level.EASY);
        levelButtons.put(findViewById(R.id.medium_level_button), Level.MEDIUM);
        levelButtons.put(findViewById(R.id.hard_level_button),Level.HARD);
        levelButtons.put(findViewById(R.id.very_hard_level_button), Level.VERY_HARD);
        Level level = Options.get().level;
        for (RadioButton button : levelButtons.keySet()
                ) {
            if (levelButtons.get(button) == level)
                button.setChecked(true);
        }
    }


    public void openStatistic(View view) {
        Intent intent = new Intent(this, PauseActivity.class);
        this.startActivity(intent);
    }


    public void chooseLevel(View view) {
        RadioButton currentButton = (RadioButton) view;
        //if(!currentButton.isChecked()) {
            for (RadioButton button : levelButtons.keySet()
                    ) {
                button.setChecked(false);
            }
            currentButton.setChecked(true);
        Options.get().level = levelButtons.get(currentButton);
       // }
    }
}
