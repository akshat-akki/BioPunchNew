package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class FragmentFirst extends Fragment implements View.OnClickListener {
    public FragmentFirst() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_first, container, false);
        Button b = (Button)v.findViewById(R.id.addEmployee);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addEmployee:
              Intent intent=new Intent(getActivity(),AddEmployee.class);
              getActivity().startActivity(intent);


                break;
        }
    };

    }

