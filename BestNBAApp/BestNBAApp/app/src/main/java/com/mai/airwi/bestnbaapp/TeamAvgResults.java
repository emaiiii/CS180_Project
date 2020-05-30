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

public class TeamAvgResults extends AppCompatActivity {

    Server server = new Server();
    String server_url = server.getUrl();

    TextView scr1, scr2, scr3, scr4, scr5, scr6, scr7;
    TableRow tableRow;
    TableLayout averageTable;

    List<String> userSet = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_avg_results);

        userSet = (ArrayList<String>) getIntent().getSerializableExtra("set");

        averageTable = (TableLayout) findViewById(R.id.teamAverageTable);

        Log.i("info", "Team AVG Results page");
        Log.i("info", String.valueOf(userSet.size()));

        // format columns
        for(int i = 0; i < 7; ++i){
            averageTable.setColumnStretchable(i, true);
        }

        analyze();
    }

    public void analyze(){
        Log.i("Info", "analyzing user set");
        Log.i("Info", "user set size: " + String.valueOf(userSet.size()));

        // loop through the user set
        for(int index = 0; index < userSet.size(); index++){
            final int finIndex = index;

            // replace the space in the name with %20
            String teamName = userSet.get(index);
            teamName = teamName.replace(" ", "%20");

            // create the URL for the request
            final String avgURL = server_url + "?teamavg=1&&name=" + teamName;
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

        // put TextViews into the row
        tableRow.addView(scr1);
        tableRow.addView(scr2);
        tableRow.addView(scr3);
        tableRow.addView(scr4);
        tableRow.addView(scr5);
        tableRow.addView(scr6);
        tableRow.addView(scr7);

        // put the row into the table
        averageTable.addView(tableRow);
    }
}
