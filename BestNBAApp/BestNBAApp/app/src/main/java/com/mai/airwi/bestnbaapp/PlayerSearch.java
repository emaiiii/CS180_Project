package com.mai.airwi.bestnbaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlayerSearch extends AppCompatActivity {

    TextView searchTitle;
    String searchAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_search);
        String query = getIntent().getStringExtra("query");

        searchAPI = "http://1dcce538.ngrok.io/?team=" + query;

        searchTitle = (TextView)findViewById(R.id.searchPlayerTitle);
        searchTitle.setText(query);
    }
}
