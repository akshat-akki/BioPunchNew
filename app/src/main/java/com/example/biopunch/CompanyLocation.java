package com.example.biopunch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CompanyLocation extends FragmentActivity implements OnMapReadyCallback {
    String hrno;
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    private boolean save=false;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        hrno=getIntent().getStringExtra("phoneNumber");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final float zoomLevel=14.0f;
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Toast.makeText(CompanyLocation.this, "Tip:Long press to save the company location!!", Toast.LENGTH_SHORT).show();
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                if(location!=null)
                {
                 //   Toast.makeText(CompanyLocation.this, location.toString(), Toast.LENGTH_SHORT).show();
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("YOUR LOCATION!!"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,zoomLevel));
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    String locationAdd="";
                    try {
                        List<Address> list = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
                        Address address;
                        if((list != null) && (list.size() > 0)) {
                            address = list.get(0);
                            if (address.getThoroughfare() != null)
                                locationAdd+=  address.getThoroughfare() + " ";
                            if (address.getLocality() != null)
                                locationAdd+= address.getLocality() + " ";
                            if (address.getAdminArea() != null)
                                locationAdd+= address.getAdminArea() + " ";
                           // Toast.makeText(CompanyLocation.this, locationAdd, Toast.LENGTH_SHORT).show();
                            Log.i("Location:",locationAdd);
                            // if(address!=null)
                            //{MarkerOptions options = new MarkerOptions().position(new LatLng(latLng.latitude,latLng.longitude)).title(address.getLocality());
                              //  mMap.addMarker(options).showInfoWindow();
                               // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            //}
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> list;
                Address address;
                String locationAdd="";
                try {
                    list = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
                    if((list != null) && (list.size() > 0)) {
                        address = list.get(0);
                        if (address.getThoroughfare() != null)
                            locationAdd+=  address.getThoroughfare() + " ";
                        if (address.getLocality() != null)
                            locationAdd+= address.getLocality() + " ";
                        if (address.getAdminArea() != null)
                            locationAdd+= address.getAdminArea() + " ";
                        if(address!=null)
                        {
                            mMap.clear();
                            MarkerOptions options = new MarkerOptions().position(new LatLng(latLng.latitude,latLng.longitude)).title(address.getLocality());
                            mMap.addMarker(options).showInfoWindow();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel));
                            FirebaseDatabase.getInstance().getReference().child("users").child(hrno).child("Location").setValue(latLng);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(locationAdd.length()==0)
                {
                    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm yyyy-MM-dd");
                    locationAdd+=sdf.format(new Date());
                }
                Toast.makeText(CompanyLocation.this,"Location Saved:)", Toast.LENGTH_LONG).show();
                save=true;
                Log.i("Location:",locationAdd);
            }
        });
        if(save==true)
        {
            Intent intent=new Intent(getApplicationContext(),DashBoardHR.class);
            intent.putExtra("phoneNumber",hrno);
            startActivity(intent);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location lastKnown=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnown!=null)
            {
                mMap.clear();
           // Toast.makeText(CompanyLocation.this, lastKnown.toString(), Toast.LENGTH_SHORT).show();
            LatLng userLocation = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLocation).title("YOUR LOCATION!!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,zoomLevel));
            }
        }
    }
}