package com.example.usrlocal.minesweeper;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ScoresFragment extends Fragment {

  ListView scoreList;

  int lvl;
  String[] myTab;

  public ScoresFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_scores, container, false);
    scoreList= (ListView)v.findViewById(R.id.scoresList);
    return v;
  }

  @Override
  public void onResume() {
    super.onResume();
    lvl = getArguments().getInt("lvl", 1);
    parse(readFile());
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,myTab);
    scoreList.setAdapter(adapter);
  }

  public void parse(String toParse){
    String scoresByLvl[] = toParse.split(";");
    myTab = scoresByLvl[lvl].split(",");
  }

  public String readFile(){
    FileInputStream fis;
    String output="";
    try{
      fis = ((ScoresActivity)getActivity()).getApplicationContext().openFileInput(GameEndActivity.FILE);
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
    return output;
  }
}
