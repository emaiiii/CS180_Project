package com.mai.airwi.bestnbaapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import static com.mai.airwi.bestnbaapp.SearchResultsPlayers.read;

public class SearchResultsTeams extends AppCompatActivity {

    String server_url = "http://704f9b8e.ngrok.io/";
    String username;

    String response;
    List<String> list;

    TextView scr1, scr2, scr3, scr4;
    TableLayout playerTable;
    TableRow tableRow;
    Button controlButton;
    int control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_teams);

        username = (String)getIntent().getSerializableExtra("username");
        Log.i("Info", "Team Search Results Page");
        Log.i("Username", username);

        // control set
        control = 0;

        // must initialize the components that you ae going to create
        scr1 = new TextView(this);
        scr2 = new TextView(this);
        scr3 = new TextView(this);
        scr4 = new TextView(this);

        tableRow = new TableRow(this);
        playerTable = (TableLayout)findViewById(R.id.PlayerSearchTable);

        // format columns
        playerTable.setColumnStretchable(0, true);
        playerTable.setColumnStretchable(1, true);
        playerTable.setColumnStretchable(2, true);
        playerTable.setColumnStretchable(3, true);

        // load data from intent
        response = getIntent().getExtras().getString("response");
        list = new ArrayList<String>();

        // encapsulate data into object
        list = read(response);

        Team team = new Team(list);
        final String city,name,abbr,tid;

        city = team.getCity();
        name = team.getNickname();
        abbr = team.getAbbr();
        tid = String.valueOf( team.getTeamID() );

        // put texts into TextView
        scr1.setText(city);
        scr1.setGravity(Gravity.CENTER);
        scr1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr1.setTextSize(15);

        scr2.setText(name);
        scr2.setGravity(Gravity.CENTER);
        scr2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr2.setTextSize(15);

        scr3.setText(abbr);
        scr3.setGravity(Gravity.CENTER);
        scr3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr3.setTextSize(15);

        scr4.setText(tid);
        scr4.setGravity(Gravity.CENTER);
        scr4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr4.setTextSize(15);

        // put TextViews into the row
        tableRow.addView(scr1);
        tableRow.addView(scr2);
        tableRow.addView(scr3);
        tableRow.addView(scr4);

        // put the row into the table
        playerTable.addView(tableRow);

        controlButton = (Button)findViewById(R.id.addButton);

        controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Add button clicked");

                control += 1; // if control = even -> not in set (show add), else (show remove)

                // generate URL
                // ?addplayer=kobe%20bryant&&addusername=mango
                final String teamName = name.replace(" ","%20");
                String controlURL = "";

                if (control % 2 == 1) { // ODD -> Add
                    controlURL = server_url + "?addteam=" + teamName + "&&addusername=" + username;
                }
                else { // EVEN -> Remove
                    controlURL = server_url + "?delteam=" + teamName + "&&delusername=" + username;
                }
                // HTTP request
                final RequestQueue requestQueue = Volley.newRequestQueue( SearchResultsTeams.this );

                Log.i("URL", controlURL);
                StringRequest controlRequest = new StringRequest(Request.Method.POST, controlURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Info", "Control sent. Control = " + String.valueOf(control));

                                if (control % 2 == 1) {
                                    controlButton.setText("REMOVE");
                                }
                                else {
                                    controlButton.setText("ADD");
                                }

                                requestQueue.stop();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                requestQueue.stop();
                            }
                        }
                );

                controlRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(controlRequest);
            }
        });
    }
}
