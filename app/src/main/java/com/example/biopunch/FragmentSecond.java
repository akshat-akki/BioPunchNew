package com.example.biopunch;
import android.app.DownloadManager;
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

public class FragmentSecond extends Fragment {

    public FragmentSecond() {}
    private TabLayout tPunched;
    private String numberPunched;
    private String Punchedcount;
    private ListView listViewPunched;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ArrayList<String> EmployeeNamesPunched = new ArrayList<String>();
        DashBoardHR activity = (DashBoardHR) getActivity();
        numberPunched=activity.phn;
        final View v1 = inflater.inflate(R.layout.fragment_second,container, false);
        TabLayout b1 = (TabLayout) v1.findViewById(R.id.TabviewPunched);
        b1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Intent myIntent = new Intent(getActivity(),DashBoardHR.class);
                        myIntent.putExtra("phoneNumber",numberPunched);
                        getActivity().startActivity(myIntent);
                        break;
                    case 1:
                        Intent myIntent1 = new Intent(getActivity(),PunchActivity.class);
                        myIntent1.putExtra("phoneNumber",numberPunched);
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
        listViewPunched = (ListView) v1.findViewById(R.id.ListEmployeePunched);

        tPunched=activity.tabLayout;


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("users").child(numberPunched).child("Employee");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long countpunched=0;
                long totalemployee=dataSnapshot.getChildrenCount();

                Punchedcount=String.valueOf(dataSnapshot.getChildrenCount());
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.child("Punched").getValue(String.class).equals("YES")&&ds.child("NameHR").exists()) {
                        String nameHr = ds.child("NameHR").getValue(String.class);
                        EmployeeNamesPunched.add(0,nameHr + "(HR)");
                        countpunched++;
                    }
                    if(ds.child("Name").exists()&&ds.child("Punched").getValue(String.class).equals("YES")) {
                        String name = ds.child("Name").getValue(String.class);
                        EmployeeNamesPunched.add(name);
                        countpunched++;
                    }
                    tPunched.getTabAt(2).setText("Not Punched ("+(totalemployee-countpunched)+")");
                    tPunched.getTabAt(1).setText("Punched ("+countpunched+")");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(v1.getContext(), android.R.layout.simple_list_item_1,EmployeeNamesPunched);
                listViewPunched.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

        return v1;
    }

}






