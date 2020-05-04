package com.mai.airwi.bestnbaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    String server_url = "http://fce66049.ngrok.io/";

    EditText rUserName, rFirstName, rLastName, rEmail, rPassword, rPasswordConf;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        rUserName = (EditText)findViewById(R.id.userEditText);
        rPassword = (EditText)findViewById(R.id.firstEditText);
        rLastName = (EditText)findViewById(R.id.lastEditText);
        rEmail = (EditText)findViewById(R.id.emailEditText);
        rPassword = (EditText)findViewById(R.id.passwordEditText);
        rPasswordConf = (EditText)findViewById(R.id.confEditText);
        registerButton = (Button)findViewById(R.id.regButton);

        /*registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Info", "Register button clicked");

                String userName, firstName, lastName, email, password, passwordConf;
                userName = rUserName.getText().toString();
                firstName = rFirstName.getText().toString();
                lastName = rLastName.getText().toString();
                email = rEmail.getText().toString();
                password = rPassword.getText().toString();
                passwordConf = rPasswordConf.getText().toString();

                if(userName.equals("")){
                    Toast.makeText(Main3Activity.this, "Username Required", Toast.LENGTH_SHORT).show();
                }
                else if(firstName.equals("")){
                    Toast.makeText(Main3Activity.this, "First Name Required", Toast.LENGTH_SHORT).show();
                }
                else if(lastName.equals("")){
                    Toast.makeText(Main3Activity.this, "Last Name Required", Toast.LENGTH_SHORT).show();
                }
                else if(email.equals("")){
                    Toast.makeText(Main3Activity.this, "Email Required", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    Toast.makeText(Main3Activity.this, "Password Required", Toast.LENGTH_SHORT).show();
                }
                else if(passwordConf.equals("")){
                    Toast.makeText(Main3Activity.this, "Confirm Password", Toast.LENGTH_SHORT).show();
                }
                else if(!passwordConf.equals(password)){
                    Toast.makeText(Main3Activity.this, "Incorrect Password Confirmation", Toast.LENGTH_SHORT).show();
                }
                else{

                }*/
                /*final UserAccount acc = new UserAccount(rUserName.getText().toString(),rFirstName.getText().toString(),
                        rLastName.getText().toString(),rEmail.getText().toString(),rPassword.getText().toString());

                final String accURL = server_url + "?register=1&&userName=" + acc.getUserName()
                        + "&&password=" + acc.getPassword() + "&&firstName=" + acc.getFirstName()
                        + "&&lastName=" + acc.getLastName() + "&&email=" + acc.getEmail();

                //final RequestQueue requestQueue = Volley.newRequestQueue(Main3Activity.this);

                Log.i("URL", accURL);*/
            //}
        //});
    }
}
