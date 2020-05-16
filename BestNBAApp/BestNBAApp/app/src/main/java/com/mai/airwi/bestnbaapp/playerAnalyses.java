package com.mai.airwi.bestnbaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class playerAnalyses extends AppCompatActivity {

    Button getAvgButton;
    Button getRatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_analyses);

        getAvgButton = (Button)findViewById(R.id.getAvgButton);
        getRatingButton = (Button)findViewById(R.id.getRatingButton);

        getAvgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Info", "Get averages button clicked");


            }
        });
    }
}
