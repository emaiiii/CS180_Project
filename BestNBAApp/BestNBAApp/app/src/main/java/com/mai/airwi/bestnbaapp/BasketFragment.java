package com.mai.airwi.bestnbaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import static com.mai.airwi.bestnbaapp.SearchFragment.splitRead;

/**
 * Created by airwi on 4/25/2020.
 */

public class BasketFragment extends Fragment {

    String server_url = "http://fe84b804.ngrok.io/";

    Button addButton;
    Button clearButton;
    Button deleteButton;
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
        deleteButton = (Button)view.findViewById(R.id.deleteButton);
        analyzeButton = (Button)view.findViewById(R.id.analyzeButton);
        setDisplay = (TextView)view.findViewById(R.id.setDisplay);
        statusDisplay = (TextView)view.findViewById(R.id.statusText);
        getGameID = (EditText)view.findViewById(R.id.getGameID);

        setDisplay.setMovementMethod(new ScrollingMovementMethod());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Add button clicked");

                String query = getGameID.getText().toString();

                if (query.isEmpty()) {
                    statusDisplay.setText("Invalid entry.");
                }
                else {
                    final String gameSearchURL = server_url + "?gameid=" + query; // "URL/?gameid=xxxxxx"

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

                                        // clear the TextView before printing all games again
                                        setDisplay.setText(" ");
                                        for(int i = 0; i < userSet.size(); ++i) {
                                            currentDisplay = setDisplay.getText().toString();
                                            userSet.get(i).print(setDisplay, currentDisplay);
                                        }
                                        statusDisplay.setText("Game added.");
                                    }

                                    requestQueue.stop();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    statusDisplay.setText("HTTP Error.");
                                    setDisplay.setText(gameSearchURL);
                                    error.printStackTrace();
                                    requestQueue.stop();
                                }
                            }
                    );

                    gameRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    requestQueue.add(gameRequest);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Clear button clicked");

                if(!userSet.isEmpty()) {
                    userSet = new ArrayList<Games>();
                }

                statusDisplay.setText("Set cleared.");
                setDisplay.setText("You have no games in your set.");

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Delete button clicked");

                if (userSet.isEmpty()) {
                    statusDisplay.setText("Set is empty.");
                }
                else {
                    String query = getGameID.getText().toString();
                    int searchID = Integer.parseInt(query);
                    int removeIndex;

                    for(removeIndex = 0; removeIndex < userSet.size(); ++removeIndex) {
                        if(searchID == userSet.get(removeIndex).getGame_id()) {
                            break;
                        }
                    }

                    if( removeIndex == userSet.size() ){
                        statusDisplay.setText("Game not found.");
                    }
                    else {
                        String currentDisplay;
                        userSet.remove(removeIndex);
                        statusDisplay.setText("Game deleted.");

                        // clear the TextView before printing all games again
                        setDisplay.setText(" ");
                        for(int i = 0; i < userSet.size(); ++i) {
                            currentDisplay = setDisplay.getText().toString();
                            userSet.get(i).print(setDisplay, currentDisplay);
                        }
                    }
                }

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

    public static List<String> read(String result){
        List<String> set = new ArrayList<String>();
        int tempIndex = 1;
        String tempString;

        for(int index = 0; index < result.length(); index++){
            if(result.charAt(index) == ',' || index == result.length() - 1){
                tempString = result.substring(tempIndex, index);

                if(tempString.charAt(0)== '\"' && tempString.charAt(tempString.length() - 1) == '\"'){
                    tempString = tempString.substring(1, tempString.length() - 1);
                }

                set.add(tempString);
                tempIndex = index + 1;
            }
        }

        return set;
    }

    public static List<List<String>> splitRead(List<String>results){
        List<List<String>> set = new ArrayList<List<String>>();
        List<String> tempList = new ArrayList<String>();

        for(int index = 0; index < results.size(); index++){
            String tempString = results.get(index);

            if(tempString.charAt(0) == '['){
                tempString = tempString.substring(1, tempString.length() - 1);
                //System.out.println(tempString);
                tempList.add(tempString);
            }
            else if(tempString.charAt(tempString.length() - 1) == ']'){
                tempString = tempString.substring(0, tempString.length() - 1);
                tempList.add(tempString);
                set.add(tempList);

                tempList = new ArrayList<String>();
            }
            else{
                tempList.add(tempString);
            }
        }

        return set;
    }
}
