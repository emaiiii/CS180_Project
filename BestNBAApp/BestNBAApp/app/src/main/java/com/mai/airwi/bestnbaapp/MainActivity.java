package com.mai.airwi.bestnbaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button bn_team;
    Button bn_player;
    TextView txt_team;
    TextView txt_player;

   /* Button button;
    TextView textView;
    String server_url = "https://54b391c2.ngrok.io";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bn_player = (Button)findViewById(R.id.searchplayer);
        bn_team = (Button)findViewById(R.id.searchteam);
        txt_player = (TextView)findViewById(R.id.playerEditText);
        txt_team = (TextView)findViewById(R.id.teamEditText);

        bn_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PlayerSearch.class);
                String playerQuery = txt_player.getText().toString();
                i.putExtra("query",playerQuery);
                startActivity(i);
            }
        });

        bn_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TeamSearch.class);
                String teamQuery = txt_team.getText().toString();
                i.putExtra("query",teamQuery);
                startActivity(i);
            }
        });

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
