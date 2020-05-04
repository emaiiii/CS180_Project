package com.mai.airwi.bestnbaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import static com.mai.airwi.bestnbaapp.SearchFragment.read;

/*
 * Created by airwi on 4/25/2020.
 */

public class AnalyzeFragment extends Fragment {

    String server_url = "http://a45dcfe8.ngrok.io/";
    TextView setDisplay;
    EditText searchEditText;
    Button analyzeButton;
    Switch category;
    int analyzeType = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyze, container, false);

        analyzeButton = (Button)view.findViewById(R.id.analyzeButton);
        setDisplay = (TextView)view.findViewById(R.id.setDisplay);
        searchEditText = (EditText)view.findViewById(R.id.searchEditText);
        category = (Switch)view.findViewById(R.id.switchCategory);

        category.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //team search
                    analyzeType = 1;
                } else {
                    //team search
                    analyzeType = 0;
                }
            }
        });

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Button clicked");

                String query = searchEditText.getText().toString();

                try {
                    query = URLEncoder.encode(query,"UTF-8");
                }
                catch (IOException e) {
                    // Encoding Error
                }

                query = query.replace("%2B","%20");

                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                switch(analyzeType) {
                    case 0: // Player Search
                        final String playerSearchURL = server_url + "?playeravg=1&&name=" + query;

                        Log.i("URL", playerSearchURL);
                        StringRequest playerRequest = new StringRequest(Request.Method.POST, playerSearchURL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Info", "Successful connection");

                                        if (response.equals("no player info found")) {
                                            setDisplay.setText("no player info. found");
                                        }
                                        else {
                                            List<String> list = new ArrayList<String>();
                                            String toDisplay;

                                            list = read(response);

                                            toDisplay = "Average FGM: " + list.get(0);
                                            toDisplay = toDisplay + "\nAverage FGA: " + list.get(1);
                                            toDisplay = toDisplay + "\nAverage FG%: " + list.get(2);
                                            toDisplay = toDisplay + "\nAverage 3FGM: " + list.get(3);
                                            toDisplay = toDisplay + "\nAverage 3FGA: " + list.get(4);
                                            toDisplay = toDisplay + "\nAverage 3FG%: " + list.get(5);
                                            toDisplay = toDisplay + "\nAverage FTM: " + list.get(6);
                                            toDisplay = toDisplay + "\nAverage FTA: " + list.get(7);
                                            toDisplay = toDisplay + "\nAverage FT%: " + list.get(8);
                                            toDisplay = toDisplay + "\nAverage OREB: " + list.get(9);
                                            toDisplay = toDisplay + "\nAverage DREB: " + list.get(10);
                                            toDisplay = toDisplay + "\nAverage REB: " + list.get(11);
                                            toDisplay = toDisplay + "\nAverage AST: " + list.get(12);
                                            toDisplay = toDisplay + "\nAverage STL: " + list.get(13);
                                            toDisplay = toDisplay + "\nAverage BLK: " + list.get(14);
                                            toDisplay = toDisplay + "\nAverage TO: " + list.get(15);
                                            toDisplay = toDisplay + "\nAverage PF: " + list.get(16);
                                            toDisplay = toDisplay + "\nAverage PTS: " + list.get(17);
                                            //toDisplay = toDisplay + "\nAverage PLUS MINUS: " + list.get(18);
                                            setDisplay.setText(toDisplay);
                                        }

                                        requestQueue.stop();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        setDisplay.setText("Search Error: No response from server." + "\nRequest:\n" + playerSearchURL);
                                        error.printStackTrace();
                                        requestQueue.stop();
                                    }
                                }
                        );
                        playerRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        requestQueue.add(playerRequest);

                        break;

                    case 1: //team search
                        final String teamSearchURL = server_url + "?teamavg=1&&name=" + query;

                        Log.i("URL", teamSearchURL);
                        StringRequest teamRequest = new StringRequest(Request.Method.POST, teamSearchURL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Info", "Successful connection");


                                        if (response.equals("no team found")) {
                                            setDisplay.setText("no team found");
                                        }
                                        else {
                                            List<String> list = new ArrayList<String>();
                                            String toDisplay;

                                            list = read(response);

                                            toDisplay = "Average PPG: " + list.get(0);
                                            toDisplay = toDisplay + "\nAverage FG%: " + list.get(1);
                                            toDisplay = toDisplay + "\nAverage FT%: " + list.get(2);
                                            toDisplay = toDisplay + "\nAverage 3FG%: " + list.get(3);
                                            toDisplay = toDisplay + "\nAverage AST: " + list.get(4);
                                            toDisplay = toDisplay + "\nAverage REB: " + list.get(5);

                                            setDisplay.setText(toDisplay);

                                        }

                                        requestQueue.stop();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        setDisplay.setText("Search Error: No response from server." + "\nRequest:\n" + teamSearchURL);
                                        error.printStackTrace();
                                        requestQueue.stop();
                                    }
                                });

                        requestQueue.add(teamRequest);

                        break;

                    default:
                        setDisplay.setText("ERROR: invalid selection");
                        requestQueue.stop();
                }
            }
        });

        return view;
    }
}
