package com.example.olga_kondratenko.autosudoku_v2.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olga_kondratenko.autosudoku_v2.R;
import com.example.olga_kondratenko.autosudoku_v2.controller.listeners.ChoseInstrumentListener;
import com.example.olga_kondratenko.autosudoku_v2.controller.Controller;
import com.example.olga_kondratenko.autosudoku_v2.controller.listeners.HintListener;
import com.example.olga_kondratenko.autosudoku_v2.controller.listeners.OneMoreSudokuListener;
import com.example.olga_kondratenko.autosudoku_v2.model.Instrument;
import com.example.olga_kondratenko.autosudoku_v2.model.Statistic;
import com.example.olga_kondratenko.autosudoku_v2.view.models.Field;
import com.example.olga_kondratenko.autosudoku_v2.view.models.NumbersEnterField;
import com.example.olga_kondratenko.autosudoku_v2.view.models.Sizes;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.olga_kondratenko.autosudoku_v2.model.Instrument.PEN;
import static com.example.olga_kondratenko.autosudoku_v2.model.Instrument.PENCIL;

public class MainFieldActivity extends Activity implements ViewController{

    public static MainFieldActivity instance;
    public Field field;
    public NumbersEnterField numbersField;
    public HashMap<Instrument, ImageButton> instruments;
    public Button continueButton;
    public ProgressBar spinner;
    public boolean isVictory=false;

    public TextView timerTextView;

    Handler timerHandler = new Handler();
    Timer timerRunnable = new Timer();

