package com.example.usrlocal.minesweeper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private Intent serviceIntent;
    private TimerUp myTimerUp;
    private boolean isRunning = false;
    private TextView timerDisplayed;

    Runnable runnableCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("aaaaaa", "lddddda");
        setContentView(R.layout.activity_game);
        int size = getIntent().getIntExtra("size",1);
        FragmentManager fm = getSupportFragmentManager();
        BoardFragment frag = new BoardFragment();
        Bundle args = new Bundle();
        args.putLong("size", size);
        frag.setArguments(args);
        fm.beginTransaction().add(R.id.boardContainer,frag).commit();

        timerDisplayed = (TextView) findViewById(R.id.timer);

        serviceIntent = new Intent(this,TimerUp.class);
        bindService(serviceIntent,myServiceConnection, Context.BIND_AUTO_CREATE);

        startService(serviceIntent);
        isRunning = true;
        Log.d("ddddddfftrttt", "yyyy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Handler handler = new Handler();
        final int delay = 1000;


        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isRunning){
                    timerDisplayed.setText(String.valueOf(myTimerUp.getCounter()));
                    Log.d("run", "la");
                }
            }
        },delay);*/

        //appel récursif
        runnableCode = new Runnable() {
            @Override
            public void run() {
                if(isRunning){
                    timerDisplayed.setText(String.valueOf(myTimerUp.getCounter()));
                    handler.postDelayed(this, 1000);
                    Log.d("run", "la");
                }
            }
        };

        handler.postDelayed(runnableCode, 1000);


    }

    private ServiceConnection myServiceConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {

            myTimerUp = ((TimerUp.MyActivityBinder)iBinder).getService();
            Log.d("marche ici", "la");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("stop", "la");
            myTimerUp = null;
        }
    };

}
