package com.example.biopunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class WorkTiming extends AppCompatActivity {
    //button to select
    RadioButton time1;
    RadioButton time2;
    RadioButton time3;
  //  RadioButton time4;
    int selected=1;
    int greenColor;
    int greyColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_timing);

        time1=findViewById(R.id.time1);
        time2=findViewById(R.id.time2);
        time3=findViewById(R.id.time3);
        //time4
        greenColor = Color.parseColor("#46E412");
        greyColor = Color.parseColor("#837F7F");
    }
    public void time1clicked(View view)
    {
        if(selected!=1)
        {
            time1.setButtonTintList(ColorStateList.valueOf(greenColor));
            if(selected==2)
                time2.setButtonTintList(ColorStateList.valueOf(greyColor));
            if(selected==3)
                time3.setButtonTintList(ColorStateList.valueOf(greyColor));
          //  if(selected==4)
            //    time4.setButtonTintList(ColorStateList.valueOf(greyColor));
            selected=1;
        }
    }
    public void time2clicked(View view)
    {
        if(selected!=2)
        {
            time2.setButtonTintList(ColorStateList.valueOf(greenColor));
            if(selected==1)
                time1.setButtonTintList(ColorStateList.valueOf(greyColor));
            if(selected==3)
                time3.setButtonTintList(ColorStateList.valueOf(greyColor));
           // if(selected==4)
             //   time4.setButtonTintList(ColorStateList.valueOf(greyColor));
            selected=2;
        }
    }
    public void time3clicked(View view)
    {
        if(selected!=3)
        {
            time3.setButtonTintList(ColorStateList.valueOf(greenColor));
            if(selected==1)
                time1.setButtonTintList(ColorStateList.valueOf(greyColor));
            if(selected==2)
                time2.setButtonTintList(ColorStateList.valueOf(greyColor));
            //if(selected==4)
              //  time4.setButtonTintList(ColorStateList.valueOf(greyColor));
            selected=3;
        }
    }
}