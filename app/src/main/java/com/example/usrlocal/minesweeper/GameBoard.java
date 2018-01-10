package com.example.usrlocal.minesweeper;

import java.util.concurrent.ThreadLocalRandom;

public class GameBoard {

  public static final int MINE = 9;
  private int cols, rows, mines, board[][];

  public GameBoard(GameSettings settings) {
    this.cols = settings.getCols();
    this.rows = settings.getRows();
    this.mines = settings.getMines();

    board = new int[cols][rows]; // zeros

    fillMines();
    fillNumbers();
  }

  private void fillMines() {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int mines = this.mines;

    while (mines > 0) {
      int col = random.nextInt(0, cols);
      int row = random.nextInt(0, rows);

      if (board[col][row] != MINE) {
        board[col][row] = MINE;
        mines -= 1;
      }
    }
  }

  public void fillNumbers() {
    for (int col = 0; col < cols; col++) {
      for (int row = 0; row < rows; row++) {

        if (board[col][row] == MINE) {
          continue;
        }

        int mines = 0;
        boolean top = row > 0;
        boolean right = col + 1 < cols;
        boolean bottom = row + 1 < rows;
        boolean left = col > 0;

        if (top) {
          if (board[col][row - 1] == MINE) {
            mines += 1;
          }
          if (left) {
            if (board[col - 1][row - 1] == MINE) {
              mines += 1;
            }
          }
          if (right) {
            if (board[col + 1][row - 1] == MINE) {
              mines += 1;
            }
          }
        }
        if (bottom) {
          if (board[col][row + 1] == MINE) {
            mines += 1;
          }
          if (left) {
            if (board[col - 1][row + 1] == MINE) {
              mines += 1;
            }
          }
          if (right) {
            if (board[col + 1][row + 1] == MINE) {
              mines += 1;
            }
          }
        }
        if (left && board[col - 1][row] == MINE) {
          mines += 1;
        }
        if (right && board[col + 1][row] == MINE) {
          mines += 1;
        }

        board[col][row] = mines;
      }
    }
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

  public int getSize() {
    return cols * rows;
  }

  public int getMines(int col, int row) {
    return this.board[col][row];
  }

  public int getMines(int position) {
    int col = position % cols;
    int row = (position - col) / cols;
    return this.getMines(col, row);
  }
}
