package com.example.olga_kondratenko.autosudoku_v2.view.models;


import android.content.Context;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.olga_kondratenko.autosudoku_v2.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.INNER_BLOCK_SIZE;
import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.INNER_SELL_SIZE;

public class InnerCell extends TableLayout {
    List<Button> pensilCells;
    boolean showed;
    boolean created;
    OnClickListener listener;

    public InnerCell(Context context, OnClickListener listener) {
        super(context);
        showed = true;
        created = false;
        this.listener = listener;
    }

    public void create() {
        if (!created){
            created=true;
            pensilCells = new ArrayList<>(INNER_BLOCK_SIZE);
            TableRow row = new TableRow(this.getContext());
            for (int index = 0; index < INNER_BLOCK_SIZE; index++) {
                if (index % INNER_SELL_SIZE == 0) {
                    row = new TableRow(this.getContext());
                    createButton(row);
                    this.addView(row, new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
                } else {
                    createButton(row);
                }
            }
        }
    }

    private void createButton(TableRow row) {

        Button pencilCell = new Button(this.getContext());
        pensilCells.add(pencilCell);
        pencilCell.setBackgroundResource(R.drawable.light_border);
        pencilCell.setOnClickListener(listener);
        //button.setTextSize(number.getTextSize()/9 );
        // button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        row.addView(pencilCell, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        pencilCell.getLayoutParams().width = Sizes.gameFieldInnerCellSize;
        pencilCell.getLayoutParams().height = Sizes.gameFieldInnerCellSize;

        //  button.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    public void setDimensions(boolean needToBeEmpty) {
        android.view.ViewGroup.LayoutParams params = this.getLayoutParams();
        if (needToBeEmpty)
            params.width = Sizes.emptySize;
        else
            params.width = Sizes.gameFieldCellSize;
        params.height = Sizes.gameFieldCellSize;
        this.setLayoutParams(params);
    }

    public void markPencil(List<Boolean> pencilMarks){
        for (int index = 0; index < pensilCells.size(); index++) {
            if (pencilMarks.get(index)){
                markPencil(index);
            }
            else {
                removePencilMark(index);
            }
        }
    }

    public void markPencil(int number){
        pensilCells.get(number).setText(String.valueOf(number));
        pensilCells.get(number).setBackgroundResource(R.drawable.light_border_fill);
    }

    public void removePencilMark(int number){
        pensilCells.get(number).setText("");
        pensilCells.get(number).setBackgroundResource(R.drawable.light_border);
    }

}
