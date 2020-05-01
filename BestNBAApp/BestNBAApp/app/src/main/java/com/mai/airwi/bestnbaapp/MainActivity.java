package com.mai.airwi.bestnbaapp;

//import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.usernameEditText);
        password = (EditText)findViewById(R.id.passwordEditText);
        logInButton = (Button)findViewById(R.id.loginButton);

        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                validateAndOpen(username.getText().toString(), password.getText().toString());
            }
        });
    }

    public void validateAndOpen(String userName, String password){

        if(((userName.equals("admin")) && (password.equals("12345")))){
            Intent intent = new Intent(this,Main2Activity.class);
            startActivity(intent);
        }
    }

}