package com.example.biopunch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LocationCheck extends AppCompatActivity {
    boolean locDone=false;
    int flag=0;
    LocationManager locationManager;
    LocationListener locationListener;
    private String no;
    private String from;
    private String hrno;
    Location dB;
    String latitudeDB;
    String longitudeDB;
   private String punched;
    public void startListening()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            startListening();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_check);
        no = getIntent().getStringExtra("phoneNumber");
        from = getIntent().getStringExtra("from");
        Log.i("number:",no);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        dB = new Location("");
        DatabaseReference usersdRef = FirebaseDatabase.getInstance().getReference().child("users").child(no);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("latitude").exists()) {
                        latitudeDB = ds.child("latitude").getValue(String.class);
                    }
                    if (ds.child("longitude").exists()) {
                        longitudeDB = ds.child("longitude").getValue(String.class);
                    }
                }
                    if (latitudeDB != null && latitudeDB.length() > 0)
                        dB.setLatitude(Double.parseDouble(latitudeDB));
                    if (longitudeDB != null && longitudeDB.length() > 0)
                        dB.setLongitude(Double.parseDouble(longitudeDB));
                    Log.i("DB Location:", String.valueOf(dB.getLatitude()));

                    if (dB.getLatitude() > 1) {
                        locationListener = new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                if (location != null) {
                                    if (flag == 0) {
                                        Log.i("location:", location.toString() + " " + dB.toString());
                                        if (dB.distanceTo(location) < 200)
                                            locDone = true;
                                        else
                                            locDone = false;
                                        //Log.i("distance:", String.valueOf(dB.distanceTo(location)));
                                        flag = 1;
                                    }
                                }
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                            }
                        };
                        if (ContextCompat.checkSelfPermission(LocationCheck.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(LocationCheck.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        } else {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                            Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (lastKnown != null) {
                                Log.i("lastKnown and database:", lastKnown.toString() + " " + dB.toString());
                                if (dB.distanceTo(lastKnown) < 200)
                                    locDone = true;
                                else
                                    locDone = false;
                                Log.i("distance:", String.valueOf(dB.distanceTo(lastKnown)));
                            }
                        }
                        if (from.equals("DashHR")) {
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference usersdRef = rootRef.child("users");
                            ValueEventListener eventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        if (ds.child("phone").getValue(String.class).equals(no))
                                        {
                                            punched=ds.child("Employee").child(no).child("Punched").getValue(String.class);
                                            if (locDone == true) {
                                                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                                Calendar calendar = Calendar.getInstance();
                                                int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                                                int minutes = calendar.get(Calendar.MINUTE);
                                                int seconds = calendar.get(Calendar.SECOND);
                                                String time=hour24hrs+":"+minutes+":"+seconds;
                                                Log.i("date today",currentDate+" "+time);
                                                if(punched.equals("YES"))
                                                {
                                                    FirebaseDatabase.getInstance().getReference().child("users")
                                                            .child(no)
                                                            .child("Employee")
                                                            .child(no)
                                                            .child("Punched")
                                                            .setValue("NO");
                                                    FirebaseDatabase.getInstance().getReference().child("users").child(no).child("Employee").child(no).child(currentDate).child("Work Time Out").setValue(time);
                                                }
                                                else
                                                {
                                                    FirebaseDatabase.getInstance().getReference().child("users")
                                                            .child(no)
                                                            .child("Employee")
                                                            .child(no)
                                                            .child("Punched")
                                                            .setValue("YES");
                                                    FirebaseDatabase.getInstance().getReference().child("users").child(no).child("Employee").child(no).child(currentDate).child("Work Time In").setValue(time);
                                                }
                                                FirebaseDatabase.getInstance().getReference().child("users").child(no).child("Employee").child(no).child(currentDate).child("Date").setValue(currentDate);
                                                Toast.makeText(LocationCheck.this, "Location matched!!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(LocationCheck.this, "Location unmatched!!", Toast.LENGTH_SHORT).show();
                                            }
                                            Intent i = new Intent(getApplicationContext(), DashBoardHR.class);
                                            i.putExtra("phoneNumber", no);
                                            startActivity(i);
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };
                            usersdRef.addListenerForSingleValueEvent(eventListener);
                        } else {
                            hrno = getIntent().getStringExtra("HRNO");
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference usersdRef = rootRef.child("users");
                            ValueEventListener eventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        if (ds.child("phone").getValue(String.class).equals(hrno))
                                        {
                                            punched=ds.child("Employee").child(no).child("Punched").getValue(String.class);
                                            if (locDone == true) {
                                                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                                Calendar calendar = Calendar.getInstance();
                                                int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                                                int minutes = calendar.get(Calendar.MINUTE);
                                                int seconds = calendar.get(Calendar.SECOND);
                                                String time=hour24hrs+":"+minutes+":"+seconds;
                                                Log.i("date today",currentDate+" "+time);
                                                if(punched.equals("NO"))
                                                {
                                                    FirebaseDatabase.getInstance().getReference().child("users")
                                                            .child(hrno)
                                                            .child("Employee")
                                                            .child(no)
                                                            .child("Punched")
                                                            .setValue("YES");
                                                    FirebaseDatabase.getInstance().getReference().child("Employees").child(no).child(currentDate).child("Work Time In").setValue(time);
                                                }
                                                else
                                                {
                                                    FirebaseDatabase.getInstance().getReference().child("users")
                                                            .child(hrno)
                                                            .child("Employee")
                                                            .child(no)
                                                            .child("Punched")
                                                            .setValue("NO");
                                                    FirebaseDatabase.getInstance().getReference().child("Employees").child(no).child(currentDate).child("Work Time Out").setValue(time);
                                                }
                                                FirebaseDatabase.getInstance().getReference().child("Employees").child(no).child(currentDate).child("Date").setValue(currentDate);
                                                Toast.makeText(LocationCheck.this, "Location matched!!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(LocationCheck.this, "Location unmatched!!", Toast.LENGTH_SHORT).show();
                                            }
                                            Intent i = new Intent(getApplicationContext(), EmpDashboard.class);
                                            i.putExtra("phone", no);
                                            startActivity(i);
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };
                            usersdRef.addListenerForSingleValueEvent(eventListener);
                        }
                    }


               }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

    }
}