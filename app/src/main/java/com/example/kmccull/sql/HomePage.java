package com.example.kmccull.sql;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    Button createGame;
    public String UserName;
    //Intent Key for UserName
    public final static String userName = "";
    public final static String GameName1 = "";
    public String Gname = "";
    // Declaring connection variables
    ConnectionClass connectionClass;  //Setup Database Connection
    public Connection con;

    //class variables
    private ArrayList<String> arrayListToDo;
    private ArrayAdapter<String> arrayAdapterToDo;

    public TextView message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        createGame = (Button) findViewById(R.id.createYourgamebutton);
        connectionClass = new ConnectionClass();
        //Create Array List
        arrayListToDo = new ArrayList<String>();
        arrayAdapterToDo = new ArrayAdapter<String>(this, R.layout.gamelist, R.id.textView, arrayListToDo);
        ListView listView = (ListView) findViewById(R.id.GamesList);
        listView.setAdapter(arrayAdapterToDo);
        unregisterForContextMenu(listView);
        FindOpenGames findgames = new FindOpenGames();
        findgames.execute("");
        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGameOnClick();
            }
        });
    }



    protected void createGameOnClick()
    {
        UserName = getIntent().getStringExtra(MainActivity.UserName);
        Intent createGame = new Intent(HomePage.this, GameCreate.class);
        createGame.putExtra(userName, UserName);
        startActivity(createGame);
    }

    public void join(View v){
        View parent = (View) v.getParent();

        TextView taskTextView = (TextView) parent.findViewById(R.id.textView);
        String task = String.valueOf(taskTextView.getText());
        Gname = task;
        AddPlayer2 addPlayer2 = new AddPlayer2();
        addPlayer2.execute("");


    }
    public class FindOpenGames extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";





        @Override
        protected String doInBackground(String... params)
        {

            try
            {
                Connection con = connectionClass.CONN();        // Connect to database


                    // Find open games where Player2_username is null.
                    String query = "Select Game_Name from Game where Player2_Username = '' or Player2_Username IS NULL;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while(rs.next())
                    {
                        String toDo = rs.getString("Game_Name");
                        arrayAdapterToDo.add(toDo);
                        isSuccess=true;


                    }
                    con.close();

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
    public class AddPlayer2 extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String Player2Name = getIntent().getStringExtra(MainActivity.UserName);




        @Override
        protected String doInBackground(String... params)
        {

            try
            {
                Connection con = connectionClass.CONN();        // Connect to database


                // Set Player2 name to current user
                String query = "Update Game SET Player2_Username = '"+ Player2Name +"' Where Game_Name = '"+Gname+"'";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
                Intent moveToReady = new Intent(HomePage.this, Player2Ready.class);
                moveToReady.putExtra(GameName1, Gname);
                //Send Current UserName to Next Page
                startActivity(moveToReady);


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
