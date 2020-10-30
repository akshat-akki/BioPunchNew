package com.example.biopunch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class DashBoardHR extends AppCompatActivity {

    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
     public String phn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_h_r);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setPagerAdapter();
        setTabLayout();
        Intent i=getIntent();
        phn=i.getStringExtra("phoneNumber");
        Toast.makeText(DashBoardHR.this, phn, Toast.LENGTH_SHORT).show();
    }
    public void callActivity()
    {
        Intent intent=new Intent(getApplicationContext(),AddEmployee.class);
        intent.putExtra("phone",phn);
        startActivity(intent);
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Employee (1)");
        tabLayout.getTabAt(1).setText("Punched (1)");
        tabLayout.getTabAt(2).setText("Not Punched (0)");
    }

    private void setPagerAdapter() {
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
    }
}