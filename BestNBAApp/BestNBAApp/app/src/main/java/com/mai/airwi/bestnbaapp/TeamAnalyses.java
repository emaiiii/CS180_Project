package com.mai.airwi.bestnbaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TeamAnalyses extends AppCompatActivity {

    Button getPercButton;
    Button getRatingButton;
    Button getAvgButton;
    Button getCompButton;

    String username;

    java.util.ArrayList<String> userSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_analyses);

        userSet = (ArrayList<String>) getIntent().getSerializableExtra("set");
        username = (String) getIntent().getSerializableExtra("username");

        Log.i("Info", "Team Analyses Page");
        Log.i("Info", "User Set Length: " + String.valueOf(userSet.size()));
        Log.i("Info", "Username: " + username);

        getPercButton = (Button)findViewById(R.id.getPercButton);
        getRatingButton = (Button)findViewById(R.id.getRatingButton);
        getAvgButton = (Button)findViewById(R.id.getAvgButton);
        getCompButton = (Button)findViewById(R.id.getCompButton);

        getPercButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Info", "Match up Info button clicked");

                ArrayList<String> set = new ArrayList<String>(userSet);
                Log.i("info", String.valueOf(set.size()));
                Log.i("info", String.valueOf(userSet.size()));

                Intent intent = new Intent(TeamAnalyses.this, PercentageResults.class);
                intent.putExtra("set", set);
                startActivity(intent);
            }
        });

        getRatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Info", "Get Team Ratings Button Clicked");

                ArrayList<String> set = new ArrayList<String>(userSet);
                Log.i("Info", "User Set Length: " + String.valueOf(userSet.size()));

                Intent intent = new Intent(TeamAnalyses.this, TeamRatingResults.class);
                intent.putExtra("set", set);
                startActivity(intent);
            }
        });

        getAvgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Info", "Get Team Average Button Clicked");

                ArrayList<String> set = new ArrayList<String>(userSet);
                Log.i("Info", "User Set Length: " + String.valueOf(userSet.size()));

                Intent intent = new Intent(TeamAnalyses.this, TeamAvgResults.class);
                intent.putExtra("set", set);
                startActivity(intent);
            }
        });

        getCompButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Info", "Get Team Average Button Clicked");

                Intent intent = new Intent(TeamAnalyses.this, TeamWinComp.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
