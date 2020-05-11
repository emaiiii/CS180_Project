package com.mai.airwi.bestnbaapp;

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

//registration
public class Main3Activity extends AppCompatActivity {

    String server_url = "http://58a7402c.ngrok.io/";

    EditText rUserName, rFirstName, rLastName, rEmail, rPassword, rPasswordConf;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        rUserName = (EditText) findViewById(R.id.userEditText);
        rFirstName = (EditText) findViewById(R.id.firstEditText);
        rLastName = (EditText) findViewById(R.id.lastEditText);
        rEmail = (EditText) findViewById(R.id.emailEditText);
        rPassword = (EditText) findViewById(R.id.passEditText);
        rPasswordConf = (EditText) findViewById(R.id.confEditText);
        registerButton = (Button) findViewById(R.id.regButton);
    }

    public void onClick(View view) {
        Log.i("Info", "Register button clicked");

        String userName, firstName, lastName, email, password, passwordConf;
        userName = rUserName.getText().toString();
        firstName = rFirstName.getText().toString();
        lastName = rLastName.getText().toString();
        email = rEmail.getText().toString();
        password = rPassword.getText().toString();
        passwordConf = rPasswordConf.getText().toString();

        // check if fields are filled out
        if (userName.equals("")) {
            Toast.makeText(Main3Activity.this, "Username Required", Toast.LENGTH_SHORT).show();
        } else if (firstName.equals("")) {
            Toast.makeText(Main3Activity.this, "First Name Required", Toast.LENGTH_SHORT).show();
        } else if (lastName.equals("")) {
            Toast.makeText(Main3Activity.this, "Last Name Required", Toast.LENGTH_SHORT).show();
        } else if (email.equals("")) {
            Toast.makeText(Main3Activity.this, "Email Required", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(Main3Activity.this, "Password Required", Toast.LENGTH_SHORT).show();
        } else if (passwordConf.equals("")) {
            Toast.makeText(Main3Activity.this, "Confirm Password", Toast.LENGTH_SHORT).show();
        } else if (!passwordConf.equals(password)) {
            Toast.makeText(Main3Activity.this, "Incorrect Password Confirmation", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Main3Activity.this, "Account Created", Toast.LENGTH_SHORT).show();

            // create URL string to be send
            final String accDetURL = server_url + "?register=1&&username=" + userName + "&&password=" + password +
                    "&&firstname=" + firstName + "&&lastname=" + lastName + "&&email=" + email;
            Log.i("URL", accDetURL);

            // set up request queue
            final RequestQueue requestQueue = Volley.newRequestQueue(Main3Activity.this);

            // POST to the server
            StringRequest stringRequest = new StringRequest(Request.Method.POST, accDetURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Info", "successful connection");
                            Log.i("Info", response);

                            requestQueue.stop();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Info", "Error: unsuccessful connection");

                            error.printStackTrace();
                            requestQueue.stop();
                        }
                    });
            requestQueue.add(stringRequest);

            // go back to the login page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

