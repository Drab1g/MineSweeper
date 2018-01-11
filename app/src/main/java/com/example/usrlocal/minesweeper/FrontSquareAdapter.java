package com.example.usrlocal.minesweeper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class FrontSquareAdapter extends BaseAdapter {

  Context context;
  GameBoard gameBoard;
  boolean[] flagBoard;

  public FrontSquareAdapter(Context context, GameBoard gameBoard) {
    this.context = context;
    this.gameBoard = gameBoard;
    this.flagBoard = new boolean[gameBoard.getSize()];
  }

  @Override
  public View getView(final int position, View convertView, final ViewGroup parent) {
    Button button;

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
        int mines = gameBoard.getMines(position);

        switch (mines) {

          // game over
          case GameBoard.MINE:
            for (View v : parent.getTouchables()) {
              v.setVisibility(View.GONE);
            }
            Toast.makeText(context.getApplicationContext(), "Game over", Toast.LENGTH_LONG).show();
            break;

          // explode empty area
          case 0:
            break;

          // reveal what is underneath
          default:
            view.setVisibility(View.GONE);

        }
      }
    });

    button.setOnLongClickListener(new View.OnLongClickListener() {

      @Override
      public boolean onLongClick(View view) {
        view.setBackground(context.getDrawable(R.drawable.flag_button));
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
}

