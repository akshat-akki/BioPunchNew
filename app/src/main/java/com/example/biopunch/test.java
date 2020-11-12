package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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