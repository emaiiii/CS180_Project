package com.mai.airwi.bestnbaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchResults extends AppCompatActivity {

    TextView searchTitle;
    String searchAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayresults);
        String query = getIntent().getStringExtra("query");

        searchAPI = "http://1dcce538.ngrok.io/?player=" + query;

        searchTitle = (TextView)findViewById(R.id.searchTeamTitle);
        searchTitle.setText(query);
    }
}
