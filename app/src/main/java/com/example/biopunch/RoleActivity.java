package com.example.biopunch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RoleActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(RoleActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
    }
    public void loginHR(View view)
    {
        Intent intent=new Intent(getApplicationContext(),GetnumberActivity.class);
        intent.putExtra("login","HR");
        startActivity(intent);
    }
    public void loginEmployee(View view)
    {
        Intent intent=new Intent(getApplicationContext(),GetnumberActivity.class);
        intent.putExtra("login","employee");
        startActivity(intent);
    }
}