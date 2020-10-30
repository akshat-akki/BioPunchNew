package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class FragmentFirst extends Fragment {
    public FragmentFirst() {
    }

    ListView listView;
    static ArrayList<String> locations = new ArrayList<String>();
    static ArrayAdapter<String> places;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        TabLayout b = (TabLayout) v.findViewById(R.id.Tabview);
        b.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Intent myIntent = new Intent(getActivity(),DashBoardHR.class);
                        getActivity().startActivity(myIntent);
                        break;
                    case 1:
                        Intent myIntent1 = new Intent(getActivity(),PunchActivity.class);
                        getActivity().startActivity(myIntent1);
                        break;
                    case 2:
                        DashBoardHR activity = (DashBoardHR) getActivity();
                        activity.callActivity();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Intent myIntent = new Intent(getActivity(),DashBoardHR.class);
                        getActivity().startActivity(myIntent);
                        break;
                    case 1:
                        Intent myIntent1 = new Intent(getActivity(),PunchActivity.class);
                        getActivity().startActivity(myIntent1);
                        break;
                    case 2:
                        DashBoardHR activity = (DashBoardHR) getActivity();
                        activity.callActivity();
                        break;

                }
            }
        });
        listView = (ListView) v.findViewById(R.id.ListEmployee);
        return v;
    }
}

