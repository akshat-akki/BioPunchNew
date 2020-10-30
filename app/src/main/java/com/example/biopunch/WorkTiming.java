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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class WorkTiming extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
  private  String time4hr[];
    private  String time4min[];
   private ArrayAdapter arrayAdapterTime4InHr;
    private ArrayAdapter arrayAdapterTime4InMin;
    private ArrayAdapter arrayAdapterTime4OutHr;
    private  ArrayAdapter arrayAdapterTime4OutMin;
    private Spinner time4inHr;
    private Spinner time4inMin;
    private Spinner time4outHr;
    private Spinner time4outMin;
    //button to select
    private RadioButton time1;
    private RadioButton time2;
    private RadioButton time3;
    private RadioButton time4;
    private Button addWorkTime;
    private Button nextTime;
    private Button addNow;
    private ImageButton crossButton;
    private ImageButton crossButtonTime4;
    //textViews
    private TextView time4in;
    private TextView time4out;
    private int selected=1;
    private int greenColor;
    private int greyColor;
    private String inTime;
    private String outTime;
    private String phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_timing);
        time1=findViewById(R.id.time1);
        time2=findViewById(R.id.time2);
        time3=findViewById(R.id.time3);
        addNow=findViewById(R.id.addButton);
        addWorkTime=findViewById(R.id.addWorkTiming);
        nextTime=findViewById(R.id.WorkTimingDone);
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
        //initialising the arrays
        time4hr = new String[24];
        time4min = new String[60];
        for (int i = 0; i < 60; i++) {
            String a = String.valueOf(i);
            if (i < 10)
                a = "0" + a;
            if (i < 24)
                time4hr[i] = a;
            time4min[i] = a;
        }
        //in: hour
        time4inHr = findViewById(R.id.time4inHr);
        time4inHr.setOnItemSelectedListener(this);
        arrayAdapterTime4InHr = new ArrayAdapter(WorkTiming.this, android.R.layout.simple_spinner_item, time4hr);
        arrayAdapterTime4InHr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time4inHr.setAdapter(arrayAdapterTime4InHr);
        //in: min
        time4inMin = findViewById(R.id.time4inMin);
        time4inMin.setOnItemSelectedListener(this);
        arrayAdapterTime4InMin = new ArrayAdapter(WorkTiming.this, android.R.layout.simple_spinner_item, time4min);
        arrayAdapterTime4InMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time4inMin.setAdapter(arrayAdapterTime4InMin);
        //out: hour
        time4outHr = findViewById(R.id.time4outHr);
        time4outHr.setOnItemSelectedListener(this);
        arrayAdapterTime4OutHr = new ArrayAdapter(WorkTiming.this, android.R.layout.simple_spinner_item, time4hr);
        arrayAdapterTime4OutHr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time4outHr.setAdapter(arrayAdapterTime4OutHr);
        //out: min
        time4outMin = findViewById(R.id.time4outMin);
        time4outMin.setOnItemSelectedListener(this);
        arrayAdapterTime4OutMin = new ArrayAdapter(WorkTiming.this, android.R.layout.simple_spinner_item, time4min);
        arrayAdapterTime4OutMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time4outMin.setAdapter(arrayAdapterTime4OutMin);
    }

    public void nextClicked(View view)
    {

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
            inTime=time4in.getText().toString().substring(3);
            outTime=time4out.getText().toString().substring(4);

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
        time4in.setText("");
        time4out.setText("");
        time4.setText("");
    }
    public void crossClicked(View view)
    {
        //didn't choose any time
         time4inHr.setVisibility(View.GONE);
         time4inMin.setVisibility(View.GONE);
         time4outHr.setVisibility(View.GONE);
         time4outMin.setVisibility(View.GONE);
        findViewById(R.id.Colon1Work).setVisibility(View.GONE);
        findViewById(R.id.Colon2Work).setVisibility(View.GONE);
        nextTime.setClickable(true);
        nextTime.setAlpha(1);
        addNow.setVisibility(View.GONE);
        findViewById(R.id.customTime).setVisibility(View.GONE);
        findViewById(R.id.time4inTextView).setVisibility(View.GONE);
        findViewById(R.id.time4outTextView).setVisibility(View.GONE);
        crossButton.setVisibility(View.GONE);
        time1.setClickable(true);
        time2.setClickable(true);
        time3.setClickable(true);
        findViewById(R.id.WorkTimingTextView).setAlpha(1);
        time1.setAlpha(1);
        time2.setAlpha(1);
        time3.setAlpha(1);
        findViewById(R.id.time1in).setAlpha(1);
        findViewById(R.id.time1out).setAlpha(1);
        findViewById(R.id.time2in).setAlpha(1);
        findViewById(R.id.time2out).setAlpha(1);
        findViewById(R.id.time3in).setAlpha(1);
        findViewById(R.id.time3out).setAlpha(1);
        addWorkTime.setAlpha(1);
        addWorkTime.setClickable(true);
    }
    public void addWorkTimingClicked(View view)
    {
        time1.setAlpha((float)0.05);
        time2.setAlpha((float)0.05);
        time3.setAlpha((float)0.05);
        crossButton.setVisibility(View.VISIBLE);
        crossButton.setClickable(true);
        time1.setClickable(false);
        time2.setClickable(false);
        time3.setClickable(false);
        addWorkTime.setAlpha((float)0.05);
        addWorkTime.setClickable(false);
        nextTime.setClickable(false);
        nextTime.setAlpha((float)0.05);
        findViewById(R.id.time1in).setAlpha((float)0.05);
        findViewById(R.id.time1out).setAlpha((float)0.05);
        findViewById(R.id.time2in).setAlpha((float)0.05);
        findViewById(R.id.time2out).setAlpha((float)0.05);
        findViewById(R.id.time3in).setAlpha((float)0.05);
        findViewById(R.id.time3out).setAlpha((float)0.05);
        findViewById(R.id.WorkTimingTextView).setAlpha((float)0.05);
        findViewById(R.id.customTime).setVisibility(View.VISIBLE);
                addNow.setVisibility(View.VISIBLE);
                addNow.setClickable(true);
        findViewById(R.id.time4inTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.time4outTextView).setVisibility(View.VISIBLE);
        time4outHr.setVisibility(View.VISIBLE);
        time4outMin.setVisibility(View.VISIBLE);
        time4inHr.setVisibility(View.VISIBLE);
        time4inMin.setVisibility(View.VISIBLE);
        time4outHr.setClickable(true);
        time4outMin.setClickable(true);
        time4inHr.setClickable(true);
        time4inMin.setClickable(true);
        findViewById(R.id.Colon1Work).setVisibility(View.VISIBLE);
        findViewById(R.id.Colon2Work).setVisibility(View.VISIBLE);
        addNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inHr=time4inHr.getSelectedItem().toString();
                String inMin=time4inMin.getSelectedItem().toString();
                String outHr=time4outHr.getSelectedItem().toString();
                String outMin=time4outMin.getSelectedItem().toString();
                if(inHr!=null&&inMin!=null&&outHr!=null&&outMin!=null)
                {
                    String x="";
                    if(inHr.charAt(0)=='0')
                      x=inHr.charAt(1)+x;
                    else
                    {
                        if(Integer.parseInt(inHr)>12)
                            x=String.valueOf(Integer.parseInt(inHr)-12);
                            else
                             x=inHr+x;
                    }
                    if(inMin.equals("00")==false)
                        x=x+":"+inMin;
                    if(outHr.charAt(0)=='0')
                        x=x+" TO "+outHr.charAt(1);
                    else
                    {
                        if(Integer.parseInt(outHr)>12)
                            x=x+" TO "+String.valueOf(Integer.parseInt(outHr)-12);
                        else
                            x=x+" TO "+outHr;
                    }
                    if(outMin.equals("00")==false)
                        x=x+":"+outMin;
                    findViewById(R.id.customTime).setVisibility(View.GONE);
                    findViewById(R.id.time4inTextView).setVisibility(View.GONE);
                    findViewById(R.id.time4outTextView).setVisibility(View.GONE);
                    crossButton.setVisibility(View.GONE);
                    crossButtonTime4.setVisibility(View.VISIBLE);
                    time4.setText(x);
                    addNow.setVisibility(View.GONE);
                    time4.setAlpha(1);
                    time4.setVisibility(View.VISIBLE);
                    addWorkTime.setVisibility(View.GONE);
                    nextTime.setAlpha(1);
                    nextTime.setClickable(true);
                    time1.setClickable(true);
                    time2.setClickable(true);
                    time3.setClickable(true);
                    findViewById(R.id.WorkTimingTextView).setAlpha(1);
                    time1.setAlpha(1);
                    time2.setAlpha(1);
                    time3.setAlpha(1);
                    findViewById(R.id.time1in).setAlpha(1);
                    findViewById(R.id.time1out).setAlpha(1);
                    findViewById(R.id.time2in).setAlpha(1);
                    findViewById(R.id.time2out).setAlpha(1);
                    findViewById(R.id.time3in).setAlpha(1);
                    findViewById(R.id.time3out).setAlpha(1);
                    String in="IN "+inHr+":"+inMin;
                    String out="OUT "+outHr+":"+outMin;
                    Toast.makeText(WorkTiming.this, out, Toast.LENGTH_SHORT).show();
                    time4in.setText(in);
                    time4out.setText(out);
                    time4in.setAlpha(1);
                    time4out.setVisibility(View.VISIBLE);
                    time4out.setAlpha(1);
                    time4inHr.setVisibility(View.GONE);
                    time4inMin.setVisibility(View.GONE);
                    time4outHr.setVisibility(View.GONE);
                    time4outMin.setVisibility(View.GONE);
                    findViewById(R.id.Colon1Work).setVisibility(View.GONE);
                    findViewById(R.id.Colon2Work).setVisibility(View.GONE);
                    time4in.setVisibility(View.VISIBLE);
                    time4out.setVisibility(View.VISIBLE);
                    time4clicked(time4);
                }
                else
                {
                    TextView customTime=findViewById(R.id.customTime);
                    customTime.setText("    Enter your working time in 24 hr format");
                    int redColor=Color.parseColor("#FD0909");
                    customTime.setTextColor(redColor);
                }
        }

    });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}