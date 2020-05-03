package com.mai.airwi.bestnbaapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class Main3Activity extends AppCompatActivity {

    String server_url = "http://ceae842d.ngrok.io/";

    EditText userName;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText passwordConf;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        userName = (EditText)findViewById(R.id.userEditText);
        firstName = (EditText)findViewById(R.id.firstEditText);
        lastName = (EditText)findViewById(R.id.lastEditText);
        email = (EditText)findViewById(R.id.emailEditText);
        password = (EditText)findViewById(R.id.passwordEditText);
        passwordConf = (EditText)findViewById(R.id.confEditText);
        registerButton = (Button)findViewById(R.id.registerButton);


        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Info.", "Register button clicked");

                if(password.getText().toString() == passwordConf.getText().toString()){

                    // put the user input into an object to encapsulate data
                    UserAccount newAccount = new UserAccount(userName.getText().toString(), firstName.getText().toString(), lastName.getText().toString(),
                            email.getText().toString(), password.getText().toString());

                    // set up request queue
                    final RequestQueue requestQueue = Volley.newRequestQueue(Main3Activity.this);

                    // create URL string to be sent
                    final String accDetURL = server_url + "?register=1&&username=" + newAccount.getUserName() + "&&firstname=" + newAccount.getFirstName() +
                            "&&lastname=" + newAccount.getLastName() + "&&email=" + newAccount.getEmail() + "&&password=" + newAccount.getPassword();

                    // POST to the server
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, accDetURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Info.", "successful connection");
                                    Toast.makeText(Main3Activity.this, response, Toast.LENGTH_SHORT).show();

                                    //Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                                    //startActivity(intent);

                                    requestQueue.stop();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("Info.", "unsuccessful connection");
                                    Toast.makeText(Main3Activity.this, "cannot create this account", Toast.LENGTH_SHORT).show();

                                    error.printStackTrace();
                                    requestQueue.stop();
                                }
                            });
                }
                else{
                    Log.i("Info", "Registration: Incorrect password");
                    Toast.makeText(Main3Activity.this, "Incorrect Password Entered. Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*public void register(){
        UserAccount newAccount = new UserAccount(userName.getText().toString(), firstName.getText().toString(), lastName.getText().toString(),
                email.getText().toString(), password.getText().toString());

        // user account details formulated in URL
        final String accDetURL = server_url + "?register=1&&username=" + newAccount.getUserName() + "&&firstname=" + newAccount.getFirstName() +
                "&&lastname=" + newAccount.getLastName() + "&&email=" + newAccount.getEmail() + "&&password=" + newAccount.getPassword();


        Intent intent = new Intent(this, MainActivity.class);
    }*/
}
