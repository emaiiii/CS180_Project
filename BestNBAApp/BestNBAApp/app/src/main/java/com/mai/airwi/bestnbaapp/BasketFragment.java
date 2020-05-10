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

    String server_url = "http://1006f878.ngrok.io/";
    String username = "JimMango";

    Button clearButton;
    Button analyzeButton;
    TextView setDisplay;
    TextView statusDisplay;

    //List<Games> userSet = new ArrayList<Games>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        clearButton = (Button)view.findViewById(R.id.clearButton);
        analyzeButton = (Button)view.findViewById(R.id.analyzeButton);
        setDisplay = (TextView)view.findViewById(R.id.setDisplay);
        statusDisplay = (TextView)view.findViewById(R.id.statusText);

        setDisplay.setMovementMethod(new ScrollingMovementMethod());

        // FIXME: add setDisplay refresh

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Clear button clicked");

                final String controlURL = server_url + "clearset=1&&clearusername=" + username;

                // HTTP request
                final RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );

                StringRequest clearRequest = new StringRequest(Request.Method.POST, controlURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Info", "Clear complete.");
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

                statusDisplay.setText("Set cleared.");
                setDisplay.setText("You have no items in your set.");
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

