package com.cookandroid.mainui.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cookandroid.mainui.R;

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
                text_region.setText("현재 위치");
                text_temper.setText("온도 입력");
                text_dust.setText("미세먼지 수치 입력");
                text_ddust.setText("초미세먼지 수치 입력");
                text_humid.setText("습도 입력");
                text_time.setText("정보 가져온 시간 입력");
            }
        });
        return root;
    }
}