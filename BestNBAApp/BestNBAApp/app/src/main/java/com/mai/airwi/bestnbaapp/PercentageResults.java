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

import static com.mai.airwi.bestnbaapp.BasketFragment.read;

public class PercentageResults extends AppCompatActivity {

    String server_url = "http://704f9b8e.ngrok.io/";

    TextView scr1, scr2, scr3;
    TableRow tableRow;
    TableLayout percTable;

    List<String> userSet = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_results);

        userSet = (ArrayList<String>) getIntent().getSerializableExtra("set");

        Log.i("info", "Seasons Results Page");
        Log.i("info", String.valueOf(userSet.size()));

        // must initialize the components that you ae going to create
        scr1 = new TextView(this);
        scr2 = new TextView(this);
        scr3 = new TextView(this);

        tableRow = new TableRow(this);
        percTable = (TableLayout)findViewById(R.id.percTable);

        // format columns
        percTable.setColumnStretchable(0, true);
        percTable.setColumnStretchable(1, true);
        percTable.setColumnStretchable(2, true);

        // do analysis requests
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
            final String percURL = server_url + "?teamratio=" + teamName;
            final RequestQueue requestQueue = Volley.newRequestQueue(this);

            Log.i("URL", percURL);
            StringRequest analyzeRequest = new StringRequest(Request.Method.POST, percURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Info", "Analyze complete");

                            if(response.equals("empty userset")){
                                return;
                            }
                            else{
                                // list contains the data
                                List<String> list = new ArrayList<String>();
                                list = read(response);

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

    // function to add texts to the table
    void addTable(List<String> list, List<String> userSet, int index){
        scr1 = new TextView(this);
        scr2 = new TextView(this);
        scr3 = new TextView(this);

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

        scr3.setText(list.get(1));
        scr3.setGravity(Gravity.CENTER);
        scr3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr3.setTextSize(15);

        // put TextViews into the row
        tableRow.addView(scr1);
        tableRow.addView(scr2);
        tableRow.addView(scr3);

        // put the row into the table
        percTable.addView(tableRow);
    }
}
