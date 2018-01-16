package com.example.usrlocal.minesweeper;

public enum GameSettings {

  EASY(9, 9, 10, 50),
  MEDIUM(12, 16, 30, 100),
  HARD(16, 22, 60, 200);

  private int cols;
  private int rows;
  private int mines;
  private int time;

  GameSettings(int cols, int rows, int mines, int time) {
    this.cols = cols;
    this.rows = rows;
    this.mines = mines;
    this.time = time;
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

  public int getTime() {return time;}

}
