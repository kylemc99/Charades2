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

public class Player1GamePlay extends AppCompatActivity {
    //Initialize Variables
    ConnectionClass connectionClass;
    public Connection con;
    private Timer timer;
    public Button button;
    public TextView Round, PointsView, Timeleft, CharadesCard;
    private static final String FORMAT = "%02d:%02d";
    public final static String GameNamefromP1GamePlay = "";

    int TotalRounds, Player1RoundOn, Player1IdeaON;

    String GameOver, GameName, P1GameNametest;
    String[] z2 = new String[7];

    //Intent Key to Send to ScoreBoard
    public final static String GameName4 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player1_game_play);


        connectionClass = new ConnectionClass();
        //Setup the TextViews
        Round = (TextView) findViewById(R.id.Round);
        PointsView = (TextView) findViewById(R.id.Points);
        Timeleft = (TextView) findViewById(R.id.Timer);
        CharadesCard = (TextView) findViewById(R.id.charadesCard);
        RunGame();

    }

    //Method for Getting information about game (Timer length, Round On, Total Rounds, Which Idea next)


    //Method for when a user clicks the Got it button
    public void GotItOnclick(View view) {
        //Increment Idea Variable and Database
        Player1IdeaON ++;
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

        testing test = new testing();
        test.execute("");

       // P1GetGameInfo getInfo = new P1GetGameInfo();
      //  getInfo.execute("");
        //Running this twice is the only way to make it work.
        GetIdea getIdea = new GetIdea();
        getIdea.execute("");
        GetIdea getIdea2 = new GetIdea();
        getIdea2.execute("");
    }
    private class testing extends AsyncTask<String, String, String[]>{
         P1GameNametest = getIntent().getStringExtra(Player1Ready.GameNameFromReady);


        @Override
        protected String[] doInBackground(String... strings) {
            return new String[0];
        }
    }

    //Class for Getting information about game (Timer length, Round On, Total Rounds, Which Idea next)
    public class P1GetGameInfo extends AsyncTask<String, String, String[]> {


        String a;
        String P1GameName = getIntent().getStringExtra(Player1Ready.GameNameFromReady);


        @Override
        protected void onPostExecute(String[] r) {


            //Updates TextViews on the Page
            PointsView.setText(z2[4]);
            Round.setText(z2[1]);  //Round On
            //Sets Variable information
            Player1IdeaON = Integer.valueOf(z2[2]);
            TotalRounds = Integer.valueOf(z2[0]);
            Player1RoundOn = Integer.valueOf(z2[1]);


            // Toast.makeText(getApplicationContext(),z[4], Toast.LENGTH_LONG).show();
            new CountDownTimer(9000, 1) {

                public void onTick(long millisUntilFinished) {
                    Timeleft.setText("" + String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                            )));
                }

                public void onFinish() {
                    //Check to see if user is on last round
                    if(Player1RoundOn == TotalRounds){
                        Intent GotoScoreBoard = new Intent(Player1GamePlay.this, ScoreBoard.class);
                        GotoScoreBoard.putExtra(GameName4, getIntent().getStringExtra(Player1Ready.GameNameFromReady));
                        startActivity(GotoScoreBoard);
                    }
                    else{
                        //Increment the Round the user is on
                        IncrementRound incrementRound = new IncrementRound();
                        incrementRound.execute("");
                        //Send user back to Ready Page
                        Intent GoBackToReady = new Intent(Player1GamePlay.this, Player1Ready.class);
                        //GoBackToReady.putExtra(Game)
                        startActivity(GoBackToReady);
                    }
                }


            }.start();
        }

        @Override
        protected String[] doInBackground(String... params) {


            try {
                Connection con = connectionClass.CONN();


                String query = "select * from Game WHERE Game_Name = '"+P1GameName+"';";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                    z2[0] = rs.getString("Game_rounds"); //Name is the string label of a column in database, read through the select query
                    z2[1] = rs.getString("Player1_Round_On");
                    z2[2] = rs.getString("Player1IdeaOn");
                    z2[3] = rs.getString("GameOver");
                    z2[4] = rs.getString("Player1_Score");
                    con.close();





            } catch (Exception ex) {
                Log.d("sql error", ex.getMessage());
            }

            return z2;
        }


    }

    private class GetIdea extends AsyncTask<String, String, String> {
        String a;


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            CharadesCard.setText(a);

        }



        @Override
        protected String doInBackground(String... params) {


            try {
                Connection con = connectionClass.CONN();


                String query = "Select * from Idea where IdeaID = '"+Player1IdeaON+"'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    a = rs.getString("Idea");
                    con.close();


                } else {
                    Toast.makeText(Player1GamePlay.this, "Something Bad happened", Toast.LENGTH_SHORT).show();
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
                    // Change below query according to your own database.
                    String IncrementPoints = "Update GAME "+"SET Player1_Score = Player1_Score + 1 "+ "Where Game_Name = '"+GameName+"'" ;
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
                    // Change below query according to your own database.
                    String getPoints = "Select Player1_Score from Game WHERE GAME_NAME = '"+GameName+"'" ;
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(getPoints);

                    if (rs.next()) {
                        b = rs.getString("Player1_Score");
                        con.close();

                    } else {
                        Toast.makeText(Player1GamePlay.this, "Something Bad happened", Toast.LENGTH_SHORT).show();
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
                    // Change below query according to your own database.
                    String IncrementPoints = "Update GAME "+"SET Player1_Round_On = Player1_Round_On + 1 "+ "Where Game_Name = '"+GameName+"'" ;
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
                    // Change below query according to your own database.
                    String IncrementIdea = "Update GAME "+"SET Player1IdeaOn = Player1IdeaOn + 1 "+ "Where Game_Name = '"+GameName+"'" ;
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


