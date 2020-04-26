package com.mai.airwi.bestnbaapp;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    /*Button searchButton;
    TextView textView;
    EditText searchField;
    String server_url = "http://658994b8.ngrok.io";
    Switch category;
    int searchType = 0;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SearchFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()){
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_basket:
                            selectedFragment = new BasketFragment();
                            break;
                        case R.id.nav_analyze:
                            selectedFragment = new AnalyzeFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

        /*searchButton = (Button)findViewById(R.id.searchButton);
        textView = (TextView)findViewById(R.id.instrTextView);
        searchField = (EditText)findViewById(R.id.searchEditText);
        category = (Switch)findViewById(R.id.switchCategory);

        category.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //team search
                    searchType = 1;
                } else {
                    //team search
                    searchType = 0;
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Button clicked");

                String query = searchField.getText().toString();
                query.replace(" ","%20");
                /*
                try {
                    query = URLEncoder.encode(URLEncoder.encode(query,"UTF-8") );
                }
                catch (IOException e) {
                    query = "invalid";  // Encoding Error
                }*/

                /*final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                switch(searchType) {
                    case 0: // Player Search
                        final String playerSearchURL = server_url + "?team=0&player=" + query; // "?team=lakers&player=0";

                        StringRequest playerRequest = new StringRequest(Request.Method.POST, playerSearchURL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Info", "Successful connection");

                                        if (response.equals("no player found")) {
                                            textView.setText("no player found");
                                        }
                                        else {
                                            List<String> list = new ArrayList<String>();
                                            String toDisplay;

                                            list = read(response);

                                            //code for player display
                                            Player playerData = new Player(list);

                                            toDisplay = "Player Name: " + playerData.getPlayerName();
                                            toDisplay = toDisplay + "\nTeam ID: " + playerData.getTeamID();
                                            toDisplay = toDisplay + "\nPlayer ID: " + playerData.getPlayerID();
                                            toDisplay = toDisplay + "\nSeason: " + playerData.getSeason();
                                            textView.setText(toDisplay);
                                        }

                                        requestQueue.stop();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        textView.setText("Search Error: No response from server." + "\nRequest: " + playerSearchURL);
                                        error.printStackTrace();
                                        requestQueue.stop();
                                    }
                                }
                        );

                        requestQueue.add(playerRequest);

                        break;

                    case 1: //team search
                        final String teamSearchURL = server_url + "?team=" + query + "&player=0";

                        StringRequest teamRequest = new StringRequest(Request.Method.POST, teamSearchURL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Info", "Successful connection");


                                        if (response.equals("no team found")) {
                                            textView.setText("no team found");
                                        }
                                        else {
                                            List<String> list = new ArrayList<String>();
                                            String toDisplay;

                                            list = read(response);

                                            // code for team display
                                            Team teamData = new Team(list);

                                            toDisplay = "Team ID: " + teamData.getTeamID();
                                            toDisplay = toDisplay + "\nMin. Year: " + teamData.getMinYear();
                                            toDisplay = toDisplay + "\nMax. Year: " + teamData.getMaxYear();
                                            toDisplay = toDisplay + "\nAbbr.: " + teamData.getAbbr();
                                            toDisplay = toDisplay + "\nNickName: " + teamData.getNickname();
                                            toDisplay = toDisplay + "\nYear Founded: " + teamData.getYearFounded();
                                            toDisplay = toDisplay + "\nCity: " + teamData.getCity();
                                            toDisplay = toDisplay + "\nArena: " + teamData.getArena();
                                            toDisplay = toDisplay + "\nArena Capacity: " + teamData.getArenaCapacity();
                                            toDisplay = toDisplay + "\nOwner: " + teamData.getOwner();
                                            toDisplay = toDisplay + "\nGen. Manager: " + teamData.getGenManager();
                                            toDisplay = toDisplay + "\nHead Coach: " + teamData.getHeadCoach();
                                            toDisplay = toDisplay + "\nD. League Affiliate: " + teamData.getDLeagueAffiliate();
                                            textView.setText(toDisplay);

                                        }

                                        requestQueue.stop();
                                    }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        textView.setText("Search Error: No response from server." + "\nRequest: " + teamSearchURL);
                                        error.printStackTrace();
                                        requestQueue.stop();
                                    }
                        });

                        requestQueue.add(teamRequest);

                        break;

                    default:
                        textView.setText("ERROR: invalid selection");
                        requestQueue.stop();
                }
            }
        });*/
    //}
}