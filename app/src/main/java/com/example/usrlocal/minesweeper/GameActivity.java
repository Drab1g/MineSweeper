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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

  private TextView timerDisplayed;
  private TextView mineCounter;
  private TextView resetButton;

  private GridView mineGridView;
  private GridView buttonGridView;

  private GameSettings settings;
  public TimerUp myTimerUp;
  private Runnable runnableCode;
  private Intent serviceIntent;

  private boolean isRunning = false;
  private boolean mode;
  private int mineNumber;
  private int myLevel;
  private String level;

  private ServiceConnection myServiceConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName name, IBinder iBinder) {
      myTimerUp = ((TimerUp.MyActivityBinder) iBinder).getService();
      againstTheClock(mode);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      myTimerUp = null;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    resetButton = (TextView) findViewById(R.id.resetButton);

    mode = getIntent().getBooleanExtra("mode", false);
    level = getIntent().getStringExtra("level");
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
    settings = GameSettings.valueOf(level);
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
          int coounter = myTimerUp.getCounter();
          timerDisplayed.setText(String.valueOf(coounter));
          if(coounter==0 && mode){
            Intent gameEndActivity = new Intent(GameActivity.this, GameEndActivity.class);
            gameEndActivity.putExtra("level", myLevel);
            gameEndActivity.putExtra("victory", false);
            gameEndActivity.putExtra("time", Integer.toString(myTimerUp.getCounter()));
            isRunning = false;
            startActivity(gameEndActivity);

          }
          handler.postDelayed(this, 1000);
        }
      }
    };
    handler.postDelayed(runnableCode, 1000);

    resetButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent gameActivity = new Intent(GameActivity.this, GameActivity.class);
        gameActivity.putExtra("level", level);
        gameActivity.putExtra("mode", mode);
        isRunning=false;
        startActivity(gameActivity);
      }
    });
  }

  public void endTheGame(boolean victory){
    isRunning = false;
    Intent gameEndActivity = new Intent(GameActivity.this, GameEndActivity.class);
    gameEndActivity.putExtra("level", myLevel);
    gameEndActivity.putExtra("victory", victory);
    gameEndActivity.putExtra("time", Integer.toString(myTimerUp.getCounter()));
    saveHighestScoreGame(victory);
    startActivity(gameEndActivity);
  }

  public void saveHighestScoreGame(boolean victory){
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    SharedPreferences.Editor editor = prefs.edit();
    if(victory){
      if(Integer.valueOf(prefs.getString("HighScore_LV"+ myLevel +"_Time","10000")) > myTimerUp.getCounter()){
        editor.putString("HighScore_LV"+ myLevel +"_Time",Integer.toString(myTimerUp.getCounter()));
        editor.commit();
      }
    }
  }

  public void incrDecrCounter(boolean value){
    if (value)mineNumber++;
    else mineNumber--;
    mineCounter.setText(Integer.toString(mineNumber));
  }

  @Override
  protected void onPause() {
    super.onPause();
    reSet();
    stopService(serviceIntent);
  }

  public void reSet(){
    myTimerUp.reSet();
  }

  public void reSet(int value){
    myTimerUp.reSet(value);
  }

  public void interrupt(){
    myTimerUp.interrupt();
  }

  public void againstTheClock(boolean value) {
    myTimerUp.againstTheClock(value);
    if(mode) reSet(settings.getTime());
  }

}
