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

import static com.mai.airwi.bestnbaapp.SearchFragment.read;
import static java.lang.Math.round;

public class PlayerAvgResults extends AppCompatActivity {

    String server_url = "http://cb97b1d3.ngrok.io/";

    String username = "test";

    TextView scr1, scr2, scr3, scr4, scr5, scr6, scr7, scr8, scr9, scr10, scr11, scr12, scr13, scr14, scr15, scr16, scr17, scr18, scr19;
    TableRow tableRow;
    TableLayout averageTable;

    List<String> userSet = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_avg_results);

        userSet = (ArrayList<String>) getIntent().getSerializableExtra("set");

        // FGM    FGA    FG_PCT    FG3M    FG3A    FG3_PCT    FTM    FTA    FT_PCT    OREB    DREB    REB    AST    STL    BLK    TO    PF    PTS

        Log.i("info", "AVG Results page");
        Log.i("info", String.valueOf(userSet.size()));
        Log.i("info", userSet.get(0));
        Log.i("info", userSet.get(1));

        // must initialize the components that you ae going to create
        scr1 = new TextView(this);
        scr2 = new TextView(this);
        scr3 = new TextView(this);
        scr4 = new TextView(this);
        scr5 = new TextView(this);
        scr6 = new TextView(this);
        scr7 = new TextView(this);
        scr8 = new TextView(this);
        scr9 = new TextView(this);
        scr10 = new TextView(this);
        scr11 = new TextView(this);
        scr12 = new TextView(this);
        scr13 = new TextView(this);
        scr14 = new TextView(this);
        scr15 = new TextView(this);
        scr16 = new TextView(this);
        scr17 = new TextView(this);
        scr18 = new TextView(this);
        scr19 = new TextView(this);

        tableRow = new TableRow(this);
        averageTable = (TableLayout)findViewById(R.id.ratingsTable);

        // format columns
        for(int i = 0; i < 19; ++i){
            averageTable.setColumnStretchable(i, true);
        }

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
            final String avgURL = server_url + "?playeravg=1&&name=" + playerName;
            final RequestQueue requestQueue = Volley.newRequestQueue(this);

            Log.i("URL", avgURL);
            StringRequest analyzeRequest = new StringRequest(Request.Method.POST, avgURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Info", "Analyze complete");

                            if(response.equals("empty userset")){
                                return;
                            }
                            else{
                                // hardcode
                                //response = "[\"8.54\",\"19.25\",\"0.41\",\"1.48\",\"4.53\",\"0.26\",\"6.20\",\"7.38\",\"0.75\",\"0.95\",\"3.97\",\"4.92\",\"4.57\",\"1.35\",\"0.36\",\"2.90\",\"2.25\",\"24.76\"]";
                                List<String> list = new ArrayList<String>();
                                list = read(response);

                                addTable(userSet, list, finIndex);
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

    void addTable(List<String> userSet, List<String> attributes, int index){
        scr1 = new TextView(this);
        scr2 = new TextView(this);
        scr3 = new TextView(this);
        scr4 = new TextView(this);
        scr5 = new TextView(this);
        scr6 = new TextView(this);
        scr7 = new TextView(this);
        scr8 = new TextView(this);
        scr9 = new TextView(this);
        scr10 = new TextView(this);
        scr11 = new TextView(this);
        scr12 = new TextView(this);
        scr13 = new TextView(this);
        scr14 = new TextView(this);
        scr15 = new TextView(this);
        scr16 = new TextView(this);
        scr17 = new TextView(this);
        scr18 = new TextView(this);
        scr19 = new TextView(this);
        
        tableRow = new TableRow(this);

        // format and add texts to the views
        // get name
        scr1.setText(userSet.get(index));
        scr1.setGravity(Gravity.CENTER);
        scr1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr1.setTextSize(15);

        // get stats
        scr2.setText(attributes.get(0));
        scr2.setGravity(Gravity.CENTER);
        scr2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr2.setTextSize(15);

        scr3.setText(attributes.get(1));
        scr3.setGravity(Gravity.CENTER);
        scr3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr3.setTextSize(15);

        scr4.setText(attributes.get(2));
        scr4.setGravity(Gravity.CENTER);
        scr4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr4.setTextSize(15);

        scr5.setText(attributes.get(3));
        scr5.setGravity(Gravity.CENTER);
        scr5.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr5.setTextSize(15);

        scr6.setText(attributes.get(4));
        scr6.setGravity(Gravity.CENTER);
        scr6.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr6.setTextSize(15);

        scr7.setText(attributes.get(5));
        scr7.setGravity(Gravity.CENTER);
        scr7.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr7.setTextSize(15);

        scr8.setText(attributes.get(6));
        scr8.setGravity(Gravity.CENTER);
        scr8.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr8.setTextSize(15);

        scr9.setText(attributes.get(7));
        scr9.setGravity(Gravity.CENTER);
        scr9.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr9.setTextSize(15);

        scr10.setText(attributes.get(8));
        scr10.setGravity(Gravity.CENTER);
        scr10.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr10.setTextSize(15);

        scr11.setText(attributes.get(9));
        scr11.setGravity(Gravity.CENTER);
        scr11.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr11.setTextSize(15);

        scr12.setText(attributes.get(10));
        scr12.setGravity(Gravity.CENTER);
        scr12.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr12.setTextSize(15);

        scr13.setText(attributes.get(11));
        scr13.setGravity(Gravity.CENTER);
        scr13.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr13.setTextSize(15);

        scr14.setText(attributes.get(12));
        scr14.setGravity(Gravity.CENTER);
        scr14.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr14.setTextSize(15);

        scr15.setText(attributes.get(13));
        scr15.setGravity(Gravity.CENTER);
        scr15.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr15.setTextSize(15);

        scr16.setText(attributes.get(14));
        scr16.setGravity(Gravity.CENTER);
        scr16.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr16.setTextSize(15);

        scr17.setText(attributes.get(15));
        scr17.setGravity(Gravity.CENTER);
        scr17.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr17.setTextSize(15);

        scr18.setText(attributes.get(16));
        scr18.setGravity(Gravity.CENTER);
        scr18.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr18.setTextSize(15);

        scr19.setText(attributes.get(17));
        scr19.setGravity(Gravity.CENTER);
        scr19.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr19.setTextSize(15);
        
        // put TextViews into the row
        tableRow.addView(scr1);
        tableRow.addView(scr2);
        tableRow.addView(scr3);
        tableRow.addView(scr4);
        tableRow.addView(scr5);
        tableRow.addView(scr6);
        tableRow.addView(scr7);
        tableRow.addView(scr8);
        tableRow.addView(scr9);
        tableRow.addView(scr10);
        tableRow.addView(scr11);
        tableRow.addView(scr12);
        tableRow.addView(scr13);
        tableRow.addView(scr14);
        tableRow.addView(scr15);
        tableRow.addView(scr16);
        tableRow.addView(scr17);
        tableRow.addView(scr18);
        tableRow.addView(scr19);
        
        // put the row into the table
        averageTable.addView(tableRow);
    }
}
