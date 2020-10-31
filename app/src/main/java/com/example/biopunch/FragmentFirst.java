package com.example.biopunch;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class FragmentFirst extends Fragment {
    public FragmentFirst() {
    }
    TabLayout t;
    ListView listView;
    String number;
    String employeecount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ArrayList<String> EmployeeNames = new ArrayList<String>();
        final ArrayAdapter<String> adapter;
        DashBoardHR activity = (DashBoardHR) getActivity();
        number=activity.phn;
        final View v = inflater.inflate(R.layout.fragment_first, container, false);
        TabLayout b = (TabLayout) v.findViewById(R.id.Tabview);
        b.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Intent myIntent = new Intent(getActivity(),DashBoardHR.class);
                        myIntent.putExtra("phoneNumber",number);
                        getActivity().startActivity(myIntent);
                        break;
                    case 1:
                        Intent myIntent1 = new Intent(getActivity(),PunchActivity.class);
                        myIntent1.putExtra("phoneNumber",number);
                        getActivity().startActivity(myIntent1);
                        break;
                    case 2:
                        DashBoardHR activity = (DashBoardHR) getActivity();
                        activity.callActivity();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
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
        listView = (ListView) v.findViewById(R.id.ListEmployee);

        t=activity.tabLayout;


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("users").child(number).child("Employee");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long countNotpunched=0;
                long totalemployee=dataSnapshot.getChildrenCount();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("NameHR").exists()) {
                        String nameHr = ds.child("NameHR").getValue(String.class);
                        EmployeeNames.add(0, nameHr + "(HR)");
                    }
                    if (ds.child("Name").exists()) {
                        String name = ds.child("Name").getValue(String.class);
                        EmployeeNames.add(name);
                    }
                    if (ds.child("Punched").getValue(String.class).equals("NO")) {
                        countNotpunched++;
                    }
                }

                    t.getTabAt(0).setText("Employee ("+totalemployee+")");
                    t.getTabAt(2).setText("Not Punched ("+countNotpunched+")");
                    t.getTabAt(1).setText("Punched ("+(totalemployee-countNotpunched)+")");

                ArrayAdapter<String> adapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_1,EmployeeNames);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

        return v;
    }

}

