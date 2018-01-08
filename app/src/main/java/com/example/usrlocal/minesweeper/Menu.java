package com.example.usrlocal.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    Button easyButton;
    Button mediumButton;
    Button hardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        easyButton= (Button)findViewById(R.id.easy_button);
        mediumButton= (Button)findViewById(R.id.medium_button);
        hardButton= (Button)findViewById(R.id.hard_button);
        easyButton.setOnClickListener(this);
        mediumButton.setOnClickListener(this);
        hardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent otherActivity;
        otherActivity= new Intent(Menu.this, GameActivity.class);
        switch (view.getId()){
            case R.id.easy_button:
                otherActivity.putExtra("size",1);
                break;
            case R.id.medium_button:
                otherActivity.putExtra("size",2);
                break;
            case R.id.hard_button:
                otherActivity.putExtra("size",3);
                break;
        }
        startActivity(otherActivity);
    }
}
