package com.cookandroid.mainui.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cookandroid.mainui.EventClass;
import com.cookandroid.mainui.MyStruct;

import com.cookandroid.mainui.R;

public class EventsFragment extends Fragment {

    private EventsViewModel eventsViewModel;
    private ListView listview;
    private ListViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel =
                ViewModelProviders.of(this).get(EventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        adapter = new ListViewAdapter(MyStruct.eventList);
        listview = (ListView)root.findViewById(R.id.listView);
        listview.setAdapter(adapter);
        return root;
    }
}