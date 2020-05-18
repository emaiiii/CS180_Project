package com.mai.airwi.bestnbaapp;

//import android.app.Fragment;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import static com.mai.airwi.bestnbaapp.SearchFragment.read;

public class MainActivity extends AppCompatActivity {

    String server_url = "http://704f9b8e.ngrok.io/";

    EditText username;
    EditText password;
    Button logInButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.usernameEditText);
        password = (EditText)findViewById(R.id.passwordEditText);
        logInButton = (Button)findViewById(R.id.loginButton);
        registerButton = (Button)findViewById(R.id.regButton);

        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkAdmin(username.getText().toString(), password.getText().toString())){
                    return;
                }

                final String reqAccURL = server_url + "?username=" + username.getText().toString() +
                        "&&password=" + password.getText().toString();

                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                Log.i("URL:", reqAccURL);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, reqAccURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("Incorrect Username or Password")){
                                    Log.i("Info", response);
                                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                                }
                                else if(response.equals("Welcome Back")) {
                                    Log.i("Info", "Log in verified");
                                    String sendUsername = username.getText().toString();

                                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                                    intent.putExtra("username", sendUsername);
                                    startActivity(intent);
                                }
                                requestQueue.stop();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Error: no connection to server", Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                                requestQueue.stop();
                            }
                        });

                requestQueue.add(stringRequest);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registerPage();
            }
        });
    }

    public boolean checkAdmin(String username, String password){
        if(username.equals("admin") && password.equals("123")){
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);

            return true;
        }

        return false;
    }

    public void registerPage(){
        Intent intent = new Intent(this,Main3Activity.class);
        startActivity(intent);
        finish();
    }

}