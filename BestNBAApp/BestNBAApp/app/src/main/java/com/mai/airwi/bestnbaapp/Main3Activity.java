package com.mai.airwi.bestnbaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity {

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
    }

}
