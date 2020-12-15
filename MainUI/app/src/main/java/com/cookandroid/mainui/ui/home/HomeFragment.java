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
import com.cookandroid.mainui.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;

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
                text_region.setText("동작구");
                text_temper.setText("4.2");
                text_dust.setText("0.0");
                text_ddust.setText("0.0");
                text_humid.setText("27");
                text_time.setText("2020/12/03");
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
                    Toast.makeText(getContext(),""+mResultList.get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(this.getClass().getName(),"onComplete: 변환실패");
                }
            }
        });
        return root;
    }
}