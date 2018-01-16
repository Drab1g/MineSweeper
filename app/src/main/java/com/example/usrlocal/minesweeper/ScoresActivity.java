package com.example.usrlocal.minesweeper;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScoresActivity extends AppCompatActivity {

  private ViewPager viewPager = null;
  private TabLayout tableLayout = null;
  private ScoresPagerAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scores);
    viewPager = (ViewPager) findViewById(R.id.viewerpager);
    tableLayout = (TabLayout) findViewById(R.id.tabs);
  }

  @Override
  protected void onResume() {
    super.onResume();
    adapter = new ScoresPagerAdapter(this, getSupportFragmentManager());
    viewPager.setOffscreenPageLimit(adapter.getCount()-1);
    viewPager.setAdapter(adapter);
    tableLayout.setupWithViewPager(viewPager);
  }
}
