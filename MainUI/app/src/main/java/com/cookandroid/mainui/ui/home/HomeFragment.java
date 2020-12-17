package com.cookandroid.mainui.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cookandroid.mainui.GpsTracker;
import com.cookandroid.mainui.MainActivity;
import com.cookandroid.mainui.Node;
import com.cookandroid.mainui.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private Node visual_node;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView text_home = root.findViewById(R.id.text_home);
        final TextView text_region = root.findViewById(R.id.home_region);
        final TextView text_temper = root.findViewById(R.id.home_temper);
        final TextView text_dust = root.findViewById(R.id.home_dust);
        final TextView text_ddust = root.findViewById(R.id.home_ddust);
        final TextView text_humid = root.findViewById(R.id.home_humid);
        final TextView text_time = root.findViewById(R.id.home_time);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                text_home.setText("홈 화면");
                text_region.setText(""+MainActivity.node1.getRegion());
                text_temper.setText(""+MainActivity.node1.getTemper());
                text_dust.setText(""+MainActivity.node1.getDust());
                text_ddust.setText(""+MainActivity.node1.getDdust());
                text_humid.setText(""+MainActivity.node1.getHumid());
                text_time.setText(""+MainActivity.node1.getTime());
            }
        });
        Button ShowLocationButton = (Button) root.findViewById(R.id.gpsButton);
        ShowLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i(this.getClass().getName(), "gpsButton Clicked");
                Geocoder mGeoCoder = new Geocoder(getContext(), Locale.getDefault());
                MainActivity.gpsTracker = new GpsTracker(getContext());

                MainActivity.latitude = MainActivity.gpsTracker.getLatitude();
                MainActivity.longitude = MainActivity.gpsTracker.getLongitude();

                try {
                    List<Address> mResultList = mGeoCoder.getFromLocation(
                            MainActivity.latitude, MainActivity.longitude, 1
                    );
                    Log.d(this.getClass().getName(), mResultList.get(0).getAddressLine(0));
                    //Toast.makeText(getContext(),""+mResultList.get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),"현재위치: 동작구로 업데이트했습니다.", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(this.getClass().getName(),"onComplete: 변환실패");
                }
                // 시각화 업데이트
                text_region.setText("동작구");
                text_temper.setText(""+MainActivity.node1.getTemper() + "℃");
                text_dust.setText(""+MainActivity.node1.getDust() + "㎍/m³");
                text_ddust.setText(""+MainActivity.node1.getDdust() + "㎍/m³");
                text_humid.setText(""+MainActivity.node1.getHumid() + "%");
                text_time.setText(""+MainActivity.node1.getTime());
            }
        });

        //-------------------지역별 버튼---------------------
        // 1. 동작구
        Button home_node1= (Button) root.findViewById(R.id.home_node1);
        home_node1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                text_region.setText("동작구");
                text_temper.setText(""+MainActivity.node1.getTemper() + "℃");
                text_dust.setText(""+MainActivity.node1.getDust()+ "㎍/m³");
                text_ddust.setText(""+MainActivity.node1.getDdust()+ "㎍/m³");
                text_humid.setText(""+MainActivity.node1.getHumid()+ "%");
                text_time.setText(""+MainActivity.node1.getTime());
            }
        });
        // 2. 강동구
        Button home_node2= (Button) root.findViewById(R.id.home_node2);
        home_node2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                text_region.setText("강남구");
                text_temper.setText(""+MainActivity.node2.getTemper()+ "℃");
                text_dust.setText(""+MainActivity.node2.getDust()+ "㎍/m³");
                text_ddust.setText(""+MainActivity.node2.getDdust()+ "㎍/m³");
                text_humid.setText(""+MainActivity.node2.getHumid()+ "%");
                text_time.setText(""+MainActivity.node2.getTime());
            }
        });
        Button home_node3= (Button) root.findViewById(R.id.home_node3);
        home_node3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                text_region.setText("종로구");
                text_temper.setText(""+MainActivity.node3.getTemper()+ "℃");
                text_dust.setText(""+MainActivity.node3.getDust()+ "㎍/m³");
                text_ddust.setText(""+MainActivity.node3.getDdust()+ "㎍/m³");
                text_humid.setText(""+MainActivity.node3.getHumid() + "%");
                text_time.setText(""+MainActivity.node3.getTime());
            }
        });

        return root;
    }
}