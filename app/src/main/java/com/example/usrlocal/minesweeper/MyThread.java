package com.example.usrlocal.minesweeper;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by usrlocal on 09/01/2018.
 */
public class MyThread implements Runnable {

  private static final long interval = (1000);
  private int counter = 0;
  private Thread thread;
  private Timer timer;
  private boolean isRunning = false;
  private boolean first = true;
  private boolean mode = false;//false->classic incrementation true->decrementation/against the clock

  MyThread() {
    this.timer = new Timer();
    this.thread = new Thread(this);
  }

  public void start() {
    isRunning = true;
    if (this.first) {
      this.thread.run();
      this.first = false;
    }
  }

  @Override
  public void run() {
    this.timer.schedule(new TimerTask() {
      @Override
      public void run() {
        if (!isRunning) return;
        if(!mode) counter++;
        else if(counter>0) counter--;
      }
    }, 0, interval);
  }

  public int get() {
    return this.counter;
  }

  public void againstTheClock(boolean value) {
    mode = value;
  }

  public void interrupt() {
    this.isRunning = false;
  }

  public void reSet() {
    counter = 0;
  }

  public void reSet(int value) {
    counter = value;
  }


}
