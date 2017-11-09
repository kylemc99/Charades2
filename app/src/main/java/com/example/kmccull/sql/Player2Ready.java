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
        String GameName = getIntent().getStringExtra(HomePage.GameName1);
        Intent GotoGamePlay = new Intent(Player2Ready.this, Player2GamePlay.class);
        GotoGamePlay.putExtra(GameNamefromHomepage, GameName);
        startActivity(GotoGamePlay);
    }

    }

