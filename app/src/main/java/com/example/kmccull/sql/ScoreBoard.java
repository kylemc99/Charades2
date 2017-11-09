package com.example.kmccull.sql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ScoreBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
    }

    public void GoHomeOnClick(View v){
        Intent goHome = new Intent(ScoreBoard.this, MainActivity.class);
        startActivity(goHome);
    }
}
