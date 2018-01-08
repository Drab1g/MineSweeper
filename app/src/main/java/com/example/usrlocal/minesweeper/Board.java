package com.example.usrlocal.minesweeper;

public class Board {

    private int rows=9;
    private int cols=9;
    private int[][] board;

    public Board(int rows, int cols) {
        this.setRows(rows);
        this.setCols(cols);

        board = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = 10;
            }
        }
        fillMines();
        fillNumbers();
    }

    public void fillMines() {
        int needed = 10;
        switch(cols){
            case 9:
                needed = 10;
                break;
            case 12:
                needed = 25;
                break;
            case 16:
                needed = 40;
                break;
        }

        while (needed > 0) {
            int x = (int) Math.floor(Math.random() * rows);
            int y = (int) Math.floor(Math.random() * cols);
            if (board[x][y]!=0) {
                board[x][y]=0;
                needed--;
            }
        }
    }

    public void fillNumbers() {
        for (int i = 0; i < rows ; i++){
            for (int j = 0; j < cols ; j++ ){

                if (board[i][j]==0) {
                    continue;
                }
                else{
                    int temp = 0;
                    boolean l = (j - 1) >= 0;
                    boolean r = (j + 1) < cols;
                    boolean u = (i - 1) >= 0;
                    boolean d = (i + 1) < rows;

                    if (u) {
                        if (board[i-1][j]==0) {
                            temp++;
                        }
                        if (l) {
                            if (board[i-1][j-1]==0) {
                                temp++;
                            }
                        }
                        if (r) {
                            if (board[i-1][j+1]==0) {
                                temp++;
                            }
                        }
                    }
                    if (d) {
                        if (board[i+1][j]==0) {
                            temp++;
                        }
                        if (l) {
                            if (board[i+1][j-1]==0) {
                                temp++;
                            }
                        }
                        if (r) {
                            if (board[i+1][j+1]==0) {
                                temp++;
                            }
                        }
                    }
                    if (l) {
                        if (board[i][j-1]==0) {
                            temp++;
                        }
                    }
                    if (r) {
                        if (board[i][j+1]==0) {
                            temp++;
                        }
                    }
                    if (temp!=0) {
                        board[i][j]=temp;
                    }
                }
            }
        }
    }


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int[][] getBoard() {
        return board;
    }
}
