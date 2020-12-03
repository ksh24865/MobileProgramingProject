package com.cookandroid.mainui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Object;

public class MainActivity extends AppCompatActivity {

    public static Node NowRegion;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap<Region, Node> NodeMap = new HashMap<Region, Node>();
        String Now = "동작구"; //임시 현재위치, 동작구
        /*
        Thread thread = new Thread() {
            HashMap<Region, Node> NodeMap = new HashMap<Region, Node>();
            public void run() {
                //Log.d("log: ","runlog");
                //System.out.println("Thread Running");

                NodeMap = updateMap();
                //printMap(NodeMap);
            }
            public HashMap<Region, Node> GetNodeMap(){
                return NodeMap;
            }
        };
        */
        /*
        LoadNodeMap a = new LoadNodeMap();
        a.start();
        NodeMap = a.GetNodeMap();

        NowRegion = NodeMap.get(Now);
        */
        NowRegion = new Node(Now,1.2,31,18,10,"2020-12-03T13:52:32"); //임시 현재위치;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_events, R.id.nav_new_event,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public static Region searchRegion(String region){
        if (region.equals("seocho")){
            return Region.SEOCHO;
        } else if (region.equals("dongjak") ){
            return Region.DONGJAK;
        } else if (region.equals( "guro")){
            return Region.GURO;
        } else if (region.equals("gangnam")){
            return Region.GANGNAM;
        } else if (region.equals("gangdong")){
            return Region.GANGDONG;
        } else if (region.equals("songpa")){
            return Region.SONGPA;
        } else {
            return null;
        }
    }

}
