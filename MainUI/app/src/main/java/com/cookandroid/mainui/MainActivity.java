package com.cookandroid.mainui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.cookandroid.mainui.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Object;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {
    public static Node node1 = new Node(Region.DONGJAK, -1.8, 28.0, 19.0, 54.2, "2020-12-16");
    public static Node node2 = new Node(Region.GANGDONG, -2.1, 25.0, 16.0, 51.7, "2020-12-16");
    public static Node node3 = new Node(Region.GANGNAM, -1.3, 24.0, 13.0, 50.4, "2020-12-16");
    public static Node node4 = new Node(Region.GURO, 0.0, 0.0, 0.0, 0.0, "2020-12-16");
    public static Node node5 = new Node(Region.SEOCHO, 0.0, 0.0, 0.0, 0.0, "2020-12-16");
    public static Node node6 = new Node(Region.SONGPA, 0.0, 0.0, 0.0, 0.0, "2020-12-16");

    public static GpsTracker gpsTracker;
    public static double latitude;
    public static double longitude;

    NotificationManager notificationManager;
    NotificationCompat.Builder builder= null;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        Thread thread1 = new Thread() {
            public void run() {
                //Log.d("log: ","runlog");
                //System.out.println("Thread Running");
                HashMap<Region, Node> NodeMap = new HashMap<Region, Node>();
                NodeMap = updateMap();
                //printMap(NodeMap);
            }
        };
        thread1.start();
*/
        notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelID="channel_01"; //알림채널 식별자
            String channelName="MyChannel01"; //알림채널의 이름(별명)
            //알림채널 객체 만들기
            NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
            //알림매니저에게 채널 객체의 생성을 요청
            notificationManager.createNotificationChannel(channel);
            //알림건축가 객체 생성
            builder=new NotificationCompat.Builder(this, channelID);
        }else{
            //알림 건축가 객체 생성
            builder= new NotificationCompat.Builder(this, null);
        }


        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<Region, Node> NodeMap = new HashMap<Region, Node>();
                NodeMap = updateMap();
                while (true) {
                    Log.d("Thread", "running");
                    //node1 = NodeMap.get(Region.DONGJAK);              //어떤 지역의 현재 노드 정보를 가져옴.
                    double sensorval=0.0;
                    int idx=0;
                    for(EventClass i : MyStruct.eventList) { //for문을 통한 전체출력
                        Log.d("Thread", ""+i.getSensor());

                        try {
                            Field field = MainActivity.node1.getClass().getDeclaredField(i.getSensor());
                            sensorval = field.getDouble(node1);
                        }catch(IllegalArgumentException e){
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        Log.d("Thread", ""+sensorval);

                        if(i.min <= sensorval || sensorval >= i.max){
                            Log.d("Thread: ","로직 조건 만족!");

                            if(i.timer == 3) // 3초에 한번씩 푸시알림
                            {
                                Log.d("Thread: ","푸시알림!");
                                builder.setSmallIcon(android.R.drawable.ic_menu_view);
                                builder.setContentTitle("ToIoT 로직 만족! ("+ i.location + ")");//알림창 제목
                                builder.setContentText("센서: " + i.getSensor() + "  센서값: " + sensorval);//알림창 내용
                                Notification notification=builder.build();
                                notificationManager.notify(idx, notification);
                                i.timer = 0;
                            }
                            i.timer++;
                        }
                        idx++;
                    }
                    try {
                        Thread.sleep(1000);
                        idx = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread2.start();

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

        if (checkLocationServicesStatus()) {
            checkRunTimePermission();
        } else {
            showDialogForLocationServiceSetting();
        }
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
        node1 = map.get(Region.DONGJAK);
        node2 = map.get(Region.GANGDONG);
        node3 = map.get(Region.GANGNAM);
        node4 = map.get(Region.GURO);
        node5 = map.get(Region.SEOCHO);
        node6 = map.get(Region.SONGPA);
        System.out.println("-동작구-\n기온: " + node1.temper+"\n습도: "+ node1.humid+"\n미세먼지: "+node1.dust + "\n초미세먼지: "+ node1.ddust + "\n기준시각: " + node1.time);
        System.out.println("-강동구-\n기온: " + node2.temper+"\n습도: "+ node2.humid+"\n미세먼지: "+node2.dust + "\n초미세먼지: "+ node2.ddust + "\n기준시각: " + node2.time);
        System.out.println("-강남구-\n기온: " + node3.temper+"\n습도: "+ node3.humid+"\n미세먼지: "+node3.dust + "\n초미세먼지: "+ node3.ddust + "\n기준시각: " + node3.time);
        System.out.println("-구로구-\n기온: " + node4.temper+"\n습도: "+ node4.humid+"\n미세먼지: "+node4.dust + "\n초미세먼지: "+ node4.ddust + "\n기준시각: " + node4.time);
        System.out.println("-서초구-\n기온: " + node5.temper+"\n습도: "+ node5.humid+"\n미세먼지: "+node5.dust + "\n초미세먼지: "+ node5.ddust + "\n기준시각: " + node5.time);
        System.out.println("-송파구-\n기온: " + node6.temper+"\n습도: "+ node6.humid+"\n미세먼지: "+node6.dust + "\n초미세먼지: "+ node6.ddust + "\n기준시각: " + node6.time);
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}
