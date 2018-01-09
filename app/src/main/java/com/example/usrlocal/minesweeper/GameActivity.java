package com.example.usrlocal.minesweeper;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    int size = getIntent().getIntExtra("size", 1);

    Bundle args = new Bundle();
    args.putLong("size", size);

    BoardFragment frag = new BoardFragment();
    frag.setArguments(args);

    FragmentManager fm = getSupportFragmentManager();
    fm.beginTransaction().add(R.id.boardContainer, frag).commit();
  }
}
