package com.example.kmccull.sql;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.Timer;

public class Player2GamePlay extends AppCompatActivity {
    ConnectionClass connectionClass;
    public Connection con;
    private Timer timer;

    int GameTime, TotalRounds,Player2RoundOn,Player2Points, Player2IdeaON ;
    String Player2Name, GameOver;
    String[] z;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2_game_play);
        GetGameInfo getInfo = new GetGameInfo();
        getInfo.execute("");
        RunGame();


    }

    //Method for Getting information about game (Timer length, Round On, Total Rounds, Which Idea next)


    //Method for when a user clicks the Got it button
    public void GotItOnclick(View view){

    }
    public void RunGame(){
        //Setup Timer and Run in a While Loop
        TextView Round = (TextView) findViewById(R.id.Round);
        TextView PointsView = (TextView) findViewById(R.id.Points);
        TextView TimeLeft = (TextView) findViewById(R.id.Timer);

        Round.setText(z[3]);
        PointsView.setText(z[5]);
        TimeLeft.setText(z[2]);
        Toast.makeText(Player2GamePlay.this,"Got here",Toast.LENGTH_SHORT).show();


        //After While Loop Check Incriment Player2RoundOn and Check if Player2RoundOn = TotalRounds
        //If it does send to ScoreBoard
        //Else Send to Player2Ready.Class

    }

    //Class for Getting information about game (Timer length, Round On, Total Rounds, Which Idea next)
    private class GetGameInfo extends AsyncTask<String,String, String[]>
    {

        String[] z =  {"","","","","","","",""};
        String GameName = getIntent().getStringExtra(Player2Ready.GameNamefromHomepage);



        @Override
        protected void onPostExecute(String[] r) {



        }

        @Override
        protected String[] doInBackground(String... params) {



            try {
                Connection con = connectionClass.CONN();
                if (con == null) {

                } else {

                    String query = "select * from Game WHERE Game_Name = 'R11'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()){
                         z[0] = rs.getString("Player2_Username");
                         z[1]= rs.getString("Game_rounds"); //Name is the string label of a column in database, read through the select query
                         z[2] = rs.getString("GameTime");
                         z[3] = rs.getString("Player2_Round_On");
                         z[4] = rs.getString("Player2IdeaOn");
                         z[5] = rs.getString("Player2_Score");
                         z[6] = rs.getString("GameOver");
                        con.close();
                        //Take information from Select Query and send to RunGame Method
                       // RunGame(TotalRounds, GameTime, Player2RoundOn, Player2IdeaON, GameOver, Player2Points);

                    }
                    else {
                        Toast.makeText(Player2GamePlay.this,"Something Bad happened",Toast.LENGTH_SHORT).show();
                    }


                    //Take information from Select Query and send to RunGame Method
              //      RunGame(TotalRounds, GameTime, Player2RoundOn, Player2IdeaON, GameOver, Player2Points);


                }
            }
            catch (Exception ex)
            {
                Log.d ("sql error", ex.getMessage());
            }

            return z;
        }


        }
    }

