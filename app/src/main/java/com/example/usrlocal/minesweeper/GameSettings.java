package com.example.usrlocal.minesweeper;

public enum GameSettings {

  EASY(9, 9, 2),
  MEDIUM(12, 16, 2),
  HARD(16, 22, 2);

  private int cols, rows, mines;

  GameSettings(int cols, int rows, int mines) {
    this.cols = cols;
    this.rows = rows;
    this.mines = mines;
  }

  public int getCols() {
    return cols;
  }

  public int getRows() {
    return rows;
  }

  public int getMines() {
    return mines;
  }
}
