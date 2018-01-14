package com.example.usrlocal.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class BoardFragment extends Fragment {

  GridView mineGridView;
  GridView buttonGridView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.board_fragment, container, false);
    String level = getArguments().getString("level");
    GameSettings settings = GameSettings.valueOf(level);
    GameBoard board = new GameBoard(settings);

    mineGridView = (GridView) view.findViewById(R.id.mineGridView);
    mineGridView.setNumColumns(settings.getCols());
    mineGridView.setAdapter(new BackSquareAdapter(this.getContext(), board));

    buttonGridView = (GridView) view.findViewById(R.id.buttonGridView);
    buttonGridView.setNumColumns(settings.getCols());
    buttonGridView.setAdapter(new FrontSquareAdapter(this.getContext(), board, this));





    return view;
  }

  public void stopTimer(){
    //getActivity().stopService(new Intent(getActivity().getApplicationContext(), TimerUp.class));
    ((GameActivity)getActivity()).interrupt();
  }

  public void endOfTheGame(boolean victory){
    ((GameActivity)getActivity()).endTheGame(victory);
  }


}