    List<ParticleSystem> animations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_field);
        field = findViewById(R.id.sudoku_field);
        setTableSize();
        field.drawEmptyField();

        numbersField  = findViewById(R.id.numbersField);
        numbersField.draw();
        numbersField.hide();

        setInstruments();

        continueButton = findViewById(R.id.proceed);
        continueButton.setOnClickListener(new OneMoreSudokuListener());

        spinner = findViewById(R.id.spinner);
        hideSpinner();

        timerTextView =  findViewById(R.id.timer);
        setButtonsSize();

        setWinAnimation();

        instance = this;

        new Handler().post(new FieldFill());
    }

    @Override
    public void onResume()
    {super.onResume();
        instance = this;
        if (timerRunnable.isStarted){
            startTimer();
            timerRunnable.setPauseTime();
        }
    }

    @Override
    public void onPause() {
        stopTimer(false);
        timerRunnable.saveCurrentTime();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Controller.getController().saveSudoku();
        super.onDestroy();
    }

    private void setTableSize() {
        Point size = new Point();
        WindowManager w = getWindowManager();
        w.getDefaultDisplay().getSize(size);
        field.setSize(size.x, size.y);
    }

    private void setButtonsSize() {
        android.view.ViewGroup.LayoutParams params = timerTextView.getLayoutParams();
        params.width = Sizes.layoutWidth/3;
        timerTextView.setLayoutParams(params);
    }

    private void setInstruments() {
       instruments = new HashMap<>(2);
        ImageButton penButton = findViewById(R.id.pen);
        penButton.setOnClickListener(new ChoseInstrumentListener(PEN));
        ImageButton pencilButton = findViewById(R.id.pencil);
        pencilButton.setOnClickListener(new ChoseInstrumentListener(PENCIL));

        instruments.put(PEN, penButton);
        instruments.put(PENCIL, pencilButton);

    }

    private void setWinAnimation(){
        animations = new ArrayList<>(3);
        animations.add(new ParticleSystem(this, 80, R.drawable.animated_confetti1, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 190, 350)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90));

        animations.add(new ParticleSystem(this, 80, R.drawable.animated_confetti2, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 190, 350)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90));

        animations.add(new ParticleSystem(this, 80, R.drawable.animated_confetti3, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.3f, 190, 350)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90));
    }

    @Override
    public void startTimer() {
        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    public void stopTimer(boolean isFullyStop) {
        timerHandler.removeCallbacks(timerRunnable);
        timerRunnable.saveCurrentTime();
        if (isFullyStop){
            timerRunnable.isStarted = false;
        }
    }

    @Override
    public void resetTimer() {
        stopTimer(true);
        timerTextView.setText(String.format("%d:%02d", 0, 0));
        timerRunnable.resetTimer();
    }

    @Override
    public long getTimer() {
        return timerRunnable.getTime();
    }

    @Override
    public void setTime(long time) {
        timerRunnable.setTimer(time);

    }

    @Override
    public void removeFocuse() {
        field.removeFocus();
    }

    public void showSpinner(){
        spinner.setVisibility(View.VISIBLE);
        android.view.ViewGroup.LayoutParams params = spinner.getLayoutParams();
        params.width = Sizes.layoutWidth;
        params.height = Sizes.layoutWidth+2*Sizes.padding;
        spinner.setLayoutParams(params);
        field.setVisibility(View.INVISIBLE);

    }

    public void hideSpinner(){
        spinner.setVisibility(View.INVISIBLE);
        android.view.ViewGroup.LayoutParams params = spinner.getLayoutParams();
        params.width = Sizes.emptySize;
        params.height = Sizes.layoutWidth+2*Sizes.padding;
        spinner.setLayoutParams(params);
        field.setVisibility(View.VISIBLE);
    }

    @Override
    public void markCellFocused(int x, int y){
        field.removeFocus();
        field.setFocus(x, y);
    }

    @Override
    public void resetFocus() {
        field.focusedCell = null;
    }

    @Override
    public void setInstrument(Instrument instrument) {
        for (ImageButton button:instruments.values()
             ) {button.setBackgroundResource(R.drawable.no_fill);
        }

        instruments.get(instrument).setBackgroundResource(R.drawable.set_aura);

    }

    @Override
    public void markGivenValue(int x, int y, int number) {
        field.cells[x][y].markGiven(number);
    }


    @Override
    public void markHintValue(int x, int y, int number) {
        field.cells[x][y].markHint(number);
    }

    @Override
    public void markUsualValue(int x, int y) {
        field.cells[x][y].markUsual();
    }

    @Override
    public void showNumbersField(){
        numbersField.show();
    }

    @Override
    public void markNumberField() {
        numbersField.clear();
    }

    @Override
    public void markNumberField(List<Boolean> pencilMarks) {
        numbersField.mark(pencilMarks);
    }

    public void hideNumberField(){
        numbersField.hide();
    }

    @Override
    public void markNumberAsUsed(int index) {
        numbersField.markUsed(index);
    }

    @Override
    public void markNumberAsOverUsed(int index) {
        numbersField.markOverUsed(index);
    }

    @Override
    public void markNumberAsNormal(int index) {
numbersField.markNormal(index);
    }

    @Override
    public void markPenNumber(int x, int y, int number){
        field.cells[x][y].markPen(number);
    }

    @Override
    public void  markEmptyField(int x, int y){
        field.cells[x][y].markEmpty();
    }

    @Override
    public void displayPencilMarkFields(int x, int y, List<Boolean> pencilMarks) {
        field.cells[x][y].markPencil(pencilMarks);
    }

    @Override
    public void markPencil(int x, int y, int number) {
        field.cells[x][y].markPencil(number);
    }

    @Override
    public void removePencilMark(int x, int y, int number) {
        field.cells[x][y].removePencilMark(number);
    }

    @Override
    public void markAsSolved(int x, int y, int level) {
        field.cells[x][y].markSolved(level);
    }

    @Override
    public void showVictory() {
        isVictory=true;
        Random random = new Random();
        String[] victoryMessages = getResources().getStringArray(R.array.victory_congratulations);
        StringBuilder builder = new StringBuilder();
        builder.append(victoryMessages[random.nextInt(victoryMessages.length)]);
        builder.append("\n");

         String[] actionMessages = getResources().getStringArray(R.array.victory_messages);
        builder.append(actionMessages[random.nextInt(actionMessages.length)]);

        if (Statistic.get().minTimeSpend> getTimer()) {
            builder.append("\n");
            String[] recordMessages = getResources().getStringArray(R.array.record_victory_congratulations);
            builder.append(recordMessages[random.nextInt(recordMessages.length)]);
        }

       // String[] buttonMessages = getResources().getStringArray(R.array.assept_buttons);
      //  String buttonMessage = buttonMessages[random.nextInt(buttonMessages.length)];

        setWinAnimation();
        for(int index = 0; index<animations.size(); index++){
            int x = Sizes.layoutWidth/(animations.size()+1);

            animations.get(index).emit(x*index, 0,20);
    }

        Toast toast = Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG);
        toast.show();
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")
                .setMessage(victoryMessage)
                .setCancelable(false)
                .setNegativeButton(buttonMessage,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();

        alert.show();*/
    }

    @Override
    public void hideVictory() {
        isVictory=false;
        for (ParticleSystem animation:animations
                ) {
            animation.cancel();
        }
    }

    public void openStatistic(View view) {
        stopTimer(false);
        Intent intent = new Intent(this, PauseActivity.class);
        this.startActivity(intent);
    }

    public void hintConfirmationShown(View view) {
        if (!isVictory) {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

            builder.setMessage(R.string.hint_message)
                    .setPositiveButton(R.string.reset_confirm, new HintListener())
                    .setNegativeButton(R.string.reset_decline,
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();

            alert.show();
        }
        else {
            Toast toast = Toast.makeText(this, R.string.no_hint_message, Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
