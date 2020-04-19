package com.mai.airwi.bestnbaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button bn_search;
    TextView txt_user;

   /* Button button;
    TextView textView;
    String server_url = "https://54b391c2.ngrok.io";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bn_search = (Button)findViewById(R.id.searchbn);
        txt_user = (TextView)findViewById(R.id.userText);

        bn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NEEDS SWITCH
                //op1, teams
                // --
                //op2, players
                Intent i = new Intent(MainActivity.this, SearchResults.class);
                String playerQuery = txt_user.getText().toString();
                i.putExtra("query",playerQuery);
                startActivity(i);
            }
        });

        /*bn_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TeamSearch.class);
                String teamQuery = txt_team.getText().toString();
                i.putExtra("query",teamQuery);
                startActivity(i);
            }
        });*/

        /*
        button = (Button)findViewById(R.id.bn);
        textView = (TextView) findViewById(R.id.txt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Button clicked");

                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                textView.setText(response);
                                Log.i("Info", "Successful connection");
                                requestQueue.stop();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        textView.setText("error...");
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                });
                requestQueue.add(stringRequest);
            }
        });*/
    }

}
