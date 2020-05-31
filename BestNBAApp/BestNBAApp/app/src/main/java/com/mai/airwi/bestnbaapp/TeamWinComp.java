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

public class TeamWinComp extends AppCompatActivity {

    Server server = new Server();
    String server_url = server.getUrl();

    String username;

    TextView scr1;
    TableRow tableRow;
    TableLayout compTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_results);

        username = (String) getIntent().getSerializableExtra("username");

        Log.i("info", "Compare Teams Page");
        Log.i("info", username);

        // must initialize the components that you ae going to create
        scr1 = new TextView(this);

        tableRow = new TableRow(this);
        compTable = (TableLayout)findViewById(R.id.compTable);

        // format columns
        compTable.setColumnStretchable(0, true);
        compTable.setColumnStretchable(1, true);
        compTable.setColumnStretchable(2, true);
        compTable.setColumnStretchable(3, true);

        // do analysis requests
        analyze();
    }

    public void analyze(){
        Log.i("Info", "analyzing user set");
        Log.i("Info", "username " + username);

        // create the URL for the request
        final String compURL = server_url + "?winratio=" + username;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        Log.i("URL", compURL);
        StringRequest analyzeRequest = new StringRequest(Request.Method.POST, compURL,
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

                            addTable(list);
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


    // function to add texts to the table
    void addTable(List<String> list){
        // initialize just in case
        scr1 = new TextView(this);
        tableRow = new TableRow(this);

        for(int index = 0; index < list.size(); index++){
            // create a new text view for each index
            // add it to the row
            // after adding 4 elements to the row, add to the table
            if(index % 4 == 0 & index != 0){
                // add the row to the table
                compTable.addView(tableRow);
                // create a new row
                tableRow = new TableRow(this);

                scr1 = new TextView(this);

                scr1.setText(list.get(index));
                scr1.setGravity(Gravity.CENTER);
                scr1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                scr1.setTextSize(15);

                // put TextViews into the row
                tableRow.addView(scr1);
            }
            else{
                scr1 = new TextView(this);

                scr1.setText(list.get(index));
                scr1.setGravity(Gravity.CENTER);
                scr1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                scr1.setTextSize(15);

                // put TextViews into the row
                tableRow.addView(scr1);

                if(index == list.size() - 1){
                    compTable.addView(tableRow);
                }
            }
        }
    }
}