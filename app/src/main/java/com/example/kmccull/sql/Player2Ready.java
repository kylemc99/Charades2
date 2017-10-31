package com.example.kmccull.sql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Player2Ready extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2_ready);

        String Player2Name = getIntent().getStringExtra(HomePage.userName);
        String GameName = getIntent().getStringExtra(HomePage.GameName);
        String r = Player2Name + " " + GameName;
        Toast.makeText(Player2Ready.this,r,Toast.LENGTH_SHORT).show();

    }
}
