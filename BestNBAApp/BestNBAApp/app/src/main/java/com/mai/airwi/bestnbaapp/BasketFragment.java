package com.mai.airwi.bestnbaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import static com.mai.airwi.bestnbaapp.SearchFragment.read;

/**
 * Created by airwi on 4/25/2020.
 */

public class BasketFragment extends Fragment {

    String server_url = "http://658994b8.ngrok.io";
    // FIXME: IMPLEMENT SERVER URL GLOBALLY

    Button addButton;
    Button clearButton;
    Button analyzeButton;
    TextView setDisplay;
    TextView statusDisplay;
    EditText getGameID;
    List<Games> userSet = new ArrayList<Games>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        addButton = (Button)view.findViewById(R.id.addButton);
        clearButton = (Button)view.findViewById(R.id.clearButton);
        analyzeButton = (Button)view.findViewById(R.id.analyzeButton);
        setDisplay = (TextView)view.findViewById(R.id.setDisplay);
        statusDisplay = (TextView)view.findViewById(R.id.statusText);
        getGameID = (EditText)view.findViewById(R.id.getGameID);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Add button clicked");

                String query = getGameID.getText().toString();

                if (query.isEmpty()) {
                    statusDisplay.setText("Invalid entry.");
                }
                else {
                    final String gameSearchURL = server_url + "?gameid="; // "URL/?gameid=xxxxxx"

                    final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    StringRequest gameRequest = new StringRequest(Request.Method.POST, gameSearchURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Info", "Successful connection");

                                    if (response.equals("no game found")) {
                                        statusDisplay.setText("Error: Game not found.");
                                    } else {
                                        List<String> list = new ArrayList<String>();
                                        String toDisplay;
                                        String currentDisplay;

                                        list = read(response);
                                        Games game = new Games(list);
                                        userSet.add(game);

                                        for(int i = 0; i < userSet.size(); ++i) {
                                            currentDisplay = setDisplay.getText().toString();
                                            userSet.get(i).print(setDisplay, currentDisplay);
                                        }
                                    }

                                    requestQueue.stop();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    statusDisplay.setText("Error: No server response.");
                                    error.printStackTrace();
                                    requestQueue.stop();
                                }
                            }
                    );

                    requestQueue.add(gameRequest);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Clear button clicked");

                if(!userSet.isEmpty()) { userSet = new ArrayList<Games>(); }

                // FIXME: CLEAR SERVER-SIDE SET (LEAVE FOR NEXT SPRINT)
                /*final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                // request here
                requestQueue.stop();*/

                statusDisplay.setText("Set cleared.");
                setDisplay.setText("You have no games in your set.");

            }
        });

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Analyze button clicked");

                statusDisplay.setText("Analyze pending implementation.");

            }
        });


        return view;
    }
}

