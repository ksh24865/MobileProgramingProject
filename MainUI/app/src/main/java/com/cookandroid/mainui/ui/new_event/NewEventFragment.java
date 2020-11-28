package com.cookandroid.mainui.ui.new_event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cookandroid.mainui.MyStruct;
import com.cookandroid.mainui.EventClass;
import com.cookandroid.mainui.R;

public class NewEventFragment extends Fragment {

    private NewEventViewModel newEventViewModel;

    // 임시 EventClass (등록 버튼을 누르면 이 정보와 같은 EventClass를 MyStruct.eventList에 추가해줌.)
    private EventClass tempEventClass = new EventClass();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newEventViewModel =
                ViewModelProviders.of(this).get(NewEventViewModel.class);
        View root = inflater.inflate(R.layout.fragment_new_event, container, false);
        final TextView textView = root.findViewById(R.id.text_new_event);
        /*newEventViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        // Spinner 및 Button UI------------------------------------------------------------
        Spinner spinner_location = root.findViewById(R.id.location);
        Spinner spinner_sensor = root.findViewById(R.id.sensor);
        Button  button_registeration = root.findViewById(R.id.Btn_regEvent);

        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempEventClass.location = (String) adapterView.getItemAtPosition(i);
                textView.setText(tempEventClass.location);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        spinner_sensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempEventClass.sensor = (String) adapterView.getItemAtPosition(i);
                textView.setText(tempEventClass.sensor);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        button_registeration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                EventClass newEvent = new EventClass(
                        tempEventClass.location,
                        tempEventClass.sensor,
                        tempEventClass.min,
                        tempEventClass.max,
                        tempEventClass.time
                );

                MyStruct.eventList.add(newEvent);
            }
        });

        return root;
    }
}