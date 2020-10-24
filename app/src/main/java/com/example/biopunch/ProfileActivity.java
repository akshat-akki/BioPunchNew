package com.example.biopunch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
    }
    public void onClick(View view)
    {
        String a=view.getTag().toString();
        if(a=="1")
        {
           b1.setBackgroundColor(272777);
        }
        if(a=="2")
        {
            b2.setBackgroundColor(272777);
        }
        if(a=="3")
        {
            b3.setBackgroundColor(272777);
        }
        if(a=="4")
        {
            b4.setBackgroundColor(272777);
        }
    }
}