package com.example.usrlocal.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class GameEndActivity extends AppCompatActivity {

  private TextView myScore;
  private TextView bestScoreValue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_end);
    myScore = (TextView) findViewById(R.id.myScore);
    bestScoreValue = (TextView) findViewById(R.id.bestScoreValue);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if(getIntent().getBooleanExtra("victory",true)){
      myScore.setText("Victoire en "+ getIntent().getStringExtra("time") + "s");
    }else{
      myScore.setText("Defaite en " + getIntent().getStringExtra("time") + "s");
    }
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    String VicOrDef = "";
    if(prefs.getBoolean("HighScore_LV1_Victory",true)){
      VicOrDef = "Victoire en ";
    }else{
      VicOrDef = "Defaite en ";
    }

    bestScoreValue.setText(VicOrDef + prefs.getString("HighScore_LV1_Time",null) + "s");
  }
}
