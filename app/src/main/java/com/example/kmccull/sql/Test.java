package com.example.kmccull.sql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String Test = getIntent().getStringExtra(Player1Ready.GameNameFromReady);
        Toast.makeText(Test.this,Test,Toast.LENGTH_SHORT).show();
    }
}
