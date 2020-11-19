package com.example.biopunch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class DashBoardHR extends AppCompatActivity {

    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager viewPager;
    public TabLayout tabLayout;
     public static String phn;
     public static String from;//for determining if the element is deleted

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(DashBoardHR.this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you definitely want to exit?")
                .setPositiveButton("NO",null)
                .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .show();
    }

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
                //convert json to excel
                //return(true);
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
       // Toast.makeText(DashBoardHR.this, phn, Toast.LENGTH_SHORT).show();

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