package com.example.olga_kondratenko.autosudoku_v2.view.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.olga_kondratenko.autosudoku_v2.R;
import com.example.olga_kondratenko.autosudoku_v2.controller.listeners.CellClickListener;

import java.util.List;

@SuppressLint("AppCompatCustomView")
public class Cell extends TableLayout {
    public int x;
    public int y;
    private Button penCell;
    private InnerCell pencilCells;
    private TableRow row;
    public CellClickListener listener;
    private int currentSolvedLevel;

    public boolean isPenView;


    public Cell(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
        listener = new CellClickListener(x, y);
        setOnClickListener(listener);
        setImageResource(R.drawable.shape);
    }

    public void setImageResource(int resId) {
        Drawable drawable = ContextCompat.getDrawable(this.getContext(), resId);
        this.setBackground(drawable);

    }


    public void completeCell() {
        this.getLayoutParams().width = Sizes.gameFieldCellSize;
        this.getLayoutParams().height =Sizes.gameFieldCellSize;


        penCell = new Button(this.getContext());
        penCell.setOnClickListener(listener);
        //penCell.setOnClickListener(listener);
       // penCell.setTextSize(TypedValue.COMPLEX_UNIT_DIP, penCell.getTextSize()*0.7f );
      //  textSize = penCell.getTextSize() / 12;
        // this.addView(penCell, new LayoutParams(LayoutParams.MATCH_PARENT,
        //         LayoutParams.MATCH_PARENT));
        row = new TableRow(this.getContext());
        row.addView(penCell, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        pencilCells = new InnerCell(this.getContext(), listener);
        row.addView(pencilCells, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));


        setDimensions(penCell, false);
        //penCell.setTextSize(penCell.getTextSize()*2);
        //penCell.getLayoutParams().width = gameFieldCellSize;
        penCell.setPaddingRelative(0, 0, 0, 0);
        //penCell.getLayoutParams().height = gameFieldCellSize;
        penCell.setBackgroundResource(R.drawable.no_fill);
        this.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        isPenView = true;
        currentSolvedLevel =0;
    }

    private void setDimensions(Button penCell, boolean needToBeEmpty){
        android.view.ViewGroup.LayoutParams params = penCell.getLayoutParams();
        if (needToBeEmpty)
            params.width = Sizes.emptySize;
        else
            params.width = Sizes.gameFieldCellSize;
        params.height = Sizes.gameFieldCellSize;
        penCell.setLayoutParams(params);
    }

    public void markFocused(){
        this.setImageResource(R.drawable.glow_cell);
    }

    public void markSolved(int level){
        currentSolvedLevel = level;
        markByLevel();
    }

    public void markByLevel(){
       switch (currentSolvedLevel){
           case 0: {
               this.setImageResource(R.drawable.shape);
               break;
           }
           case 1: {
               this.setImageResource(R.drawable.solved_1);
               break;
           }
           case 2: {
               this.setImageResource(R.drawable.solved_2);
               break;
           }
           default:{
                   this.setImageResource(R.drawable.solved_3);
           }
       }
    }

    public void markUnFocused(){
       markByLevel();
    }

    public void markGiven(int number){
        setPenView();
        this.penCell.setText(String.valueOf(number));
        this.setImageResource(R.drawable.given_cell);
    }

    public void markUsual(){
        setPenView();
        this.setImageResource(R.drawable.shape);
    }

    public void markPen(int number){
        setPenView();
       this.penCell.setText(String.valueOf(number));
    }

    public void markEmpty(){
        setPenView();
        this.penCell.setText("");
    }

    public void markPencil(int number){
        setPencilView();
        pencilCells.markPencil(number);
    }
    public void markPencil(List<Boolean> pencilMarks){
        setPencilView();
        pencilCells.markPencil(pencilMarks);
    }

    public void removePencilMark(int number){
        setPencilView();
        pencilCells.removePencilMark(number);
    }


    public void setPenView(){
        if (!isPenView) {
            isPenView = true;
            setDimensions(penCell, false);
            if (pencilCells.created) {
                pencilCells.setDimensions(true);
            }
        }
    }
    public void setPencilView(){
        if (isPenView) {
            isPenView = false;
            setDimensions(penCell, true);
            if (pencilCells.created) {
                pencilCells.setDimensions(false);
            }
            else
                pencilCells.create();
        }
    }

