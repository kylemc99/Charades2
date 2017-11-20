package com.example.kmccull.sql;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;

public class ScoreBoardP2 extends AppCompatActivity {

    //Initialize Variables
    ConnectionClass connectionClass;
    public Connection con;
    private Timer timer;
    public Button button;
    public TextView Player1Score, Player2Score, Player1Name, Player2Name, Winner;
    String  GameName;
    String[] z = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_p2);

        connectionClass = new ConnectionClass();
        Player1Name = (TextView) findViewById(R.id.teamnameoneText);
        Player2Name = (TextView) findViewById(R.id.teamnametwoText);
        Player1Score = (TextView) findViewById(R.id.TeamoneScore);
        Player2Score = (TextView) findViewById(R.id.TeamtwoScore);
        GameName = getIntent().getStringExtra(Player2GamePlay.GameNamefromReady);
        Winner = (TextView) findViewById(R.id.Winner);
        GetScores getScores = new GetScores();
        getScores.execute("");
    }

    public void GoHomeOnClick(View v){
        Intent goHome = new Intent(ScoreBoardP2.this, MainActivity.class);
        startActivity(goHome);
    }

    private class GetScores extends AsyncTask<String, String, String[]> {


        String a;

        @Override
        protected void onPostExecute(String[] r) {

            //  Toast.makeText(getApplicationContext(), r[1], Toast.LENGTH_LONG).show();
            //   Round = (TextView) findViewById(R.id.Round);
            //   PointsView = (TextView) findViewById(R.id.Points);

            Player2Name.setText(z[1]);
            Player1Name.setText(z[0]);
            Player1Score.setText(z[2]);
            Player2Score.setText(z[3]);
            int P1S = Integer.valueOf(z[2]);
            int P2S = Integer.valueOf(z[3]);
            if (P1S > P2S){
                Winner.setText(z[0] +" Wins!");
            } else {
                Winner.setText(z[1] + " Wins!");
            }
        }

        @Override
        protected String[] doInBackground(String... params) {


            try {
                Connection con = connectionClass.CONN();


                String query = "select * from Game WHERE Game_Name = '"+GameName+"';";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    z[0] = rs.getString("Player1_Username");
                    z[1] = rs.getString("Player2_Username");
                    z[2] = rs.getString("Player1_Score");
                    z[3] = rs.getString("Player2_Score");
                    con.close();


                } else {
                    Toast.makeText(ScoreBoardP2.this, "Something Bad happened", Toast.LENGTH_SHORT).show();
                }


                //Take information from Select Query and send to RunGame Method
                //RunGame(TotalRounds, GameTime, Player2RoundOn, Player2IdeaON, GameOver, Player2Points);

            } catch (Exception ex) {
                Log.d("sql error", ex.getMessage());
            }

            return z;
        }


    }
}

