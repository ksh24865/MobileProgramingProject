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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread thread = new Thread() {
            public void run() {
                //Log.d("log: ","runlog");
                //System.out.println("Thread Running");
                HashMap<Region, Node> NodeMap = new HashMap<Region, Node>();
                NodeMap = updateMap();
                //printMap(NodeMap);
            }
        };
        thread.start();


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
    public static HashMap<Region, Node>updateMap(){
        JSONArray ja = null;
        Log.d("log: ","templog0");
        HashMap<Region, Node> NodeMap = new HashMap<Region, Node>();
        try {
            URL aURL= new URL("http://10.5.110.31:9200/toiot-temp/_search?q=timestamp=%222020-11-25T13:52:32%22");
            URLConnection uc = aURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine =in.readLine();
            inputLine = inputLine.substring(155, inputLine.length()-2);
            ja = new JSONArray(inputLine);
            Log.d("ja길이 : ", Integer.toString(ja.length()));
            for (int i = 0; i < ja.length(); i++){
                JSONObject json_data = ja.getJSONObject(i);
                JSONObject source_data = (JSONObject)json_data.get("_source");
                JSONObject node_data = (JSONObject)source_data.get("node");
                JSONObject values_data = (JSONObject)source_data.get("values");
                String timestamp_data = (String)source_data.get("timestamp");
                String region_data = (String)node_data.get("sink_name");
                Object obj = values_data.get("기온");
                double temper_data;
                if(obj instanceof Integer)
                    temper_data = Double.valueOf((int)obj);
                else
                    temper_data = (double)obj;
                int dust_data = (int)values_data.get("미세먼지");
                int ddust_data = (int)values_data.get("초미세먼지");
                int humid_data = (int)values_data.get("상대습도");
                //Log.d("log: ",values_data.toString()+", "+region_data+","+timestamp_data+", "+Double.toString(temper_data));
                Node tmpNode = new Node(searchRegion(region_data),temper_data,dust_data,ddust_data,humid_data,timestamp_data);
                NodeMap.put(tmpNode.region,tmpNode);
                tmpNode = null;
            }
            in.close();
            return NodeMap;
        } catch (IOException e) {
            System.out.println("URL에서 데이터를 읽는 중 오류가 발생 했습니다.");
        } catch (JSONException e) {
            System.out.println("JSONExeption입니다.");
        }
        return null;
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
    public static void printMap (HashMap<Region, Node> map){
        Node node1 = map.get(Region.DONGJAK);
        Node node2 = map.get(Region.GANGDONG);
        Node node3 = map.get(Region.GANGNAM);
        Node node4 = map.get(Region.GURO);
        Node node5 = map.get(Region.SEOCHO);
        Node node6 = map.get(Region.SONGPA);
        System.out.println("-동작구-\n기온: " + node1.temper+"\n습도: "+ node1.humid+"\n미세먼지: "+node1.dust + "\n초미세먼지: "+ node1.ddust + "\n기준시각: " + node1.time);
        System.out.println("-강동구-\n기온: " + node2.temper+"\n습도: "+ node2.humid+"\n미세먼지: "+node2.dust + "\n초미세먼지: "+ node2.ddust + "\n기준시각: " + node2.time);
        System.out.println("-강남구-\n기온: " + node3.temper+"\n습도: "+ node3.humid+"\n미세먼지: "+node3.dust + "\n초미세먼지: "+ node3.ddust + "\n기준시각: " + node3.time);
        System.out.println("-구로구-\n기온: " + node4.temper+"\n습도: "+ node4.humid+"\n미세먼지: "+node4.dust + "\n초미세먼지: "+ node4.ddust + "\n기준시각: " + node4.time);
        System.out.println("-서초구-\n기온: " + node5.temper+"\n습도: "+ node5.humid+"\n미세먼지: "+node5.dust + "\n초미세먼지: "+ node5.ddust + "\n기준시각: " + node5.time);
        System.out.println("-송파구-\n기온: " + node6.temper+"\n습도: "+ node6.humid+"\n미세먼지: "+node6.dust + "\n초미세먼지: "+ node6.ddust + "\n기준시각: " + node6.time);

    }
}

}
