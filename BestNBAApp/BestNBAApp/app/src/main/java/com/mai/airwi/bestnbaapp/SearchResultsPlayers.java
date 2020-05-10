package com.mai.airwi.bestnbaapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPlayers extends AppCompatActivity {

    String response;
    List<String> list;

    TextView scr1, scr2, scr3, scr4;
    TableLayout playerTable;
    TableRow tableRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_players);

        // must initialize the components that you ae going to create
        scr1 = new TextView(this);
        scr2 = new TextView(this);
        scr3 = new TextView(this);
        scr4 = new TextView(this);

        tableRow = new TableRow(this);
        playerTable = (TableLayout)findViewById(R.id.PlayerSearchTable);

        // format columns
        playerTable.setColumnStretchable(0, true);
        playerTable.setColumnStretchable(1, true);
        playerTable.setColumnStretchable(2, true);
        playerTable.setColumnStretchable(3, true);

        // load data from intent
        response = getIntent().getExtras().getString("response");
        list = new ArrayList<String>();

        // encapsulate data into object
        list = read(response);

        Player player = new Player(list);
        String name,season,tid,pid;

        name = player.getPlayerName();
        tid = Integer.toString(player.getTeamID());
        pid = Integer.toString(player.getPlayerID());
        season = Integer.toString(player.getSeason());


        Log.i("info", name);
        Log.i("info", tid);
        Log.i("info", pid);
        Log.i("info", season);

        // put texts into TextView
        scr1.setText(name);
        scr1.setGravity(Gravity.CENTER);
        scr1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr1.setTextSize(15);

        scr2.setText(tid);
        scr2.setGravity(Gravity.CENTER);
        scr2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr2.setTextSize(15);

        scr3.setText(pid);
        scr3.setGravity(Gravity.CENTER);
        scr3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr3.setTextSize(15);

        scr4.setText(season);
        scr4.setGravity(Gravity.CENTER);
        scr4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        scr4.setTextSize(15);

        // put TextViews into the row
        tableRow.addView(scr1);
        tableRow.addView(scr2);
        tableRow.addView(scr3);
        tableRow.addView(scr4);

        // put the row into the table
        playerTable.addView(tableRow);
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
