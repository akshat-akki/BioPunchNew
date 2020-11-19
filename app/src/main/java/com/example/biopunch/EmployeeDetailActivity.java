package com.example.biopunch;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDetailActivity extends AppCompatActivity {
    private String empNo;
    private String role;
    private String hrNo;
     private TextView companyNameEmp;
    private TextView nameEmp;
    private TextView contactEmp;
    private Button download;
    String[] attend;
    List<String[]> data = new ArrayList<String[]>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        empNo=getIntent().getStringExtra("phoneNumber");
        role=getIntent().getStringExtra("role");
        hrNo=empNo.substring(empNo.indexOf(' ')+1);
        empNo=empNo.substring(0,empNo.indexOf(' '));
        contactEmp= findViewById(R.id.empContactNoText);
        contactEmp.setText(empNo);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Employees").child(empNo).child("Attendance");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("Date").exists() && ds.child("Work Time In").exists() && ds.child("Work Time Out").exists()) {
                        final String date = ds.child("Date").getValue(String.class);
                        final String InTime = ds.child("Work Time In").getValue(String.class);
                        final String OutTime = ds.child("Work Time Out").getValue(String.class);
                        Log.i("info", date + " " + InTime + " " + OutTime);
                        download = findViewById(R.id.downloadRepoEmpHr);
                        download.setOnClickListener(new View.OnClickListener() {
                            String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + empNo + "Report.csv"); // Here csv file name is MyCsvFile.csv

                            @Override
                            public void onClick(View v) {
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
                                Toast.makeText(getApplicationContext(),"File Downloaded at"+csv,Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = ref.child("users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("phone").getValue(String.class).equals(hrNo)) {
                         companyNameEmp=findViewById(R.id.companyTextViewEmp);
                         nameEmp=findViewById(R.id.empNameText);
                        if(ds.child("CompanyName").exists())
                            companyNameEmp.setText(ds.child("CompanyName").getValue(String.class));
                        if(ds.child("Employee").child(empNo).child("Name").exists())
                            nameEmp.setText(ds.child("Employee").child(empNo).child("Name").getValue(String.class));
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);
        if(role.equals("HR"))
        {
           findViewById(R.id.deleteEmployee).setVisibility(View.VISIBLE);
            findViewById(R.id.downloadRepoEmpHr).setVisibility(View.VISIBLE);
        }
        else
        {
            findViewById(R.id.deleteEmployee).setVisibility(View.INVISIBLE);
            findViewById(R.id.downloadRepoEmpHr).setVisibility(View.INVISIBLE);
        }
        findViewById(R.id.deleteEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EmployeeDetailActivity.this).setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are You Sure?")
                        .setMessage("Do you definitely want to delete this employee?")
                        .setPositiveButton("No",null)
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete the employee from list
                                Query query = ref.child("Employees").orderByChild("MyNo").equalTo(empNo);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.i( "onCancelled", String.valueOf(error.toException()));
                                    }
                                });
                                Query newQuery = ref.child("users").child(hrNo).child("Employee").orderByChild("Phone").equalTo(empNo);
                                newQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.i( "onCancelled", String.valueOf(error.toException()));
                                    }
                                });
                                Intent intent=new Intent(getApplicationContext(),DashBoardHR.class);
                                intent.putExtra("phoneNumber",hrNo);
                                intent.putExtra("from","EmployeeDelete");
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });
    }
}