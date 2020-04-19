package com.mai.airwi.bestnbaapp;

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

import java.util.*;

public class MainActivity extends AppCompatActivity {

    Button searchButton;
    TextView textView;
    String server_url = "http://a2201f39.ngrok.io/?team=lakers&player=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = (Button)findViewById(R.id.searchButton);
        textView = (TextView) findViewById(R.id.instrTextView);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Button clicked");

                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Info", "Successful connection");

                                // code for team display
                                List<String> list = new ArrayList<String>();
                                list = read(response);
                                Team teamData = new Team(list);

                                String toDisplay = "Team ID: " + teamData.getTeamID();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nMin. Year: " + teamData.getMinYear();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nMax. Year: " + teamData.getMaxYear();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nAbbr.: " + teamData.getAbbr();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nNickName: " + teamData.getNickname();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nYear Founded: " + teamData.getYearFounded();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nCity: " + teamData.getCity();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nArena: " + teamData.getArena();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nArena Capacity: " + teamData.getArenaCapacity();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nOwner: " + teamData.getOwner();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nGen. Manager: " + teamData.getGenManager();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nHead Coach: " + teamData.getHeadCoach();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nD. League Affiliate: " + teamData.getDLeagueAffiliate();
                                textView.setText(toDisplay);


                                // code for player display
                                /*List<String> list = new ArrayList<String>();
                                list = read(response);
                                Player playerData = new Player(list);

                                String toDisplay = "Player Name: " + playerData.getPlayerName();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nTeam ID: " + playerData.getTeamID();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nPlayer ID: " + playerData.getPlayerID();
                                textView.setText(toDisplay);
                                toDisplay = toDisplay + "\nSeason: " + playerData.getSeason();
                                textView.setText(toDisplay);*/

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
        });
    }

    public static List<String> read(String result){
        List<String> set = new ArrayList<String>();
        int tempIndex = 1;
        String tempString;

        for(int index = 0; index < result.length(); index++){
            if(result.charAt(index) == ',' || index == result.length() - 1){
                tempString = result.substring(tempIndex, index);

                if(tempString.charAt(0)== '\"' && tempString.charAt(tempString.length() - 1) == '\"'){
                    tempString = tempString.substring(1, tempString.length() - 1);
                }

                set.add(tempString);
                tempIndex = index + 1;
            }
        }

        return set;
    }
}
