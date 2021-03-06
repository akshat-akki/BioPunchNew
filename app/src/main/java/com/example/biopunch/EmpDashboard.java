package com.example.biopunch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.vo.DateData;

public class EmpDashboard extends AppCompatActivity {
    private String empno="";
    private String hrno="";
    private TabLayout bottomtab;
    MCalendarView calendarView;
    ArrayList<DateData> dates=new ArrayList<>();
    private  String[] attend;
    private  List<String[]> data = new ArrayList<String[]>();


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(EmpDashboard.this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you definitely want to exit?")
                .setPositiveButton("NO",null)
                .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();

                        System.exit(0);
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_employee,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.settingsEmployee:
            {
                Intent intSet=new Intent(getApplicationContext(),SettingsEmployee.class);
                intSet.putExtra("phoneNumber",empno);
                startActivity(intSet);
                return true;}
            case R.id.downloadReportEmployee:
            {
                String path=report();
                Toast.makeText(getApplicationContext(),"File Downloaded at"+path,Toast.LENGTH_LONG).show();

                return(true);
            }
            default:
                return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_dashboard);
        Intent i=getIntent();
        empno=i.getStringExtra("phone");
        bottomtab=findViewById(R.id.TabviewEmp);
         calendarView = findViewById(R.id.calendar);

        DatabaseReference rootRef1 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef1 = rootRef1.child("Employees").child(empno).child("Attendance");
        ValueEventListener eventListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("Date").exists())
                    {
                        String str=ds.child("Date").getValue(String.class);
                        Log.i("Dates",str+"");
                        dates.add(new DateData(Integer.parseInt(str.substring(6,10)),Integer.parseInt(str.substring(3,5)),Integer.parseInt(str.substring(0,2))));
                    }
                }
                calendarView.setMarkedStyle(MarkStyle.BACKGROUND,Color.GREEN);
                for(int l=0;l<dates.size();l++) {

                    calendarView.markDate(dates.get(l).getYear(),dates.get(l).getMonth(),dates.get(l).getDay());//mark multiple dates with this code.
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef1.addListenerForSingleValueEvent(eventListener1);
        Log.i("marked dates:-",""+calendarView.getMarkedDates());

        bottomtab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Intent myIntent = new Intent(getApplicationContext(),EmpDashboard.class);
                        myIntent.putExtra("phone",empno);
                        startActivity(myIntent);
                        break;
                    case 1:
                        Intent myIntent1 = new Intent(getApplicationContext(),PunchActivity.class);
                        myIntent1.putExtra("phoneNumber",empno);
                        myIntent1.putExtra("Empdash",true);
                        startActivity(myIntent1);
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
                        Intent myIntent = new Intent(getApplicationContext(),EmpDashboard.class);
                        myIntent.putExtra("phone",empno);
                        startActivity(myIntent);
                        break;
                    case 1:
                        Intent myIntent1 = new Intent(getApplicationContext(),PunchActivity.class);
                        myIntent1.putExtra("phoneNumber",empno);
                        myIntent1.putExtra("Empdash",true);
                        startActivity(myIntent1);
                        break;
                }
            }
        });
    }
    private String report() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Employees").child(empno).child("Attendance");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("Date").exists() && ds.child("Work Time In").exists() && ds.child("Work Time Out").exists()) {
                        final String date = ds.child("Date").getValue(String.class);
                        final String InTime = ds.child("Work Time In").getValue(String.class);
                        final String OutTime = ds.child("Work Time Out").getValue(String.class);
                        Log.i("info", date + " " + InTime + " " + OutTime);
                        String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + empno + "Report.csv"); // Here csv file name is MyCsvFile.csv
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
                        Toast.makeText(getApplicationContext(), "File Downloaded at" + csv, Toast.LENGTH_LONG).show();
                    }


                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + empno + "Report.csv";
    }
}