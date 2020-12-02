package com.cookandroid.mainui;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class LoadNodeMap extends Thread{
    HashMap<String, Node> NodeMap = new HashMap<String, Node>();
    public void run() {
        //Log.d("log: ","runlog");
        //System.out.println("Thread Running");

        NodeMap = updateMap();
        //printMap(NodeMap);
    }
    public static HashMap<String, Node>updateMap(){
        JSONArray ja = null;
        Log.d("log: ","templog0");
        HashMap<String, Node> NodeMap = new HashMap<String, Node>();
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
                Node tmpNode = new Node(region_data,temper_data,dust_data,ddust_data,humid_data,timestamp_data);
                NodeMap.put(tmpNode.Region,tmpNode);
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
    public HashMap<String, Node> GetNodeMap(){
        return NodeMap;
    }

}
