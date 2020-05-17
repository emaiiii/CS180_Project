package com.mai.airwi.bestnbaapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import static java.lang.Math.round;

public class PlayerRatingResults extends AppCompatActivity {

    String server_url = "http://cb97b1d3.ngrok.io/";

    String username = "test";

    TextView scr1, scr2;
    TableRow tableRow;
    TableLayout ratingsTable;

    List<String> userSet = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_rating_results);

        userSet = (ArrayList<String>) getIntent().getSerializableExtra("set");

        Log.i("info", "Rating Results page");
        Log.i("info", String.valueOf(userSet.size()));
        Log.i("info", userSet.get(0));
        Log.i("info", userSet.get(1));

        // must initialize the components that you ae going to create
        scr1 = new TextView(this);
        scr2 = new TextView(this);

        tableRow = new TableRow(this);
        ratingsTable = (TableLayout)findViewById(R.id.ratingsTable);

        // format columns
        ratingsTable.setColumnStretchable(0, true);
        ratingsTable.setColumnStretchable(1, true);

        // get the user set based on the username

        analyze();
    }

    public void analyze(){
        Log.i("Info", "analyzing user set");
        Log.i("Info", "user set size: " + String.valueOf(userSet.size()));

        // loop through the user set
        for(int index = 0; index < userSet.size(); index++){
            final int finIndex = index;

            // replace the space in the name with %20
            String playerName = userSet.get(index);
            playerName = playerName.replace(" ", "%20");

            // create the URL for the request
            final String ratingUrl = server_url + "?playerrating=" + playerName;
            final RequestQueue requestQueue = Volley.newRequestQueue(this);

            Log.i("URL", ratingUrl);
            StringRequest analyzeRequest = new StringRequest(Request.Method.POST, ratingUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Info", "Analyze complete");

                            if(response.equals("empty userset")){
                                return;
                            }
                            else{
                                // list contains the ranking
                                List<String> list = new ArrayList<String>();
                                double num = Double.parseDouble(response);
                                num = round(num);

                                response = String.valueOf(num);

                                list.add(response);

                                addTable(list, userSet, finIndex);
                            }
                            requestQueue.stop();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Info", "Refresh error.");
                            error.printStackTrace();
                            requestQueue.stop();
                        }
                    }
            );

            analyzeRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(analyzeRequest);
        }
    }

    /*public List<String> pullSet() {

        final String refreshURL = server_url + "?userset=" + username;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final List<String>[] set = new List<String>[1];

        Log.i("URL", refreshURL);
        StringRequest refreshRequest = new StringRequest(Request.Method.POST, refreshURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Info", "Pull complete.");
                        // FIXME: parse json to table layout
                        if(response.equals("empty userset")){
                            return;
                        }
                        else{
                            List<String> list = new ArrayList<String>();
                            list = read(response);
                            set[0] = list;

                            Log.i("Info", "user set size: " + String.valueOf(userSet.size()));
                        }
                        requestQueue.stop();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Info", "Refresh error.");
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                }
        );

        requestQueue.add(refreshRequest);

        return set;
    }*/

    void addTable(List<String> list, List<String> userSet, int index){
        scr1 = new TextView(this);
        scr2 = new TextView(this);
        tableRow = new TableRow(this);

        // format and add texts to the views
        // add names
        scr1.setText(userSet.get(index));
        scr1.setGravity(Gravity.CENTER);
        scr1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr1.setTextSize(15);

        // add ratings
        scr2.setText(list.get(0));
        scr2.setGravity(Gravity.CENTER);
        scr2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr2.setTextSize(15);

        // put TextViews into the row
        tableRow.addView(scr1);
        tableRow.addView(scr2);

        // put the row into the table
        ratingsTable.addView(tableRow);
    }
}

