package com.example.biopunch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoardHR extends AppCompatActivity {

    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager viewPager;
    public TabLayout tabLayout;
     public String phn;

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
                startActivity(intSet);
                return true;}
            case R.id.workTime:
            {
                Intent intSet2=new Intent(getApplicationContext(),WorkTiming.class);
                startActivity(intSet2);
                return(true);
            }
            case R.id.refresh:
            {
                //refresh the list of punched and not punched
                return(true);
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

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setPagerAdapter() {
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
    }
}