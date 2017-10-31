package com.example.kmccull.sql;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class GameCreate extends AppCompatActivity {
    //Grab the userID of the user Creating the Game
    String Player1Name;
    public Connection con;
    ConnectionClass connectionClass;
    public TextView message;
    public ProgressBar progressBar;
    EditText gmName,gmRounds, gmTime;
    public final static String GameName2 = "";
    public final static String Player1nameIntent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_create);
        connectionClass = new ConnectionClass();
        gmName = (EditText) findViewById(R.id.txtGameName);
        gmRounds = (EditText) findViewById(R.id.txtRounds);
        gmTime = (EditText) findViewById(R.id.txtTimePerRound);
        Player1Name = getIntent().getStringExtra(HomePage.userName);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void CreateGameOnClick(View view){
        createGame objSend = new createGame();
        objSend.execute("");

    }
    private class createGame extends AsyncTask<String,String, String>
    {
        String z = "";
        Boolean isSuccess = false;


        String gameName = gmName.getText().toString();
        String gameRounds =gmRounds.getText().toString();
        String gameTime = gmTime.getText().toString();
        String Player1 = getIntent().getStringExtra(HomePage.userName);



        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(GameCreate.this,r,Toast.LENGTH_SHORT).show();


        }

        @Override
        protected String doInBackground(String... params) {



            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    String CreateGameSQL = "INSERT INTO Game VALUES('" +gameName+ "','" +Player1+ "',0,'',0,"+ gameRounds+", "+gameTime+", 1, 1, 0)";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(CreateGameSQL);
                    z = "Your Game has been created, Waiting on Player 2 to join";

                    Intent i = new Intent(GameCreate.this, Player1Ready.class);
                    //Send Game Name to next page
                    i.putExtra(Player1nameIntent, Player1);
                    i.putExtra(GameName2, gameName);
                    //Send Current UserName to Next Page
                    startActivity(i);
                    finish();
                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                z = ex.getMessage();
                Log.d ("sql error", z);
            }

            return z;
        }
    }
}