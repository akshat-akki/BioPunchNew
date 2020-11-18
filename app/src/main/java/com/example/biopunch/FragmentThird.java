package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentThird extends Fragment {

    public FragmentThird() {}
    private TabLayout tNotPunched;
    private String numberNotPunched;
    private String Punchedcount;
    private ListView listViewNotPunched;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
       final  View v2= inflater.inflate(R.layout.fragment_third, container, false);

        final ArrayList<String> EmployeeNamesNotPunched = new ArrayList<String>();
        DashBoardHR activity = (DashBoardHR) getActivity();
        numberNotPunched=activity.phn;
            TabLayout b2 = (TabLayout) v2.findViewById(R.id.TabviewNotPunched);
        b2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Intent myIntent = new Intent(getActivity(),DashBoardHR.class);
                        myIntent.putExtra("phoneNumber",numberNotPunched);
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
        listViewNotPunched = (ListView) v2.findViewById(R.id.ListEmployeeNotPunched);

        tNotPunched=activity.tabLayout;


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("users").child(numberNotPunched).child("Employee");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long countNotpunched=0;
                long totalemployee=dataSnapshot.getChildrenCount();

                Punchedcount=String.valueOf(dataSnapshot.getChildrenCount());
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.child("Punched").getValue(String.class).equals("NO")&&ds.child("NameHR").exists()) {
                        String nameHr = ds.child("NameHR").getValue(String.class);
                        EmployeeNamesNotPunched.add(0,nameHr + "(HR)");
                        countNotpunched++;
                    }
                    if(ds.child("Name").exists()&&ds.child("Punched").getValue(String.class).equals("NO")) {
                        String name = ds.child("Name").getValue(String.class);
                        EmployeeNamesNotPunched.add(name);
                        countNotpunched++;
                    }
                   // tNotPunched.getTabAt(2).setText("Not Punched ("+(countNotpunched)+")");
                   // tNotPunched.getTabAt(1).setText("Punched ("+(totalemployee-countNotpunched)+")");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(v2.getContext(), android.R.layout.simple_list_item_1,EmployeeNamesNotPunched);
                listViewNotPunched.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

        return v2;
    }

}


