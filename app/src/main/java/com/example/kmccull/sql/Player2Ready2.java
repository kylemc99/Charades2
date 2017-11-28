package com.example.kmccull.sql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;

import static com.example.kmccull.sql.R.styleable.View;

public class Player2Ready2 extends AppCompatActivity {
    //Intent Key
    public final static String GameNamefromGame = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2_ready);
    }

    public void GotoGamePlay(View v){
        String GameName = getIntent().getStringExtra(Player2GamePlay.GameNamefromGamePlay);
        Intent GotoGamePlay = new Intent(Player2Ready2.this, Player2GamePlay.class);
        GotoGamePlay.putExtra(GameNamefromGame, GameName);
        startActivity(GotoGamePlay);
    }

}