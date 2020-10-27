package com.example.biopunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class WorkTiming extends AppCompatActivity {
    //button to select
    private RadioButton time1;
    private RadioButton time2;
    private RadioButton time3;
    private RadioButton time4;
    private Button addWorkTime;
    private Button nextTime;
    private EditText timeOutEditText;
    private Button addNow;
    private ImageButton crossButton;
    private ImageButton crossButtonTime4;
    //textViews
    private TextView time4in;
    private EditText timeInEditText;
    private TextView customTime;
    private TextView time4out;
    private TextView workTiming;
    private TextView time1in;
    private TextView time1out;
    private TextView time2in;
    private TextView time2out;
    private TextView time3in;
    private TextView time3out;
    private int selected=1;
    private int greenColor;
    private int greyColor;
    private String inTime;
    private String outTime;
    private String phoneno;
    public String workTimeArray[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_timing);
        workTiming=findViewById(R.id.WorkTimingTextView);
        time1in=findViewById(R.id.time1in);
        time2in=findViewById(R.id.time2in);
        time3in=findViewById(R.id.time3in);
        time1=findViewById(R.id.time1);
        time2=findViewById(R.id.time2);
        customTime= findViewById(R.id.customTime);
        time3=findViewById(R.id.time3);
        addNow=findViewById(R.id.addButton);
        addWorkTime=findViewById(R.id.addWorkTiming);
        nextTime=findViewById(R.id.WorkTimingDone);
        time1out=findViewById(R.id.time1out);
        time2out=findViewById(R.id.time2out);
        time3out=findViewById(R.id.time3out);
        timeInEditText=(EditText)findViewById(R.id.time4inEditText);
        timeOutEditText=(EditText)findViewById(R.id.time4outEditText);
        crossButton=findViewById(R.id.crossButton);
        crossButtonTime4=findViewById(R.id.crossButtonTime4);
        //time4
        time4=findViewById(R.id.time4);
        time4in=findViewById(R.id.time4in);
        time4out=findViewById(R.id.time4out);
        //color
        greenColor = Color.parseColor("#46E412");
        greyColor = Color.parseColor("#837F7F");
        phoneno=getIntent().getStringExtra("mobile");
        workTimeArray=new String[4];
        workTimeArray[0]="9 TO 6";
        workTimeArray[1]="8 TO 5";
        workTimeArray[2]="10 TO 7";
    }

    public void nextClicked(View view)
    {
        //workTimeArray[3]=inTime+
        FirebaseDatabase.getInstance().getReference().child("users").child(phoneno).child("WorkTimeIn").setValue(inTime);
        FirebaseDatabase.getInstance().getReference().child("users").child(phoneno).child("WorkTimeOut").setValue(outTime);
        Intent intent=new Intent(getApplicationContext(),DashBoardHR.class);
        startActivity(intent);
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
            if(selected==4)
                time4.setButtonTintList(ColorStateList.valueOf(greyColor));
            selected=1;
            inTime="09:00";
            outTime="18:00";
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
            if(selected==4)
                time4.setButtonTintList(ColorStateList.valueOf(greyColor));
            selected=2;
            inTime="08:00";
            outTime="17:00";
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
            if(selected==4)
                time4.setButtonTintList(ColorStateList.valueOf(greyColor));
            selected=3;
            inTime="10:00";
            outTime="19:00";
        }
    }
    public void time4clicked(View view)
    {
        if(selected!=4)
        {
            time4.setButtonTintList(ColorStateList.valueOf(greenColor));
            if(selected==1)
                time1.setButtonTintList(ColorStateList.valueOf(greyColor));
            if(selected==2)
                time2.setButtonTintList(ColorStateList.valueOf(greyColor));
            if(selected==3)
                time3.setButtonTintList(ColorStateList.valueOf(greyColor));
            selected=4;
            inTime=timeInEditText.getText().toString();
            outTime=timeOutEditText.getText().toString();

        }
    }

    public void crossTime4clicked(View view)
    {
        time4.setVisibility(View.GONE);
        time4in.setVisibility(View.GONE);
        time4out.setVisibility(View.GONE);
        crossButtonTime4.setVisibility(View.GONE);
        addWorkTime.setClickable(true);
        addWorkTime.setVisibility(View.VISIBLE);
        addWorkTime.setAlpha(1);
        time1clicked(time1);
        timeInEditText.setText("");
        timeOutEditText.setText("");
        time4in.setText("");
        time4out.setText("");
        time4.setText("");
    }
    public void crossClicked(View view)
    {
        nextTime.setClickable(true);
        nextTime.setAlpha(1);
        time4in.setText("");
        time4out.setText("");
        time4.setText("");
        timeInEditText.setText("");
        timeOutEditText.setText("");
        addNow.setVisibility(View.GONE);
        customTime.setVisibility(View.GONE);
        timeInEditText.setVisibility(View.GONE);
        timeOutEditText.setVisibility(View.GONE);
        crossButton.setVisibility(View.GONE);
        time1.setClickable(true);
        time2.setClickable(true);
        time3.setClickable(true);
        workTiming.setAlpha(1);
        time1.setAlpha(1);
        time2.setAlpha(1);
        time3.setAlpha(1);
        time1in.setAlpha(1);
        time1out.setAlpha(1);
        time2in.setAlpha(1);
        time2out.setAlpha(1);
        time3in.setAlpha(1);
        time3out.setAlpha(1);
        addWorkTime.setAlpha(1);
        addWorkTime.setClickable(true);
        nextTime.setAlpha(1);
        nextTime.setClickable(true);
    }
    public void addWorkTimingClicked(View view)
    {
        time1.setAlpha((float)0.05);
        time2.setAlpha((float)0.05);
        time3.setAlpha((float)0.05);
        crossButton.setVisibility(View.VISIBLE);
        time1.setClickable(false);
        time2.setClickable(false);
        time3.setClickable(false);
        addWorkTime.setAlpha((float)0.05);
        addWorkTime.setClickable(false);
        nextTime.setClickable(false);
        nextTime.setAlpha((float)0.05);
        time1in.setAlpha((float)0.05);
        time1out.setAlpha((float)0.05);
        time2in.setAlpha((float)0.05);
        time2out.setAlpha((float)0.05);
        time3in.setAlpha((float)0.05);
        time3out.setAlpha((float)0.05);
        workTiming.setAlpha((float)0.05);
                customTime.setVisibility(View.VISIBLE);
                customTime.setAlpha(1);
                addNow.setVisibility(View.VISIBLE);
                addNow.setClickable(true);
        timeInEditText.setClickable(true);
        timeOutEditText.setClickable(true);
        timeInEditText.setVisibility(View.VISIBLE);
        timeOutEditText.setVisibility(View.VISIBLE);
        timeInEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null&& s.length()>0)
                {
                    time4in.setText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        timeOutEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null&& s.length()>0)
                {
                    time4out.setText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in=time4in.getText().toString();
                String out=time4out.getText().toString();
                if(in!=null&&in.length()>=1&&out!=null&&out.length()>=1)
                {
                    customTime.setVisibility(View.GONE);
                    timeInEditText.setVisibility(View.GONE);
                    timeOutEditText.setVisibility(View.GONE);
                    crossButton.setVisibility(View.GONE);
                    crossButtonTime4.setVisibility(View.VISIBLE);
                    if(in.length()<=2&&out.length()<=2)
                    {int a=Integer.parseInt(in);
                    int b=Integer.parseInt(out);
                    String a1="";
                    String a2="";
                    if(a>12)
                        a1=String.valueOf(a-12);
                    else
                        a1=String.valueOf(a);
                    if(b>12)
                        a2=String.valueOf(b-12);
                    else
                        a2=String.valueOf(b);
                    String x=a1+" TO "+a2;
                    if(a>12 && b>12)
                    {
                      x+=" (NIGHT SHIFT)";
                    }
                    time4.setText(x);
                    if(in.length()==1)
                        in="0"+in+":00";
                    if(in.length()==2)
                        in=in+":00";
                    if(out.length()==1)
                        out="0"+out+":00";
                    if(out.length()==2)
                        out=out+":00";}
                    else if(in.length()<=5&&out.length()<=5)
                    {
                        int a=Integer.parseInt(in.substring(0,in.indexOf(":")));
                        int b=Integer.parseInt(out.substring(0,out.indexOf(":")));
                        String a1="";
                        String a2="";
                        if(a1.length()==1)
                            in="0"+in;
                        if(a2.length()==1)
                            out="0"+out;
                        if(a>12)
                            a1=String.valueOf(a-12);
                        else
                            a1=String.valueOf(a);
                        if(b>12)
                            a2=String.valueOf(b-12);
                        else
                            a2=String.valueOf(b);
                        String x=a1+":"+in.substring(in.indexOf(":")+1)+" TO "+a2+":"+out.substring(out.indexOf(":")+1);
                        if(a>12 && b>12)
                        {
                            x+=" (NIGHT SHIFT)";
                        }
                        time4.setText(x);
                    }
                    in="IN "+in;
                    out="OUT "+out;
                    addNow.setVisibility(View.GONE);
                    time4.setAlpha(1);
                    time4.setVisibility(View.VISIBLE);
                    addWorkTime.setVisibility(View.GONE);
                    nextTime.setAlpha(1);
                    nextTime.setClickable(true);
                    time1.setClickable(true);
                    time2.setClickable(true);
                    time3.setClickable(true);
                    workTiming.setAlpha(1);
                    time1.setAlpha(1);
                    time2.setAlpha(1);
                    time3.setAlpha(1);
                    time1in.setAlpha(1);
                    time1out.setAlpha(1);
                    time2in.setAlpha(1);
                    time2out.setAlpha(1);
                    time3in.setAlpha(1);
                    time3out.setAlpha(1);
                    time4in.setText(in);
                    time4out.setText(out);
                    time4in.setAlpha(1);
                    time4out.setAlpha(1);
                    time4in.setVisibility(View.VISIBLE);
                    time4out.setVisibility(View.VISIBLE);
                    time4clicked(time4);
                }
                else
                {
                    customTime.setText("    Enter your working time in 24 hr format");
                    int redColor=Color.parseColor("#FD0909");
                    customTime.setTextColor(redColor);
                }
            }
        });

    }
}