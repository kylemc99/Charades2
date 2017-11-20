package com.example.kmccull.sql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class Player1Ready extends AppCompatActivity {
    //Intent Key
    public final static String GameName3 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player1_ready);


    }
    public void GotoGamePlay(View v){
        Intent GotoGamePlay = new Intent(Player1Ready.this, Player1GamePlay.class);
        GotoGamePlay.putExtra(GameName3, getIntent().getStringExtra(GameCreate.GameName2));
        startActivity(GotoGamePlay);
    }
}
