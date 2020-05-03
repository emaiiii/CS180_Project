package com.mai.airwi.bestnbaapp;

//import android.app.Fragment;
import android.accounts.Account;
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

    String server_url = "http://ceae842d.ngrok.io/";

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
        registerButton = (Button)findViewById(R.id.registerButton);

        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkAdmin(username.getText().toString(), password.getText().toString());

                final String requestAccURL = server_url + "?getacc=1&&username=" + username.getText().toString();

                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, requestAccURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                List<String> list = new ArrayList<String>();

                                list = read(response);

                                UserAccount acc = new UserAccount(list);

                                if(acc.correctPass(password.getText().toString())){
                                    Log.i("Info.", "log in verified");
                                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Log.i("Info.", "log in not verified");
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
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registerPage();
            }
        });
    }

    public void checkAdmin(String username, String password){
        if(username.equals("admin") && password.equals("123")){
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }
    }

    public void registerPage(){
        Intent intent = new Intent(this,Main3Activity.class);
        startActivity(intent);
    }

}