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
        //Get Intents from Both locations we could be coming from
        String P1GameName2 = getIntent().getStringExtra(GameCreate.GameName2);
        String P1GameNamefromGamePlay = getIntent().getStringExtra(Player1GamePlay.GameNamefromP1GamePlay);
        //if Intent from Gameplay is null use the intent from Gamecreate
        if(P1GameNamefromGamePlay.length() == 0){
            String P1GameName = getIntent().getStringExtra(GameCreate.GameName2);
            Intent P1GotoGamePlay = new Intent(Player1Ready.this, Player1GamePlay.class);
            P1GotoGamePlay.putExtra(GameNameFromReady, P1GameName);
            startActivity(P1GotoGamePlay);
        }
        else {
            String P1GameName = getIntent().getStringExtra(Player1GamePlay.GameNamefromP1GamePlay);
            Intent P1GotoGamePlay = new Intent(Player1Ready.this, Player1GamePlay.class);
            P1GotoGamePlay.putExtra(GameNameFromReady, P1GameName);
            startActivity(P1GotoGamePlay);
        }
    }
}
