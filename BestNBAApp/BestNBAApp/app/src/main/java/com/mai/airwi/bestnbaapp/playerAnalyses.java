package com.mai.airwi.bestnbaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class playerAnalyses extends AppCompatActivity {

    Button getAvgButton;
    Button getRatingButton;

    ArrayList<String> userSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_analyses);

        userSet = (ArrayList<String>) getIntent().getSerializableExtra("set");

        Log.i("Info", "Player Analyses Page");
        Log.i("Info", String.valueOf(userSet.size()));
        Log.i("Info", userSet.get(0));
        Log.i("Info", userSet.get(1));

        getAvgButton = (Button)findViewById(R.id.getAvgButton);
        getRatingButton = (Button)findViewById(R.id.getRatingButton);

        getAvgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Info", "Get averages button clicked");

                ArrayList<String> set = new ArrayList<String>(userSet);
                Log.i("info", String.valueOf(set.size()));
                Log.i("info", String.valueOf(userSet.size()));

                Intent intent = new Intent(playerAnalyses.this, playerAvgResults.class);
                intent.putExtra("set", set);
                startActivity(intent);
            }
        });

        getRatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Info", "Get averages button clicked");

                ArrayList<String> set = new ArrayList<String>(userSet);
                Log.i("info", String.valueOf(set.size()));

                Intent intent = new Intent(playerAnalyses.this, ratingResults.class);
                intent.putExtra("set", set);
                startActivity(intent);
            }
        });
    }
}
