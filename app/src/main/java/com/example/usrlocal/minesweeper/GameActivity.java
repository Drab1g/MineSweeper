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
    FragmentManager fm = getSupportFragmentManager();
    BoardFragment frag = new BoardFragment();
    Bundle args = new Bundle();
    args.putLong("size", size);
    frag.setArguments(args);
    fm.beginTransaction().add(R.id.boardContainer, frag).commit();

  }
}
