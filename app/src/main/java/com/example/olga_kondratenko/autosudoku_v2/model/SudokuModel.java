package com.example.olga_kondratenko.autosudoku_v2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.BLOCK_SIZE;
import static com.example.olga_kondratenko.autosudoku_v2.utils.Constants.QUANTITY;

public class SudokuModel implements Serializable{
    private List<List<SudokuCellModel>> cells;
    private List<Integer> solutionsTotal;

    public SudokuModel() {
        solutionsTotal= new ArrayList<>(QUANTITY);
        cells = new ArrayList<>(QUANTITY);
        for (int rowIndex= 0; rowIndex<QUANTITY; rowIndex++){
            List<SudokuCellModel> row = new ArrayList<>(QUANTITY);
            for (int columnIndex= 0; columnIndex<QUANTITY; columnIndex++){
                row.add(new SudokuCellModel());
            }
            cells.add(row);
            solutionsTotal.add(0);
        }
    }

    public int getCellSolution(int x, int y){
        return cells.get(x).get(y).solution;
    }

    public void setCellSolution(int x, int y, int solution, boolean given){
        SudokuCellModel cell = cells.get(x).get(y);
        if(cell.solution>0){
            int index =cell.solution-1;
            solutionsTotal.set(index, solutionsTotal.get(index)-1);
        }
        if(solution>0) {
            int index = solution - 1;
            solutionsTotal.set(index, solutionsTotal.get(index)+1);
        }
        cell.solution = solution;
        cell.given = given;


    }

    public boolean isCellGiven(int x, int y){
        return cells.get(x).get(y).given;
    }

    public boolean isPencilMarkPresent(int x, int y, int number){
        return cells.get(x).get(y).pencilMarks.get(number);
    }

    public int getPencilMarksNumber(int x, int y){
        return cells.get(x).get(y).getPenMarksNumber();
    }

    public List<Boolean> getPencilMarks(int x, int y){
        return cells.get(x).get(y).pencilMarks;
    }

    public void setPencilNumber(int x, int y, int number, boolean mark){
        cells.get(x).get(y).pencilMarks.set(number, mark);
    }

    public int getSolvedLevel(int x, int y){
        return cells.get(x).get(y).getSolvedLevel();
    }

    public List<Integer> getSolutionsTotal(){
        return solutionsTotal;
    }

    public boolean checkCellBlockSolved(int x, int y){
        return cells.get(x).get(y).isBlockSolved;
    }

    public boolean checkCellRowSolved(int x, int y){
        return cells.get(x).get(y).isRowSolved;
    }

    public boolean checkCellColumnSolved(int x, int y){
        return cells.get(x).get(y).isColumnSolved;
    }

    public boolean checkCellFillBlock(int x, int y){
        boolean isSolved = true;
        int startX = (x- (x%BLOCK_SIZE));
        int startY = (y- (y%BLOCK_SIZE));

        Set<Integer> values = new HashSet<>(QUANTITY);
        for (int index= 0; index<QUANTITY; index++){
            int newCellX = (startX+ index/BLOCK_SIZE);
            int newCellY = (startY+index%BLOCK_SIZE);
            if (cells.get(newCellX).get(newCellY).solution ==0 || values.contains(cells.get(newCellX).get(newCellY).solution)){
                isSolved= false;
                break;
            }
            else {
                values.add(cells.get(newCellX).get(newCellY).solution);
            }

        }

        for (int index= 0; index<QUANTITY; index++){
            int newCellX = (startX+ index/BLOCK_SIZE);
            int newCellY = (startY+index%BLOCK_SIZE);
            cells.get(newCellX).get(newCellY).isBlockSolved = isSolved;
        }
        return isSolved;
    }

    public boolean checkCellFillRow(int x, int y){
        boolean isSolved = true;
        Set<Integer> values = new HashSet<>(QUANTITY);
        for (int index= 0; index<QUANTITY; index++){
            if (cells.get(x).get(index).solution ==0 || values.contains(cells.get(x).get(index).solution)){
                isSolved = false;
                break;
            }
            else {
                values.add(cells.get(x).get(index).solution);
            }
        }

        for (int index= 0; index<QUANTITY; index++){
            cells.get(x).get(index).isRowSolved = isSolved;
        }

        return isSolved;
    }

    public boolean checkCellFillColumn(int x, int y){
        boolean isSolved = true;
        Set<Integer> values = new HashSet<>(QUANTITY);
        for (int index= 0; index<QUANTITY; index++){
            if (cells.get(index).get(y).solution ==0 || values.contains(cells.get(index).get(y).solution)){
                isSolved = false;
                break;
            }
            else {
                values.add(cells.get(index).get(y).solution);
            }
        }
        for (int index= 0; index<QUANTITY; index++){
            cells.get(index).get(y).isColumnSolved = isSolved;


        }
        return isSolved;
    }

    public boolean checkVictory(){
        for (int rowIndex= 0; rowIndex<QUANTITY; rowIndex++){
            for (int columnIndex= 0; columnIndex<QUANTITY; columnIndex++){
              if (cells.get(rowIndex).get(columnIndex).getSolvedLevel() != 3){
                  return false;
              }
            }
            }

        return true;
    }

}
