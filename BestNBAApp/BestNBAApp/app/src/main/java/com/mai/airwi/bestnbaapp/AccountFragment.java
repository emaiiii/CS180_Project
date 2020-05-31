package com.mai.airwi.bestnbaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.*;

import static com.mai.airwi.bestnbaapp.SearchFragment.read;

public class AccountFragment extends Fragment {

    Server server = new Server();
    String server_url = server.getUrl();

    String username = "test";

    String response;

    TextView statDisplay;

    Button nextButton;
    Button prevButton;
    Button refreshButton;

    int pos = 0; // POSITION of stat view in set
    List<String> userSet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        statDisplay = (TextView)view.findViewById((R.id.statDisplay));
        nextButton = (Button)view.findViewById(R.id.nextButton);
        prevButton = (Button) view.findViewById(R.id.prevButton);
        refreshButton = (Button) view.findViewById(R.id.refresh);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Next button clicked");

                pos += 1;

                if (pos < userSet.size() && pos >= 0) {
                    String playerName = userSet.get(pos);
                    playerName = playerName.replace(" ", "%20");

                    //final String statURL = server_url + "?playername=" + playerName + "&&username=" + username;
                    final String statURL = server_url + "?playeravg=1&&name=" + playerName;

                    //?playeravg=1&&name=kobe%20bryant
                    // HTTP request
                    //
                    final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    Log.i("ULR", statURL);
                    StringRequest statRequest = new StringRequest(Request.Method.POST, statURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Info", "Init request complete.");

                                    if (response.equals("no player found")) {
                                        statDisplay.setText("no player found");
                                    } else {
                                        List<String> list = new ArrayList<String>();
                                        String toDisplay = "POSITION: " + pos + "\nRESPONSE:\n:" + response;

                                        statDisplay.setText(toDisplay);
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

                    statRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    requestQueue.add(statRequest);
                    //
                }
                else {
                    statDisplay.setText("No access:: RESET TO BEGIN");
                    pos = 0;
                }

            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Prev button clicked");

                //String get = userSet.get(0);
                //get = get.replace(" ", "%20");
                //statDisplay.setText(get);

                pos -= 1;

                if (pos < userSet.size() && pos >= 0) {
                    String playerName = userSet.get(pos);
                    playerName = playerName.replace(" ", "%20");

                    //final String statURL = server_url + "?playername=" + playerName + "&&username=" + username;
                    final String statURL = server_url + "?playeravg=1&&name=" + playerName;

                    //?playeravg=1&&name=kobe%20bryant
                    // HTTP request
                    //
                    final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    Log.i("ULR", statURL);
                    StringRequest statRequest = new StringRequest(Request.Method.POST, statURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Info", "Init request complete.");

                                    if (response.equals("no player found")) {
                                        statDisplay.setText("no player found");
                                    } else {
                                        List<String> list = new ArrayList<String>();
                                        String toDisplay = "POSITION: " + pos + "\nRESPONSE:\n:" + response;

                                        statDisplay.setText(toDisplay);
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

                    statRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    requestQueue.add(statRequest);
                    //
                }
                else {
                    statDisplay.setText("No access:: RESET TO BEGIN");
                    pos = 0;
                }
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Refresh button clicked");

                if (pos < userSet.size() ) {
                    String playerName = userSet.get(pos);
                    playerName = playerName.replace(" ", "%20");

                    //final String statURL = server_url + "?playername=" + playerName + "&&username=" + username;
                    final String statURL = server_url + "?playeravg=1&&name=" + playerName;

                    // HTTP request
                    //
                    final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    Log.i("ULR", statURL);
                    StringRequest statRequest = new StringRequest(Request.Method.POST, statURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Info", "Init request complete.");

                                    if (response.equals("no player found")) {
                                        statDisplay.setText("no player found");
                                    } else {
                                        List<String> list = new ArrayList<String>();
                                        String toDisplay = "POSITION: " + pos + "\nRESPONSE:\n:" + response;

                                        statDisplay.setText(toDisplay);
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
                    statRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(statRequest);
                    //
                }
                else {
                    statDisplay.setText("No access:: RESET TO BEGIN");
                    pos = 0;
                }
            }
        });

        pullSet();

        return view;
    }

    public void pullSet() {
        final String refreshURL = server_url + "?userset=" + username;
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Log.i("URL", refreshURL);
        StringRequest refreshRequest = new StringRequest(Request.Method.POST, refreshURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Info", "PULL complete.");
                        // FIXME: parse json to table layout
                        if(response.equals("empty userset")){
                            return;
                        }
                        else{
                            List<String> list = new ArrayList<String>();
                            list = read(response);
                            userSet = list;
                            //addToTable(list);
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
    }

}
