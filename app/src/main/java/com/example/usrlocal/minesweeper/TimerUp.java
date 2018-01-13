package com.example.usrlocal.minesweeper;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by usrlocal on 09/01/2018.
 */
public class TimerUp extends Service {

  private IBinder MyBinder;
  private MyThread thread;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return MyBinder;
  }

  public void onCreate() {
    super.onCreate();
    MyBinder = new MyActivityBinder();
    thread = new MyThread();
  }

  public void interrupt() {
    thread.interrupt();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    thread.start();
    return START_STICKY;
  }

  public int getCounter() {
    return this.thread.get();
  }

  public void reSet() {
    this.thread.reSet();
  }

  public class MyActivityBinder extends Binder {
    TimerUp getService() {
      return TimerUp.this;
    }
  }
}
