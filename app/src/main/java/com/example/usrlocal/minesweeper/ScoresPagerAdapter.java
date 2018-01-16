package com.example.usrlocal.minesweeper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by usrlocal on 16/01/2018.
 */

public class ScoresPagerAdapter extends FragmentPagerAdapter {

  private Context context;

  public ScoresPagerAdapter(Context context, FragmentManager fm) {
    super(fm);
    this.context = context;
  }

  @Override
  public Fragment getItem(int position) {
    ScoresFragment frag = new ScoresFragment();
    Bundle args = new Bundle();
    switch (position) {
      case 0:
        args.putInt("lvl", 0);
        break;
      case 1:
        args.putInt("lvl", 1);
        break;
      case 2:
        args.putInt("lvl", 2);
        break;
      default:
        args.putInt("lvl", 0);
        break;
    }
    frag.setArguments(args);
    return frag;
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public CharSequence getPageTitle(int position) {

    switch (position) {
      case 0: return "Facile";
      case 1: return "Moyen";
      case 2: return "Difficile";
      default: return null;
    }
  }
}
