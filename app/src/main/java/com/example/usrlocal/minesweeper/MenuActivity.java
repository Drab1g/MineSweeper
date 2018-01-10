package com.example.usrlocal.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

  private Button easyButton;
  private Button mediumButton;
  private Button hardButton;

  private HashMap<Integer, GameSettings> buttonLevelMap = new HashMap() {{
    put(R.id.easy_button, GameSettings.EASY);
    put(R.id.medium_button, GameSettings.MEDIUM);
    put(R.id.hard_button, GameSettings.HARD);
  }};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.menu_view);

    easyButton = (Button) findViewById(R.id.easy_button);
    mediumButton = (Button) findViewById(R.id.medium_button);
    hardButton = (Button) findViewById(R.id.hard_button);

    easyButton.setOnClickListener(this);
    mediumButton.setOnClickListener(this);
    hardButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    Intent gameActivity = new Intent(MenuActivity.this, GameActivity.class);
    GameSettings level = buttonLevelMap.get(view.getId());
    gameActivity.putExtra("level", level.name());
    startActivity(gameActivity);
  }
}
