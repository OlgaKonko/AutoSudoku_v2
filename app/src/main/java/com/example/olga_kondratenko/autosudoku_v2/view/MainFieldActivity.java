package com.example.olga_kondratenko.autosudoku_v2.view;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
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
import com.example.olga_kondratenko.autosudoku_v2.controller.listeners.OneMoreSudokuListener;
import com.example.olga_kondratenko.autosudoku_v2.model.Instrument;
import com.example.olga_kondratenko.autosudoku_v2.view.models.Field;
import com.example.olga_kondratenko.autosudoku_v2.view.models.NumbersEnterField;
import com.example.olga_kondratenko.autosudoku_v2.view.models.Sizes;

import java.util.HashMap;
import java.util.List;

import static com.example.olga_kondratenko.autosudoku_v2.model.Instrument.PEN;
import static com.example.olga_kondratenko.autosudoku_v2.model.Instrument.PENCIL;

public class MainFieldActivity extends Activity implements ViewController{

    public static MainFieldActivity instance;
    public Field field;
    public NumbersEnterField numbersField;
    public HashMap<Instrument, ImageButton> instruments;
    public Button continueButton;
    public ProgressBar spinner;
    public TextView unUsedNumbers;

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

        unUsedNumbers =  findViewById(R.id.unUsedNumbersView);
        setButtonsSize();

        instance = this;

        Controller.getController().fieldDrowActions();

    }

    @Override
    public void onResume()
    {super.onResume();
        instance = this;
    }

    private void setTableSize() {
        Point size = new Point();
        WindowManager w = getWindowManager();
        w.getDefaultDisplay().getSize(size);
        field.setSize(size.x, size.y);
    }

    private void setButtonsSize() {
        android.view.ViewGroup.LayoutParams params = unUsedNumbers.getLayoutParams();
        params.width = Sizes.layoutWidth/3;
       unUsedNumbers.setLayoutParams(params);
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
        Toast toast = Toast.makeText(this, "Congratulations!", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showUnUsedNumbers(String s) {
        unUsedNumbers.setText(s);
    }

}
