package com.example.biopunch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.ActionCodeUrl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardHR extends AppCompatActivity {

    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager viewPager;
     public TabLayout tabLayout;
     public static String phn;
     public static String from;
     private  String[] attend;
     private List<String[]> data = new ArrayList<String[]>();
     //for determining if the element is deleted
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_hr,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.settings:
            {Intent intSet=new Intent(getApplicationContext(),Settings_HR.class);
            intSet.putExtra("phoneNumber",phn);
                startActivity(intSet);
                return true;}
            case R.id.refresh:
            {
                Intent myIntent = new Intent(getApplicationContext(),DashBoardHR.class);
                myIntent.putExtra("phoneNumber",phn);
                startActivity(myIntent);
                //refresh the list of punched and not punched
                return(true);
            }
            case R.id.downloadReport:
            {
                String path=report();
                Toast.makeText(getApplicationContext(),"File Downloaded at"+path,Toast.LENGTH_LONG).show();
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_h_r);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        Intent i=getIntent();
        phn=i.getStringExtra("phoneNumber");
        from=i.getStringExtra("from");
        Toast.makeText(DashBoardHR.this, phn, Toast.LENGTH_SHORT).show();

        setPagerAdapter();
        setTabLayout();
           }
    public void callActivity()
    {
        Intent intent=new Intent(getApplicationContext(),AddEmployee.class);
        intent.putExtra("phone",phn);
        startActivity(intent);
    }
    private String report()
    {
        String path;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("users").child(phn).child("Employee").child(phn).child("Attendance");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("Date").exists() && ds.child("Work Time In").exists() && ds.child("Work Time Out").exists()) {
                        final String date = ds.child("Date").getValue(String.class);
                        final String InTime = ds.child("Work Time In").getValue(String.class);
                        final String OutTime = ds.child("Work Time Out").getValue(String.class);
                        Log.i("info", date + " " + InTime + " " + OutTime);
                        String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + phn + "Report.csv"); // Here csv file name is MyCsvFile.csv
                        CSVWriter writer = null;
                        try {
                            writer = new CSVWriter(new FileWriter(csv));
                            data.add(new String[]{"*DATE*", "*IN TIME*", "*OUT TIME*"});
                            data.add(new String[]{date, InTime, OutTime});
                            writer.writeAll(data); // data is adding to csv
                            writer.close();
                            //callRead();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
         return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + phn + "Report.csv";
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setPagerAdapter() {
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
    }
}