   /* public PencilNumbers cells;
    private Button penCell;
    TableRow row;
    public int x;
    public int y;
    public boolean sellValueIsGiven = false;

    private boolean solved;

   // private boolean gridInited = false;
    public boolean gridShowed = false;

    MoveListener listener;
    float textSize;

    public Cell(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
    }

    public void refresh() {
        setSolutions(currentSudoku.field[x][y], false);
    }


    public void setSolutions(PossibleSolutions possibleSolutions, boolean start) {
        if (start){
            markPen(possibleSolutions.solution, start);
        }
        else {
        if (possibleSolutions.solved) {
            System.out.println("!!!! try to mark solution pen "+possibleSolutions.solution);
            markPen(possibleSolutions.solution, start);
        } else {
            if (!usePen) {
                System.out.println("!!!! try to mark pensil");
                markPensil(possibleSolutions);
            } else {
                System.out.println("!!!! try to mark pen"+possibleSolutions.solution);
                markPen(possibleSolutions.solution, start);
            }
        }}
    }

    public void updateBackGround () {
        if (!sellValueIsGiven) {
            int status = currentSudoku.status[x][y];
            if (status == 0)
                this.setBackgroundResource(R.drawable.shape);
            if (status == 1)
                this.setBackgroundResource(R.drawable.light_background_shape);
            if (status == 2)
                this.setBackgroundResource(R.drawable.middle_background_shape);
            if (status == 3)
                this.setBackgroundResource(R.drawable.dark_background_shape);
        }
    }

    public void markPen(int solution, boolean start) {
        System.out.println("!!!! mark pen");
        changeView(false);
        //this.start = start;

        if (solution == 0) {
            penCell.setText("");
        } else {
            penCell.setText(String.valueOf(solution));
            if (start) {
                sellValueIsGiven = true;
                penCell.setBackgroundResource(R.color.given_color);
            }
        }
    }

    public void markPensil(PossibleSolutions possibleSolutions) {
        System.out.println("!!!! mark pensil");
        cells.initGrid();
        changeView(true);

        for (int index = 0; index < INNER_BLOCK_SIZE; index++) {
            if (possibleSolutions.solutions[index] == 1) {
                System.out.println("!!!! mark pensil sell "+index);
                cells.setText(index, String.valueOf(index + 1));
               cells.setBackground(index, R.drawable.selected_number);
            } else {
                cells.setText(index, "!");
                cells.setBackground(index, R.drawable.gray_shape);
            }
        }
    }

    public void markSolved() {
                sellValueIsGiven = true;
                penCell.setBackgroundResource(R.color.solved_color);


    }

    private void changeView(boolean showGrid) {
        if (showGrid && !gridShowed){
            System.out.println("!!!! change view to table");
           //penCell.getLayoutParams().width = empty_size;
           setDimensions(penCell, true);
            gridShowed = true;
        }
        else
            if (!showGrid && gridShowed){
                System.out.println("!!!! change view to button");
                //penCell.getLayoutParams().width = gameFieldCellSize;
                setDimensions(penCell, false);
                gridShowed = false;
            }

     /*   if (showGrid && !gridShowed) {
            gridShowed = true;
            penCell.getLayoutParams().height = empty_size;

            for (int index = 0; index < INNER_BLOCK_SIZE; index++) {
                cells[index].getLayoutParams().height = gameFieldInnerCellSize;
            }
        } else {
            if (!showGrid && gridShowed) {
                gridShowed = false;
                penCell.getLayoutParams().height = gameFieldCellSize;
                for (int index = 0; index < INNER_BLOCK_SIZE; index++) {
                    cells[index].getLayoutParams().height = empty_size;
                }
            }

        }*/
  /*  }
    private void setDimensions(Button penCell, boolean isEmpty){
        android.view.ViewGroup.LayoutParams params = penCell.getLayoutParams();
        if (isEmpty)
        params.width = empty_size;
        else
            params.width = game_field_sell_size;
        params.height =  game_field_sell_size;
        penCell.setLayoutParams(params);
    }

    public void setOnClickListener(MoveListener listener) {
        this.listener = listener;
    }

    public void completeCell() {
        this.getLayoutParams().width = game_field_sell_size;
        this.getLayoutParams().height = game_field_sell_size;
        game_field_inner_sell_size = game_field_sell_size / INNER_SELL_SIZE;
        penCell = new Button(this.getContext());
        penCell.setOnClickListener(listener);
          penCell.setTextSize(TypedValue.COMPLEX_UNIT_DIP,penCell.getTextSize()*0.7f );
        textSize = penCell.getTextSize() / 12;
       // this.addView(penCell, new LayoutParams(LayoutParams.MATCH_PARENT,
       //         LayoutParams.MATCH_PARENT));
        row = new TableRow(this.getContext());
        row.addView(penCell, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        cells = new PencilNumbers(this.getContext(), listener);
        row.addView(cells, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));


        setDimensions(penCell, false);
        //penCell.setTextSize(penCell.getTextSize()*2);
        //penCell.getLayoutParams().width = gameFieldCellSize;
        penCell.setPaddingRelative(0, 0, 0, 0);
       //penCell.getLayoutParams().height = gameFieldCellSize;
        penCell.setBackgroundResource(R.drawable.no_fill);
        this.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void setImageResource(int resId) {
        Drawable drawable = ContextCompat.getDrawable(this.getContext(), resId);
        this.setBackground(drawable);

    }*/


}
