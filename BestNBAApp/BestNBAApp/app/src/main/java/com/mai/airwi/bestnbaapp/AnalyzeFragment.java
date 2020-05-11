package com.mai.airwi.bestnbaapp;

import android.content.Intent;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

    String server_url = "http://58a7402c.ngrok.io/";
    String username = "JimMango";

    String response;
    List<String> list;

    TextView statDisplay;

    Button nextButton;
    Button prevButton;
    Button refreshButton;

    int pos = 0; // POSITION of stat view in set

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyze, container, false);

        statDisplay = (TextView)view.findViewById((R.id.statDisplay));
        nextButton = (Button)view.findViewById(R.id.nextButton);
        prevButton = (Button) view.findViewById(R.id.prevButton);
        refreshButton = (Button) view.findViewById(R.id.refresh);

        // make init request: userset is at 0 or at null
        // if at any point null, redirect text to say no items in set
        // if 0, load data for 0 averages
        // next sends up (if at max send back to 0) --> CIRCULAR
        // prev is like next in reverse --> CIRCULAR

        String playerName = ""; // FIXME: fxn to get player name @ pos
        final String statURL = server_url + "?playername=" + playerName + "&&username=" + username;

        // HTTP request
        //
        final RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );

        Log.i("ULR", statURL);
        StringRequest statRequest = new StringRequest(Request.Method.POST, statURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Info", "Request complete.");

                        if (response.equals("no player found")) {
                            statDisplay.setText("no player found");
                        }
                        else {
                            List<String> list = new ArrayList<String>();
                            String toDisplay;

                            list = read(response);

                            // code for team display
                            Team teamData = new Team(list);

                            // FIXME: parse details to display
                            toDisplay = "Team ID: " + teamData.getTeamID();
                            toDisplay = toDisplay + "\nMin. Year: " + teamData.getMinYear();
                            toDisplay = toDisplay + "\nMax. Year: " + teamData.getMaxYear();

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
        //

        requestQueue.add(statRequest);

        // FIXME: load data from intent
        //response = getIntent().getExtras().getString("response");

        /*
        // encapsulate data into object
        list = new ArrayList<String>();
        list = read(response);
        Player player = new Player(list);




*/

        return view;
    }
}
