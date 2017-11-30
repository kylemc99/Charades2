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
import android.os.CountDownTimer;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.Timer;

public class Player2GamePlay extends AppCompatActivity {
    //Initialize Variables
    ConnectionClass connectionClass;
    public Connection con;
    private Timer timer;
    public Button button;
    public TextView Round, PointsView, Timeleft, CharadesCard;
    private static final String FORMAT = "%02d:%02d";
    public final static String GameNamefromGamePlay = "";

    int TotalRounds, Player2RoundOn, Player2Points, Player2IdeaON;
    long GameTime;

    String[] z = new String[7];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2_game_play);
        connectionClass = new ConnectionClass();
        //Setup the TextViews
        Round = (TextView) findViewById(R.id.Round);
        PointsView = (TextView) findViewById(R.id.Points);
        Timeleft = (TextView) findViewById(R.id.Timer);
        CharadesCard = (TextView) findViewById(R.id.charadesCard);
        RunGame();

    }

    //Method for when a user clicks the Got it button
    public void GotItOnclick(View view) {
        //Increment Idea Variable and Database
        Player2IdeaON ++;
        IncrementIdea incrementIdea = new IncrementIdea();
        incrementIdea.execute("");
        //Increment Points
        IncrementPoints incrementPoints = new IncrementPoints();
        incrementPoints.execute("");
        //Get a new Idea
        GetIdea getIdea = new GetIdea();
        getIdea.execute("");
        //Get updated Points from Database
        GetPoints getPoints = new GetPoints();
        getPoints.execute("");

    }

    public void RunGame() {
        //Get the Game Info
        GetGameInfo getInfo = new GetGameInfo();
        getInfo.execute("");
        //Running this twice is the only way to make it work.
        GetIdea getIdea = new GetIdea();
        getIdea.execute("");
        GetIdea getIdea2 = new GetIdea();
        getIdea2.execute("");
    }


    //Class for Getting information about game (Timer length, Round On, Total Rounds, Which Idea next)
    private class GetGameInfo extends AsyncTask<String, String, String[]> {


        String a;
        //Get the Name of the game from the previous Page
        String GameName = getIntent().getStringExtra(Player2Ready.GameNamefromHomepage);


        @Override
        protected void onPostExecute(String[] r) {



            PointsView.setText(z[5]);
            Round.setText(z[3]);
            //Set variables from information from Database
            Player2IdeaON = Integer.valueOf(z[4]);
            TotalRounds = Integer.valueOf(z[1]);
            Player2RoundOn = Integer.valueOf(z[3]);
            GameTime = java.lang.Long.valueOf(z[2]);
            long a = GameTime;
            //Run a Timer for 90 seconds
            new CountDownTimer(90000, 1) {

                public void onTick(long millisUntilFinished) {
                    Timeleft.setText("" + String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                            )));
                }

                public void onFinish() {
                    //Check to see if user is on last round
                    if(Player2RoundOn == TotalRounds){
                        Intent GotoScoreBoard = new Intent(Player2GamePlay.this, ScoreBoard.class);
                        GotoScoreBoard.putExtra(GameNamefromGamePlay, getIntent().getStringExtra(Player2Ready.GameNamefromHomepage));
                        startActivity(GotoScoreBoard);
                    }
                    //not on the last round send back to Ready Page
                    else{
                        //Increment the Round the user is on
                        IncrementRound incrementRound = new IncrementRound();
                        incrementRound.execute("");
                        //Send user back to Ready Page
                        Intent GoBackToReady = new Intent(Player2GamePlay.this, Player2Ready.class);
                        GoBackToReady.putExtra(GameNamefromGamePlay, GameName);
                        startActivity(GoBackToReady);
                    }
                }


            }.start();
        }

        @Override
        protected String[] doInBackground(String... params) {


            try {
                Connection con = connectionClass.CONN();


                String query = "select * from Game WHERE Game_Name = '"+GameName+"';";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                //Get information from Database and place information in an array
                if (rs.next()) {
                    z[0] = rs.getString("Player2_Username");
                    z[1] = rs.getString("Game_rounds"); //Name is the string label of a column in database, read through the select query
                    z[2] = String.valueOf(rs.getInt("Game_Time"));
                    z[3] = rs.getString("Player2_Round_On");
                    z[4] = rs.getString("Player2IdeaOn");
                    z[5] = rs.getString("Player2_Score");
                    z[6] = rs.getString("GameOver");
                    con.close();


                } else {
                    Toast.makeText(Player2GamePlay.this, "Something Bad happened", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception ex) {
                Log.d("sql error", ex.getMessage());
            }

            return z;
        }


    }

    private class GetIdea extends AsyncTask<String, String, String> {
        String a;
        String GameName = getIntent().getStringExtra(Player2Ready.GameNamefromHomepage);

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            CharadesCard.setText(a);

        }



        @Override
        protected String doInBackground(String... params) {


            try {
                Connection con = connectionClass.CONN();


                String query = "Select * from Idea where IdeaID = '"+Player2IdeaON+"'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    a = rs.getString("Idea");
                    con.close();


                } else {
                    Toast.makeText(Player2GamePlay.this, "Something Bad happened", Toast.LENGTH_SHORT).show();
                }


                //Take information from Select Query and send to RunGame Method
                //RunGame(TotalRounds, GameTime, Player2RoundOn, Player2IdeaON, GameOver, Player2Points);

            } catch (Exception ex) {
                Log.d("sql error", ex.getMessage());
            }

            return a;
        }

    }
    private class IncrementPoints extends AsyncTask<String, String, String>{

        String b;
        String GameName = getIntent().getStringExtra(Player2Ready.GameNamefromHomepage);

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // PointsView.setText(b);

        }



        @Override
        protected String doInBackground(String... params) {


            try {
                Connection con = connectionClass.CONN();        // Connect to database
                if (con == null)
                {
                    b = "Check Your Internet Access!";
                }
                else
                {
                    // Increment Points in Database
                    String IncrementPoints = "Update GAME "+"SET Player2_Score = Player2_Score + 1 "+ "Where Game_Name = '"+GameName+"'" ;
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(IncrementPoints);
                    con.close();

                }

            } catch (Exception ex) {
                Log.d("sql error", ex.getMessage());
            }

            return b;
        }

    }
    private class GetPoints extends AsyncTask<String, String, String>{

        String b;
        String GameName = getIntent().getStringExtra(Player2Ready.GameNamefromHomepage);

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
             PointsView.setText(b);

        }



        @Override
        protected String doInBackground(String... params) {


            try {
                Connection con = connectionClass.CONN();        // Connect to database
                if (con == null)
                {
                    b = "Check Your Internet Access!";
                }
                else
                {
                    //Grab the score of Player2
                    String getPoints = "Select Player2_Score from Game WHERE GAME_NAME = '"+GameName+"'" ;
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(getPoints);

                    if (rs.next()) {
                        b = rs.getString("Player2_Score");
                        con.close();

                    } else {
                        Toast.makeText(Player2GamePlay.this, "Something Bad happened", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception ex) {
                Log.d("sql error", ex.getMessage());
            }

            return b;
        }

    }
    private class IncrementRound extends AsyncTask<String, String, String>{
        String b;
        String GameName = getIntent().getStringExtra(Player2Ready.GameNamefromHomepage);

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }



        @Override
        protected String doInBackground(String... params) {


            try {
                Connection con = connectionClass.CONN();        // Connect to database
                if (con == null)
                {
                    b = "Check Your Internet Access!";
                }
                else
                {
                    // Add a round to the Round on for Player 2
                    String IncrementPoints = "Update GAME "+"SET Player2_Round_On = Player2_Round_On + 1 "+ "Where Game_Name = '"+GameName+"'" ;
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(IncrementPoints);
                    con.close();

                }

            } catch (Exception ex) {
                Log.d("sql error", ex.getMessage());
            }

            return b;
        }
    }
    private class IncrementIdea extends AsyncTask<String, String, String>{
        String b;
        String GameName = getIntent().getStringExtra(Player2Ready.GameNamefromHomepage);

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }



        @Override
        protected String doInBackground(String... params) {


            try {
                Connection con = connectionClass.CONN();        // Connect to database
                if (con == null)
                {
                    b = "Check Your Internet Access!";
                }
                else
                {
                    // Add one to Player2 Idea On
                    String IncrementIdea = "Update GAME "+"SET Player2IdeaOn = Player2IdeaOn + 1 "+ "Where Game_Name = '"+GameName+"'" ;
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(IncrementIdea);
                    con.close();

                }

            } catch (Exception ex) {
                Log.d("sql error", ex.getMessage());
            }

            return b;
        }
    }

}

