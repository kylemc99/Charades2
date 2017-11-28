package com.example.kmccull.sql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class Player1Ready extends AppCompatActivity {
    //Intent Key
    public final static String GameNameFromReady = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player1_ready);

    }
    public void GotoGamePlay(View v){
        String P1GameName = getIntent().getStringExtra(GameCreate.GameName2);
        Toast.makeText(Player1Ready.this,P1GameName,Toast.LENGTH_SHORT).show();
        Intent P1GotoGamePlay = new Intent(Player1Ready.this, Player1GamePlay.class);
        P1GotoGamePlay.putExtra(GameNameFromReady, P1GameName);
        startActivity(P1GotoGamePlay);
    }
}
