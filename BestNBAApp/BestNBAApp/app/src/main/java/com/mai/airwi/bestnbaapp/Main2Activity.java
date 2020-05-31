package com.mai.airwi.bestnbaapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class Main2Activity extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // load the user name
        username = (String)getIntent().getSerializableExtra("username");
        Log.i("Info", "main screen");
        Log.i("Username", username);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SearchFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);

                    switch(item.getItemId()){
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                        case R.id.nav_basket:
                            selectedFragment = new BasketFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                        case R.id.nav_analyze:
                            selectedFragment = new StatFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
