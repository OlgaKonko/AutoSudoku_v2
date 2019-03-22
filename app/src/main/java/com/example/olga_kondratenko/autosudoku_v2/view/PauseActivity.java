package com.example.olga_kondratenko.autosudoku_v2.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.olga_kondratenko.autosudoku_v2.R;
import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;
import com.example.olga_kondratenko.autosudoku_v2.model.Statistic;

public class PauseActivity extends Activity {

    TextView statisic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);

        statisic = findViewById(R.id.statistic_fields);
        statisic.setText(getStatistic());
    }

    @SuppressLint("DefaultLocale")
    public String getStatistic(){
        String[] statisticMessages = getResources().getStringArray(R.array.statistic_fields);

        StringBuilder builder = new StringBuilder();

        builder.append(String.format("%s : %d \n", statisticMessages[0], Statistic.get().totalSolved));
        builder.append(String.format("%s : %d \n", statisticMessages[1], Statistic.get().totalGenerated));
        float percents;
        if(Statistic.get().totalSolved ==0){
            percents=0;
        }
        else
        percents =  ((float) Statistic.get().totalSolved/(float) Statistic.get().totalGenerated)*100;
        builder.append(String.format("%s : %,.2f%% \n", statisticMessages[2], percents));


        long time = Statistic.get().totalTimeSpend;
        long seconds = (time / 1000);
        long minutes = seconds / 60;
        long hours = minutes / 60;
        seconds = seconds % 60;
        hours = hours % 60;
        if (hours>0){
            builder.append(String.format("%s : %d h %02d m %02d s \n", statisticMessages[3], hours, minutes, seconds));
        }
        else {
            builder.append(String.format("%s : %d m %02d s \n", statisticMessages[3], minutes, seconds));
        }

        time = Statistic.get().minTimeSpend;
        if (time==0){
            builder.append(String.format("%s : - \n", statisticMessages[4]));
        }else {
            seconds = (time / 1000);
            minutes = seconds / 60;

            builder.append(String.format("%s : %d m %02d s \n", statisticMessages[4], minutes, seconds));
        }

        builder.append(String.format("%s : %d \n", statisticMessages[5], Statistic.get().totalHints));
        if (Statistic.get().hintsNow>0){
        builder.append(String.format("%s : %d \n", statisticMessages[6], Statistic.get().hintsNow));
        }
        return builder.toString();
    }

    public void closeStatistic(View view) {
        this.finish();
    }

    public void resetStatistic(View view) {
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.reset_title)
                .setMessage(R.string.reset_message)
                .setPositiveButton(R.string.reset_confirm, (dialog, id) -> {
                    Controller.getStatisticController().reset();
                    dialog.cancel();
                    statisic.setText(getStatistic());
                })
                .setNegativeButton(R.string.reset_decline,
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();

        alert.show();
    }
}
