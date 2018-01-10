package com.example.usrlocal.minesweeper;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_view);

    Bundle args = new Bundle();
    args.putString("level", getIntent().getStringExtra("level"));

    BoardFragment frag = new BoardFragment();
    frag.setArguments(args);

    FragmentManager fm = getSupportFragmentManager();
    fm.beginTransaction().add(R.id.board_fragment, frag).commit();
  }
}
