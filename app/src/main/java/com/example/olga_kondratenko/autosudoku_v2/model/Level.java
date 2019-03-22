package com.example.olga_kondratenko.autosudoku_v2.model;


public enum Level {
    EASY (15),
    MEDIUM(10),
    HARD(5),
    VERY_HARD(0);

    private final int hintQuantity;

    Level(int hintQuantity) {
        this.hintQuantity = hintQuantity;
    }

    public int getHintQuantity() {
        return hintQuantity;
    }
}
