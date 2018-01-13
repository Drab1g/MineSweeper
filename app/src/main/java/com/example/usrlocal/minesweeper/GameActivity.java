package com.example.usrlocal.minesweeper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

  private Intent serviceIntent;
  public TimerUp myTimerUp;
  private boolean isRunning = false;
  private TextView timerDisplayed;
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

    Bundle args = new Bundle();
    args.putString("level", getIntent().getStringExtra("level"));

    BoardFragment frag = new BoardFragment();
    frag.setArguments(args);

    FragmentManager fm = getSupportFragmentManager();
    fm.beginTransaction().add(R.id.board_fragment, frag).commit();

    timerDisplayed = (TextView) findViewById(R.id.timer);

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
    //myTimerUp.reSet();
    //myTimerUp.interrupt();
    stopService(serviceIntent);
  }

  public void reSet(){
    myTimerUp.reSet();
  }

  public void interrupt(){
    myTimerUp.interrupt();
  }
}

