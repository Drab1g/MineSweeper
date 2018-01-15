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
  private Button tryAgain;
  int currentLevel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_end);
    myScore = (TextView) findViewById(R.id.myScore);
    bestScoreValue = (TextView) findViewById(R.id.bestScoreValue);
    tryAgain = (Button) findViewById(R.id.tryAgain);
  }

  @Override
  protected void onResume() {
    super.onResume();

    currentLevel = getIntent().getIntExtra("level",1);
    if(getIntent().getBooleanExtra("victory",false)){
      myScore.setText("Victoire en "+ getIntent().getStringExtra("time") + "s");
    }else{
      myScore.setText("Defaite en " + getIntent().getStringExtra("time") + "s");
    }

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    String VicOrDef = "";
    if(prefs.getString("HighScore_LV"+currentLevel+"_Time",null)!=null){
      VicOrDef = "Victoire en ";
      bestScoreValue.setText(VicOrDef + prefs.getString("HighScore_LV"+currentLevel+"_Time",null) + "s");
    }else{
      VicOrDef = "Defaite";
      bestScoreValue.setText(VicOrDef);
    }



    tryAgain.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(GameEndActivity.this, MenuActivity.class);
        startActivity(intent);
      }
    });
  }
}
