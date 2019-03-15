package com.example.olga_kondratenko.autosudoku_v2.view.models;


import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.olga_kondratenko.autosudoku_v2.R;
import com.example.olga_kondratenko.autosudoku_v2.controller.listeners.ChoseNumberListener;

import java.util.List;

import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.INNER_SELL_SIZE;

public class NumbersEnterField extends TableLayout {
    public Button[][] numbers;

    public NumbersEnterField(Context context) {
        super(context);
    }

    public NumbersEnterField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void draw() {
        numbers = new Button[INNER_SELL_SIZE][INNER_SELL_SIZE];

        TableRow row;
        for (int x = 0; x < INNER_SELL_SIZE; x++) {
            row = new TableRow(this.getContext());
            for (int y = 0; y < INNER_SELL_SIZE; y++) {
                createButton(x, y, row);
            }
            this.addView(row, new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    public void clear() {
        for (int x = 0; x < INNER_SELL_SIZE; x++) {
            for (int y = 0; y < INNER_SELL_SIZE; y++) {
                numbers[x][y].setBackgroundResource(R.drawable.shape);
            }
        }
    }

    public void mark(List<Boolean> marks) {
        for (int x = 0; x < INNER_SELL_SIZE; x++) {
            for (int y = 0; y < INNER_SELL_SIZE; y++) {
                if (marks.get(x*INNER_SELL_SIZE+y)){
                numbers[x][y].setBackgroundResource(R.drawable.glow_cell);}
                else {
                    numbers[x][y].setBackgroundResource(R.drawable.shape);
                }
            }
        }
    }

    public void show() {
      this.setVisibility(VISIBLE);
    }

    public void hide() {
        this.setVisibility(INVISIBLE);
    }

    private void createButton(int x, int y, TableRow row) {
        Button number = new Button(this.getContext());
        numbers[x][y] = number;
        number.setTextSize(TypedValue.COMPLEX_UNIT_DIP,number.getTextSize()*0.4f );
        number.setBackgroundResource(R.drawable.shape);
        number.setOnClickListener(new ChoseNumberListener(x * INNER_SELL_SIZE + y));
        row.addView(number, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        number.getLayoutParams().width = Sizes.numbersCellSize;
        number.getLayoutParams().height = Sizes.numbersCellSize;
        number.setText(String.valueOf(x * INNER_SELL_SIZE + y + 1));
    }


}
