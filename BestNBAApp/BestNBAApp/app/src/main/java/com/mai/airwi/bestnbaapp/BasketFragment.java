package com.mai.airwi.bestnbaapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by airwi on 4/25/2020.
 */

public class BasketFragment extends Fragment {

    Server server = new Server();
    String server_url = server.getUrl();
    String username;

    Button clearButton;
    Button analyzeButton;
    Switch category;
    TextView scr1;
    TableRow tableRow;
    TableLayout basketTable;

    int searchType = 0;

    List<String> userSet = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        username = getArguments().getString("username");

        Log.i("Info", "Basket Page");
        Log.i("Username", username);

        clearButton = (Button)view.findViewById(R.id.clearButton);
        category = (Switch)view.findViewById(R.id.switchCategory);
        analyzeButton = (Button)view.findViewById(R.id.analyzeButton);
        basketTable = (TableLayout)view.findViewById(R.id.basketTable);

        // format columns
        basketTable.setColumnStretchable(0, true);
        basketTable.setColumnStretchable(1, true);

        refreshDisplay();

        category.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchType = 1;
                    Log.i("Info", "Search type: Teams");
                    refreshDisplay();
                } else {
                    searchType = 0;
                    Log.i("Info", "Search type: Players");
                    refreshDisplay();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Clear button clicked");

                String clearURL = "";
                switch(searchType){
                    case 0:
                        Log.i("Info", "Clearing Player Set...");
                        clearURL = server_url + "?clearplayer=1&&clearusername=" + username;
                    case 1:
                        Log.i("Info", "Clearing Team Set...");
                        clearURL = server_url + "?clearteam=1&&clearusername=" + username;
                }
                final String controlURL = clearURL;

                // HTTP request
                final RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );

                Log.i("ULR", controlURL);
                StringRequest clearRequest = new StringRequest(Request.Method.POST, controlURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Info", "Clear complete.");

                                // deletes rows except for the first which contains headers
                                while (basketTable.getChildCount() > 1){
                                    basketTable.removeView(basketTable.getChildAt(basketTable.getChildCount() - 1));
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

                requestQueue.add(clearRequest);
            }
        });

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Analyze button clicked");
                Log.i("Info", "User set length: " + String.valueOf(userSet.size()));

                ArrayList<String> set = new ArrayList<String>(userSet);

                switch(searchType){
                    case 0:
                        Intent intent = new Intent(BasketFragment.this.getActivity(), PlayerAnalyses.class);
                        intent.putExtra("set", set);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(BasketFragment.this.getActivity(), TeamAnalyses.class);
                        intent2.putExtra("set", set);
                        intent2.putExtra("username", username);
                        startActivity(intent2);
                        break;
                    default:
                        Log.i("Error", "Search type invalid.");
                }
            }
        });

        return view;
    }

    public void refreshDisplay() {
        // clear table except headers
        while (basketTable.getChildCount() > 1){
            basketTable.removeView(basketTable.getChildAt(basketTable.getChildCount() - 1));
        }

        String setURL = "";
        switch(searchType){
            case 0:
                setURL = server_url + "?userset=" + username;
                Log.i("Player Search URL", setURL);
                break;
            case 1:
                setURL = server_url + "?teamuserset=" + username;
                Log.i("Team Search URL", setURL);
                break;
            default:
                Log.i("Error", "Search type invalid.");
        }

        final String refreshURL = setURL;

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest refreshRequest = new StringRequest(Request.Method.POST, refreshURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Info", "Refresh complete.");
                        if(response.equals("empty userset")){
                            return;
                        }
                        else{
                            List<String> list = new ArrayList<String>();
                            list = read(response);

                            userSet = addToTable(list);
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

    // adjusted for needs of the basket page
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

    public List<String> addToTable(List<String> list){
        List<String> nameSet = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            // initialize row elements
            scr1 = new TextView(BasketFragment.this.getActivity());
            tableRow = new TableRow(BasketFragment.this.getActivity());

            String playerName= list.get(i);
            nameSet.add(playerName);

            // format and add texts to the views
            scr1.setText(playerName);
            scr1.setGravity(Gravity.CENTER);
            scr1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            scr1.setTextSize(15);

            // add views into the rows
            tableRow.addView(scr1);

            // add row into the table
            basketTable.addView(tableRow);
        }
        return nameSet;
    }
}

