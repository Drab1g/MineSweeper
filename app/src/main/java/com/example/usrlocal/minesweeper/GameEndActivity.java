package com.example.usrlocal.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GameEndActivity extends AppCompatActivity {

  private TextView myScore;
  private TextView bestScoreValue;
  private Button tryAgain;
  private Button register;
  private Button bestScores;
  private EditText editText;

  boolean  victory=false;
  int currentLevel;
  String currentTime;

  public static final String FILE = "Score.txt";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_end);
    myScore = (TextView) findViewById(R.id.myScore);
    bestScoreValue = (TextView) findViewById(R.id.bestScoreValue);
    tryAgain = (Button) findViewById(R.id.tryAgain);
    register = (Button) findViewById(R.id.register);
    bestScores = (Button) findViewById(R.id.BestScores);
    editText = (EditText) findViewById(R.id.pseudoRegister);
  }

  @Override
  protected void onResume() {
    super.onResume();

    currentLevel = getIntent().getIntExtra("level",1);
    currentTime = getIntent().getStringExtra("time");
    victory = getIntent().getBooleanExtra("victory",false);
    if(victory){
      myScore.setText("Victoire en "+ currentTime + "s");
    }else{
      myScore.setText("Defaite en " + currentTime + "s");
      editText.setVisibility(View.GONE);
      register.setVisibility(View.GONE);
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

    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        saveScoreInFile();
        Intent intent = new Intent(GameEndActivity.this, ScoresActivity.class);
        startActivity(intent);
      }
    });

    bestScores.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(GameEndActivity.this, ScoresActivity.class);
        startActivity(intent);
      }
    });

  }

  public void saveScoreInFile(){
    FileOutputStream fos;
    if(!fileExist(FILE)){
      try{
        fos = openFileOutput(FILE, Context.MODE_PRIVATE);
        String toInject = " ; ; ";
        fos.write(toInject.getBytes());
        fos.close();
      }catch (FileNotFoundException e){
        e.printStackTrace();
      }catch (IOException e){
        e.printStackTrace();
      }
    }

    FileInputStream fis;
    String output="";
    try{
      fis = openFileInput(FILE);
      int c;
      String temp="";
      while( (c = fis.read()) != -1){
        temp = temp + Character.toString((char)c);
      }
      output = temp;
      fis.close();
    }catch (FileNotFoundException e){
      e.printStackTrace();
    }catch (IOException e){
      e.printStackTrace();
    }

    String scoresByLvl[] = output.split(";");
    String currentLvlScores = scoresByLvl[currentLevel-1];
    if(currentLvlScores.equals(" ")){
      currentLvlScores += currentTime;
      if(editText.getText().toString().equals("")) currentLvlScores+="s noName";
      else currentLvlScores+= "s " + editText.getText();
    }else{
      currentLvlScores+=",";
      currentLvlScores+=currentTime;
      if(editText.getText().toString().equals("")) currentLvlScores+="s noName";
      else currentLvlScores+= "s " + editText.getText();
    }
    scoresByLvl[currentLevel-1] = currentLvlScores;
    output = scoresByLvl[0] + ";" + scoresByLvl[1]  + ";" +  scoresByLvl[2];

    try{
      fos = openFileOutput(FILE,Context.MODE_PRIVATE);
      fos.write(output.getBytes());
      fos.close();
    }catch (FileNotFoundException e){
      e.printStackTrace();
    }catch (IOException e){
      e.printStackTrace();
    }
  }

  public boolean fileExist(String fname){
    File file = getBaseContext().getFileStreamPath(fname);
    return file.exists();
  }

}
