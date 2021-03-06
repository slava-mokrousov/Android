package minesweeper.mokr.slava.minesweeper.gameLogic;

import java.io.Serializable;

public class Cell implements Serializable {

    private int value;
    private boolean bomb;
    private boolean isClicked;
    private boolean isFlagged;

    public Cell() {
        value = 0;
        bomb = false;
        isClicked = false;
        isFlagged = false;
    }

    public void setBomb() {
        bomb = true;
    }

    public boolean isBomb() {return bomb; }

    public void incValue() {value++; }

    public int getValue() {return value; }

    public boolean isClicked() {return isClicked; }

    public boolean isFlagged() {return isFlagged; }

    public void cellClicked() {isClicked = true; }

    public void cellFlagged() {isFlagged = !isFlagged; }

    public void flagClear() {isFlagged = false; }

    public void clearValue() {
        if (!isBomb()) {value = 0;}
    }
}