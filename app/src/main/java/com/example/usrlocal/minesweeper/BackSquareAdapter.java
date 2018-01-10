package com.example.usrlocal.minesweeper;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BackSquareAdapter extends BaseAdapter {

  private Context context;
  private GameBoard gameBoard;
  private int[] minesColorMap = {
    -1,
    R.color.one,
    R.color.two,
    R.color.three,
    R.color.four,
    R.color.five,
    R.color.six,
    R.color.seven,
    R.color.eight
  };

  public BackSquareAdapter(Context context, GameBoard gameBoard) {
    this.context = context;
    this.gameBoard = gameBoard;
  }

  /**
   * renders the number of mines in the area as a TextView or a mine as an ImageView. both views'
   * instances may be recycled.
   */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    GridView grid = (GridView) parent;
    int squareSize = grid.getColumnWidth();
    int mines = gameBoard.getMines(position);

    if (mines == GameBoard.MINE) {
      ImageView imageView;

      // recycling
      if (convertView instanceof ImageView) {
        imageView = (ImageView) convertView;
      }
      else {
        imageView = new ImageView(context);
        imageView.setLayoutParams(new GridView.LayoutParams(squareSize, squareSize));
        imageView.setBackgroundColor(context.getResources().getColor(R.color.lightGrey));
        imageView.setImageDrawable(context.getDrawable(R.drawable.mine_noire));
      }

      return imageView;
    }
    else {
      TextView textView;

      // recycling
      if (convertView instanceof TextView) {
        textView = (TextView) convertView;
      }
      else {
        textView = new TextView(context);
        textView.setLayoutParams(new GridView.LayoutParams(squareSize, squareSize));
        textView.setBackgroundColor(context.getResources().getColor(R.color.lightGrey));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(18);
      }

      // colored number square
      if (mines > 0) {
        textView.setText(String.valueOf(mines));
        textView.setTextColor(context.getResources().getColor(minesColorMap[mines]));
      }
      // empty square
      else {
        textView.setText("");
      }

      return textView;
    }
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
  public long getItemId(int i) {
    return i;
  }
}
