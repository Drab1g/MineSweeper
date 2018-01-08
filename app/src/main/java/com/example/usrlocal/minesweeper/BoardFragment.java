package com.example.usrlocal.minesweeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class BoardFragment extends Fragment {

    GridView mineBoard;
    GridView buttonGridView;

    int size=1;
    int numberColumn=9;
    Board board;

    public BoardFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);
        size=getActivity().getIntent().getIntExtra("size",1);
        switch (size){
            case 1:
                board = new Board(9,9);
                numberColumn=9;
                break;
            case 2:
                board = new Board(16,12);
                numberColumn=12;
                break;
            case 3:
                board = new Board(22,16);
                numberColumn=16;
                break;
            default:
                board = new Board(9,9);
                numberColumn=9;
                break;
        }
        mineBoard=(GridView)v.findViewById(R.id.mineBoard);
        mineBoard.setNumColumns(numberColumn);
        mineBoard.setAdapter(new BackSquareAdapter(this.getContext(),board));

        buttonGridView=(GridView)v.findViewById(R.id.buttonGridView);
        buttonGridView.setNumColumns(numberColumn);
        buttonGridView.setAdapter(new FrontSquareAdapter(this.getContext(),board));
        return v;
    }
}
