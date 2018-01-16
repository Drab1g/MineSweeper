package com.example.usrlocal.minesweeper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FrontSquareAdapter extends BaseAdapter {

  Context context;
  GameBoard gameBoard;
  boolean[] flagBoard;
  boolean[] clearedSquares;
  GameActivity parentActivity;

  public FrontSquareAdapter(Context context, GameBoard gameBoard, GameActivity parentActivity) {
    this.context = context;
    this.gameBoard = gameBoard;
    this.flagBoard = new boolean[gameBoard.getSize()];
    this.clearedSquares = new boolean[gameBoard.getSize()];
    this.parentActivity=parentActivity;
  }

  @Override
  public View getView(final int position, final View convertView, final ViewGroup parent) {
    final Button button;

    // recycling
    if (convertView instanceof Button) {
      button = (Button) convertView;
      button.setVisibility(View.VISIBLE);
    }
    else {
      GridView grid = (GridView) parent;
      int squareSize = grid.getColumnWidth();

      button = new Button(context);
      button.setLayoutParams(new GridView.LayoutParams(squareSize, squareSize));
      button.setBackground(context.getDrawable(R.drawable.empty_button));
    }

    // listening
    button.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {

        //simple click on a flagged button removes the flag
        if(flagBoard[position]){
          view.setBackground(context.getDrawable(R.drawable.empty_button));
          flagBoard[position]=false;
          parentActivity.incrDecrCounter(true);
          return;
        }

        int mines = gameBoard.getMines(position);

        switch (mines) {

          // game over
          case GameBoard.MINE:
            for (View v : parent.getTouchables()) {
              v.setVisibility(View.GONE);
            }
            parentActivity.interrupt();
            parentActivity.endTheGame(false);
            break;

          // explode empty area
          case 0:
            explodeEmptyArea(position,parent);
            if (gameBoard.getSize() - gameBoard.getMine() == getClearedSquaresNumber()){
              parentActivity.interrupt();
              parentActivity.endTheGame(true);
            }
            break;

          // reveal what is underneath  and check if game is completed
          default:
            view.setVisibility(View.GONE);
            clearedSquares[position]=true;
            if (gameBoard.getSize() - gameBoard.getMine() == getClearedSquaresNumber()){
              parentActivity.interrupt();
              parentActivity.endTheGame(true);
            }
        }
      }
    });

    button.setOnLongClickListener(new View.OnLongClickListener() {

      @Override
      public boolean onLongClick(View view) {
        view.setBackground(context.getDrawable(R.drawable.flag_button));
        flagBoard[position]=true;
        parentActivity.incrDecrCounter(false);
        return true;
      }

    });

    return button;
  }

  @Override
  public int getCount() {
    return gameBoard.getSize();
  }

  @Override
  public Object getItem(int position) {
    return gameBoard.getMines(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public int getClearedSquaresNumber(){
    int nb=0;
    for (boolean b : clearedSquares){
      if (b) nb++;
    }
    return nb;
  }


  public int explodeEmptyArea(int clickedSquare, ViewGroup grid) {

    List<Integer> emptySquaresArea = new ArrayList<Integer>(); // storing in this list all empty square
    //positions that are within the same area as the cliquedSquare

    int currentEvaluation = 0;
    int currentPosition = clickedSquare;
    boolean continuer = true;

    emptySquaresArea.add(currentPosition);
    grid.getChildAt(currentPosition).setVisibility(View.GONE);
    clearedSquares[currentPosition]=true;

    while (continuer || currentEvaluation < emptySquaresArea.size()) {
      continuer = false;
      currentPosition = emptySquaresArea.get(currentEvaluation);

      int col = currentPosition % gameBoard.getCols();
      int row = (currentPosition - col) / gameBoard.getCols();
      boolean left = col > 0;
      boolean right = col + 1 < gameBoard.getCols();
      boolean top = row > 0;
      boolean bottom = row + 1 < gameBoard.getRows();

      if (top) {
        int upPos = currentPosition - gameBoard.getCols();
        grid.getChildAt(upPos).setVisibility(View.GONE);
        clearedSquares[upPos]=true;
        if (gameBoard.getMines(upPos) == 0 && !emptySquaresArea.contains(upPos)) {
          continuer = true;
          emptySquaresArea.add(upPos);
        }
        if (left) {
          int upleftPos = currentPosition - gameBoard.getCols() - 1;
          grid.getChildAt(upleftPos).setVisibility(View.GONE);
          clearedSquares[upleftPos]=true;
          if (gameBoard.getMines(upleftPos) == 0 && !emptySquaresArea.contains(upleftPos)) {
            continuer = true;
            emptySquaresArea.add(upleftPos);
          }
        }
        if (right) {
          int uprightPos = currentPosition - gameBoard.getCols() + 1;
          grid.getChildAt(uprightPos).setVisibility(View.GONE);
          clearedSquares[uprightPos]=true;
          if (gameBoard.getMines(uprightPos) == 0 && !emptySquaresArea.contains(uprightPos)) {
            continuer = true;
            emptySquaresArea.add(uprightPos);
          }
        }
      }
      if (bottom) {
        int downPos = currentPosition + gameBoard.getCols();
        grid.getChildAt(downPos).setVisibility(View.GONE);
        clearedSquares[downPos]=true;
        if (gameBoard.getMines(downPos) == 0 && !emptySquaresArea.contains(downPos)) {
          continuer = true;
          emptySquaresArea.add(downPos);
        }
        if (left) {
          int downleftPos = currentPosition + gameBoard.getCols() - 1;
          grid.getChildAt(downleftPos).setVisibility(View.GONE);
          clearedSquares[downleftPos]=true;
          if (gameBoard.getMines(downleftPos) == 0 && !emptySquaresArea.contains(downleftPos)) {
            continuer = true;
            emptySquaresArea.add(downleftPos);
          }
        }
        if (right) {
          int downrightPos = currentPosition + gameBoard.getCols() + 1;
          grid.getChildAt(downrightPos).setVisibility(View.GONE);
          clearedSquares[downrightPos]=true;
          if (gameBoard.getMines(downrightPos) == 0 && !emptySquaresArea.contains(downrightPos)) {
            continuer = true;
            emptySquaresArea.add(downrightPos);
          }
        }
      }
      if (left) {
        int leftPos = currentPosition - 1;
        grid.getChildAt(leftPos).setVisibility(View.GONE);
        clearedSquares[leftPos]=true;
        if (gameBoard.getMines(leftPos) == 0 && !emptySquaresArea.contains(leftPos)) {
          continuer = true;
          emptySquaresArea.add(leftPos);
        }
      }
      if (right) {
        int rightPos = currentPosition + 1;
        grid.getChildAt(rightPos).setVisibility(View.GONE);
        clearedSquares[rightPos]=true;
        if (gameBoard.getMines(rightPos) == 0 && !emptySquaresArea.contains(rightPos)) {
          continuer = true;
          emptySquaresArea.add(rightPos);
        }
      }
      currentEvaluation++;
    }
    return 1;
  }
}

