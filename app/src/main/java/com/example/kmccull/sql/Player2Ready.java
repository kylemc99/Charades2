package com.example.kmccull.sql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;

import static com.example.kmccull.sql.R.styleable.View;

public class Player2Ready extends AppCompatActivity {
    //Intent Key
    public final static String GameNamefromHomepage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2_ready);
    }

    public void GotoGamePlay(View v){
        String P2GameName2 = getIntent().getStringExtra(GameCreate.GameName2);
        String P2GameNamefromGamePlay = getIntent().getStringExtra(Player2GamePlay.GameNamefromGamePlay);
        //determine which intent to use depending on where we are coming from
        if(P2GameNamefromGamePlay.length() == 0){
            //user is coming from Homepage
            String P2GameName = getIntent().getStringExtra(HomePage.GameName1);
            Intent P2GotoGamePlay = new Intent(Player2Ready.this, Player1GamePlay.class);
            P2GotoGamePlay.putExtra(GameNamefromHomepage, P2GameName);
            startActivity(P2GotoGamePlay);
        }
        else {
            //user is coming from GamePlay
            String P2GameName = getIntent().getStringExtra(Player2GamePlay.GameNamefromGamePlay);
            Intent P2GotoGamePlay = new Intent(Player2Ready.this, Player2GamePlay.class);
            P2GotoGamePlay.putExtra(GameNamefromHomepage, P2GameName);
            startActivity(P2GotoGamePlay);
        }
    }

    }

