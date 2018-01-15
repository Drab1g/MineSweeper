package com.example.usrlocal.minesweeper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GameActivity extends AppCompatActivity {

  private TextView timerDisplayed;
  private TextView mineCounter;
  private GridView mineGridView;
  private GridView buttonGridView;

  private Intent serviceIntent;
  public TimerUp myTimerUp;
  private boolean isRunning = false;
  private int mineNumber;
  int myLevel;
  private static final String FILE = "Score.txt";
  private Runnable runnableCode;
  private ServiceConnection myServiceConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName name, IBinder iBinder) {
      myTimerUp = ((TimerUp.MyActivityBinder) iBinder).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      myTimerUp = null;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_view);

    String level = getIntent().getStringExtra("level");
    myLevel=0;
    switch (level){
      case "EASY":
        myLevel = 1;
        break;
      case "MEDIUM":
        myLevel = 2;
        break;
      case "HARD":
        myLevel = 3;
        break;
    }
    GameSettings settings = GameSettings.valueOf(level);
    mineNumber=settings.getMines();
    GameBoard board = new GameBoard(settings);

    mineGridView = (GridView) findViewById(R.id.mineGridView);
    mineGridView.setNumColumns(settings.getCols());
    mineGridView.setAdapter(new BackSquareAdapter(this, board));

    buttonGridView = (GridView) findViewById(R.id.buttonGridView);
    buttonGridView.setNumColumns(settings.getCols());
    buttonGridView.setAdapter(new FrontSquareAdapter(this, board, this));

    timerDisplayed = (TextView) findViewById(R.id.timer);
    mineCounter = (TextView)findViewById(R.id.mineCount);

    mineCounter.setText(Integer.toString(mineNumber));

    serviceIntent = new Intent(this, TimerUp.class);
    bindService(serviceIntent, myServiceConnection, Context.BIND_AUTO_CREATE);
  }

  @Override
  protected void onResume() {
    super.onResume();

    startService(serviceIntent);
    isRunning = true;

    final Handler handler = new Handler();
    final int delay = 1000;

    //appel récursif
    runnableCode = new Runnable() {
      @Override
      public void run() {
        if (isRunning) {
          timerDisplayed.setText(String.valueOf(myTimerUp.getCounter()));
          handler.postDelayed(this, 1000);
        }
      }
    };

    handler.postDelayed(runnableCode, 1000);


  }

  @Override
  protected void onPause() {
    super.onPause();
    myTimerUp.reSet();
    //myTimerUp.interrupt();
    stopService(serviceIntent);
  }

  public void reSet(){
    myTimerUp.reSet();
  }

  public void interrupt(){
    myTimerUp.interrupt();
  }

  public void endTheGame(boolean victory){
    Intent gameEndActivity = new Intent(GameActivity.this, GameEndActivity.class);
    gameEndActivity.putExtra("level", myLevel);
    gameEndActivity.putExtra("victory", victory);
    gameEndActivity.putExtra("time", Integer.toString(myTimerUp.getCounter()));
    saveHighestScoreGame(victory);
    startActivity(gameEndActivity);
  }

  public void incrDecrCounter(boolean value){
    if (value){
      mineNumber++;
    }
    else{
      mineNumber--;
    }
    mineCounter.setText(Integer.toString(mineNumber));
  }

  public void saveHighestScoreGame(boolean victory){
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    SharedPreferences.Editor editor = prefs.edit();

    if(victory){

      //editor.putBoolean("HighScore_LV1_Victory",victory);
      if(Integer.valueOf(prefs.getString("HighScore_LV"+ myLevel +"_Time","10000")) > myTimerUp.getCounter()){
        //Log.d("valeur enregistree",prefs.getString("HighScore_LV1_Time","10000"));
        editor.putString("HighScore_LV"+ myLevel +"_Time",Integer.toString(myTimerUp.getCounter()));
      }
    }
    editor.commit();
  }
  //TODO
  /*public void saveScoreInFile(){
    FileOutputStream fos;
    if(!fileExist(FILE)){
      try{
        fos = openFileOutput(FILE,Context.MODE_PRIVATE);
        String toInject = "||";
        fos.write(toInject.getBytes());
        fos.close();
      }catch (FileNotFoundException e){
        e.printStackTrace();
      }catch (IOException e){
        e.printStackTrace();
      }
    }

    FileInputStream fis;
    try{
      fis = openFileInput(FILE);
      String output="";
      byte[] buffer = new byte[1024];
      while(fis.read(buffer)!=-1){
        output = new String(buffer);
      }
      fis.close();
    }catch (FileNotFoundException e){
      e.printStackTrace();
    }catch (IOException e){
      e.printStackTrace();
    }

  }

  public boolean fileExist(String fname){
    File file = getBaseContext().getFileStreamPath(fname);
    return file.exists();
  }*/
}

