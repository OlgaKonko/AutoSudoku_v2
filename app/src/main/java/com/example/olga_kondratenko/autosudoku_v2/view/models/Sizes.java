package com.example.olga_kondratenko.autosudoku_v2.view.models;


import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.FIELD_SIZE;
import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.INNER_SELL_SIZE;
import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.LINES_SIZE;
import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.LINE_SCALE;
import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.NUMBERS_SCALE;

public class Sizes {

    public static int layoutWidth = 1080;
    public static int layoutHeight = 1920;

    public static int padding;

    public static int gameFieldSize;
    public static int gameFieldLinesSize;

    public static int gameFieldCellSize;
    public static int gameFieldInnerCellSize;

    public static int numbersFieldSize;
    public static int numbersCellSize;

    public static int emptySize = 0;

    public static void initSizes(int layoutSetedWidth, int layoutSetedHeight, int setedPadding) {
        layoutWidth  = layoutSetedWidth;
        layoutHeight = layoutSetedHeight;

        padding = setedPadding;

        gameFieldSize = layoutWidth - (padding*2);
        gameFieldLinesSize = gameFieldSize / LINE_SCALE;
        gameFieldCellSize = (gameFieldSize - (gameFieldLinesSize * LINES_SIZE)) / FIELD_SIZE;
        gameFieldInnerCellSize = gameFieldCellSize/ INNER_SELL_SIZE;

        numbersFieldSize = layoutWidth / NUMBERS_SCALE;
        numbersCellSize = numbersFieldSize / INNER_SELL_SIZE;
    }
}
