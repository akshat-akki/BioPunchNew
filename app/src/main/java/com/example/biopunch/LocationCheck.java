package com.example.biopunch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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

import java.util.List;
import java.util.Locale;

public class LocationCheck extends AppCompatActivity {
    boolean locDone=false;
    LocationManager locationManager;
    LocationListener locationListener;
    private String no;
    private String from;
    private String hrno;
    Location dB;
    public void startListening()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }
    public String updateLocation(Location location)
    {
        String add="";
        Geocoder geo=new Geocoder(this, Locale.getDefault());
        try {
            List<Address> l = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if(l!=null && l.size()>0)
            {
                if(l.get(0).getThoroughfare()!=null)
                {
                    add+=l.get(0).getThoroughfare()+"\n";
                }
                if(l.get(0).getLocality()!=null)
                {
                    add+=l.get(0).getLocality()+" ";
                }
                if(l.get(0).getPostalCode()!=null)
                {
                    add+=l.get(0).getPostalCode()+" ";
                }
                if(l.get(0).getAdminArea()!=null)
                {
                    add+=l.get(0).getAdminArea()+" ";
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(add.length()<=0)
            add="Address not found :(";
        return(add);
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
        no=getIntent().getStringExtra("phoneNumber");
        from=getIntent().getStringExtra("from");
        Log.i("numberHR",no+"  "+from);
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
          dB=new Location("");
        DatabaseReference usersdRef = FirebaseDatabase.getInstance().getReference().child("users").child(no);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String latitudeDB ="";
                String longitudeDB="";
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("Location").child("latitude").exists()) {
                        latitudeDB = ds.child("Location").child("latitude").getValue(String.class);
                    }
                    if (ds.child("Location").child("longitude").exists()) {
                        longitudeDB = ds.child("Location").child("longitude").getValue(String.class);
                    }
                    break;
                }
                if(latitudeDB!=null && latitudeDB.length()>0)
                    dB.setLatitude(Double.parseDouble(latitudeDB));
                if(longitudeDB!=null && longitudeDB.length()>0)
                    dB.setLongitude(Double.parseDouble(longitudeDB));
                Log.i("latitudeDb and longitudeDb=",latitudeDB+"  "+longitudeDB);
                Toast.makeText(LocationCheck.this, dB.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location and db",location.toString()+"  "+dB.toString());
                if(location!=null)
                {
                    if(dB.distanceTo(location)<200)
                        locDone=true;
                    else
                        locDone=false;
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
        if(ContextCompat.checkSelfPermission(LocationCheck.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(LocationCheck.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location lastKnown=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i("curr and db",lastKnown.toString()+"  "+dB.toString());
            if(lastKnown!=null)
            {
                if(dB.distanceTo(lastKnown)<200)
                    locDone=true;
                else
                    locDone=false;
            }
        }
        if(from.equals("DashHR"))
        {
            if(locDone==true)
            {
                FirebaseDatabase.getInstance().getReference().child("users")
                        .child(no)
                        .child("Employee")
                        .child(no)
                        .child("Punched")
                        .setValue("YES");
                Toast.makeText(LocationCheck.this, "Location matched!!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(LocationCheck.this, "Location unmatched!!", Toast.LENGTH_SHORT).show();
            }
            //   Intent i = new Intent(getApplicationContext(), DashBoardHR.class);
            // i.putExtra("phoneNumber", no);
            //startActivity(i);
        }
        else
        {
            hrno=getIntent().getStringExtra("HRNO");
            if(locDone==true)
            {
                FirebaseDatabase.getInstance().getReference().child("users")
                        .child(hrno)
                        .child("Employee")
                        .child(no)
                        .child("Punched")
                        .setValue("YES");
                Toast.makeText(LocationCheck.this, "Location matched!!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(LocationCheck.this, "Location unmatched!!", Toast.LENGTH_SHORT).show();
            }
            //Intent i = new Intent(getApplicationContext(), EmpDashboard.class);
            //i.putExtra("phone", no);
            //startActivity(i);
        }
    }
}