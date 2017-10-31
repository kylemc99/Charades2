package com.example.kmccull.sql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Player1Ready extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player1_ready);

        Intent i =this.getIntent();
        String Player1Name = getIntent().getStringExtra(GameCreate.Player1nameIntent);
        String GameName = getIntent().getStringExtra(GameCreate.GameName2);
        String r =  Player1Name;
        Toast.makeText(Player1Ready.this,r,Toast.LENGTH_SHORT).show();

    }
}
