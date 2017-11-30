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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class RegisterUser extends AppCompatActivity {

    public Connection con;
    ConnectionClass connectionClass;
    public TextView message;
    public ProgressBar progressBar;
    EditText edtuserid,edtpass, verifypass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        connectionClass = new ConnectionClass();
        edtuserid = (EditText) findViewById(R.id.txtUserName);
        edtpass = (EditText) findViewById(R.id.txtPassword);
        verifypass = (EditText) findViewById(R.id.txtVerifyPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
    public void btnConn(View view){
        Send objSend = new Send();
        objSend.execute("");

    }
    private class Send extends AsyncTask<String,String, String>
    {
        String z = "";
        Boolean isSuccess = false;


        String userid = edtuserid.getText().toString();
        String password = edtpass.getText().toString();
        String verify = verifypass.getText().toString();


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(RegisterUser.this,r,Toast.LENGTH_SHORT).show();

            if(isSuccess) {
                Intent i = new Intent(RegisterUser.this, MainActivity.class);
                startActivity(i);
                finish();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            if(userid.trim().equals("")|| password.trim().equals(""))
                z = "Please enter User Name and Password";
            //Make sure verify password and password match
            else if (!verify.equals(password))
            {
                z = "Passwords do not match";
            }
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String CreateUser = "INSERT INTO USERS VALUES('"+ userid +"','"+ password +"')";
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(CreateUser);
                        z = "Your user account has been created";
                        Intent i = new Intent(RegisterUser.this, MainActivity.class);
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
            }
            return z;
        }
    }
}


