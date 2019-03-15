package com.example.olga_kondratenko.autosudoku_v2.view.models;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.olga_kondratenko.autosudoku_v2.R;

import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.*;

public class Field extends TableLayout {

    public Cell[][] cells;
    public Cell focusedCell;

    public Field(Context context) {
        super(context);
    }

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSize (int layout_width, int layout_height){

        Sizes.initSizes(layout_width, layout_height, this.getPaddingLeft());
    }


  //  public Field(Context context, AttributeSet attrs) {
 //       super(context, attrs);
  //  }

    public void drawEmptyField() {
        cells = new Cell[FIELD_SIZE][FIELD_SIZE];

        TableRow row;
        for (int x = 0; x < FIELD_SIZE; x++) {
            if (x % BLOCK_SIZE == 0) {
                createHorizontalLine();
            }
            row = new TableRow(this.getContext());
            for (int y = 0; y < FIELD_SIZE; y++) {
                createButton(x, y, row);
            }
            createLine(row, Sizes.gameFieldLinesSize, Sizes.gameFieldCellSize);
            this.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
        createHorizontalLine();
    }

    public void removeFocus(){
        if (focusedCell!=null){
        focusedCell.markUnFocused();}
    }

    public void setFocus(int x, int y){
        focusedCell = cells[x][y];
        focusedCell.markFocused();
    }

    /*

    public void fillField() {
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                cells[x][y].setSolutions(currentSudoku.field[x][y], true);
            }

        }
    }

    public void updateSellsBackground(){
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                cells[x][y].updateBackGround();
            }

        }
    }

    public void fillSolvedField() {
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                cells[x][y].markSolved();
            }

        }
    }

*/
    private void createButton(int x, int y, TableRow row) {
        if (y % BLOCK_SIZE == 0) {
            createLine(row, Sizes.gameFieldLinesSize, Sizes.gameFieldCellSize);
        }
        Cell cell = new Cell(this.getContext(), x, y);
        cells[x][y] = cell;
        //button.setOnClickListener(new MoveListener(x, y, button));
        row.addView(cell, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        cell.completeCell();
    }

    private void createLine(TableRow row, int width, int height) {
        ImageView button = new ImageView(this.getContext());
        button.setImageResource(R.drawable.fill_cell);
        row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        button.getLayoutParams().width = width;
        button.getLayoutParams().height = height;

        button.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private void createHorizontalLine() {
        TableRow row = new TableRow(this.getContext());
        for (int y = 0; y < FIELD_SIZE; y++) {
            if (y % BLOCK_SIZE == 0) {
                createLine(row, Sizes.gameFieldLinesSize, Sizes.gameFieldLinesSize);
            }
            createLine(row, Sizes.gameFieldCellSize, Sizes.gameFieldLinesSize);
        }
        createLine(row, Sizes.gameFieldLinesSize, Sizes.gameFieldLinesSize);
        this.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }
}
