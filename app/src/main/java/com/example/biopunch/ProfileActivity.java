package com.example.biopunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    //serial number vise buttons
    Button serial1;
    Button serial2;
    Button serial3;
    Button serial4;
    //TextViews
    TextView companyName;
    TextView password;
    TextView companyEmailAddress;
    TextView contactPerson;
    //EditTexts
    EditText companyNameEditText;
    EditText passwordEditText;
    EditText companyEmailAddressEditText;
    EditText contactPersonEditText;
    InputMethodManager inputMethodManager;
    String UID;
    /**
     * Force show softKeyboard.
     */
    public static void forceShow(@NonNull Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Force hide softKeyboard.
     */
    public static void forceHide(@NonNull Activity activity, @NonNull EditText editText) {
        if (activity.getCurrentFocus() == null || !(activity.getCurrentFocus() instanceof EditText)) {
            editText.requestFocus();
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //not calling super ,disables back button
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //buttons
        serial1=findViewById(R.id.sn1);
        serial2=findViewById(R.id.sn2);
        serial3=findViewById(R.id.sn3);
        serial4=findViewById(R.id.sn4);
        //TextViews
        companyName=findViewById(R.id.companyNameText);
        password=findViewById(R.id.passwordText);
        companyEmailAddress=findViewById(R.id.companyEmailText);
        contactPerson=findViewById(R.id.contactPersonText);
       //EditTexts
        companyNameEditText=(EditText)findViewById(R.id.companyNameEditText);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        companyEmailAddressEditText=(EditText)findViewById(R.id.companyEmailEditText);
        contactPersonEditText=(EditText)findViewById(R.id.contactPersonEditText);

        companyNameEditText.requestFocus();
        inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        forceShow(ProfileActivity.this);
        //next1 button
        final Button next1=findViewById(R.id.next1);
        companyNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             if(s!=null && s.length()>1)
             next1.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Intent intent=getIntent();
        UID=intent.getStringExtra("phone");
    }
    public void next1clicked(View view)
   {
       FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("CompanyName").setValue(companyNameEditText.getText().toString());
       //when companyName field is completed
       serial1.setBackground(ContextCompat.getDrawable(ProfileActivity.this,R.color.colorPrimaryDark));
       serial1.setTextColor(Color.parseColor("#ffffffff"));
       serial2.setVisibility(View.VISIBLE);
       password.setVisibility(View.VISIBLE);
       passwordEditText.setVisibility(View.VISIBLE);
       passwordEditText.requestFocus();
       final TextView errorMessage=findViewById(R.id.errorText);
       errorMessage.setVisibility(View.VISIBLE);
       forceShow(ProfileActivity.this);
       //next2 button
       final Button next2=findViewById(R.id.next2);
       passwordEditText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(s!=null && s.length()>=6)
               {
                   errorMessage.setVisibility(View.INVISIBLE);
                   next2.setVisibility(View.VISIBLE);
               }
               if(s!=null && s.length()<6)
               {
                   errorMessage.setVisibility(View.VISIBLE);
                   next2.setVisibility(View.INVISIBLE);
               }
           }

           @Override
           public void afterTextChanged(Editable s) {
           }
       });
   }
    public void next2clicked(View view)
    {
        //when password field is completed
        FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("Password").setValue(passwordEditText.getText().toString());
        serial2.setBackground(ContextCompat.getDrawable(ProfileActivity.this,R.color.colorPrimaryDark));
        serial2.setTextColor(Color.parseColor("#ffffffff"));
        serial3.setVisibility(View.VISIBLE);
        companyEmailAddress.setVisibility(View.VISIBLE);
        companyEmailAddressEditText.setVisibility(View.VISIBLE);
        companyEmailAddressEditText.requestFocus();
        forceShow(ProfileActivity.this);
        //next3 button
        final Button next3=findViewById(R.id.next3);
        companyEmailAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null && s.length()>=7)
                    next3.setVisibility(View.VISIBLE);
                if(s!=null && s.length()<7)
                    next3.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void next3clicked(View view)
    {
        //when companyEmailAddress field is completed
        FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("Email").setValue(companyEmailAddressEditText.getText().toString());
        serial3.setBackground(ContextCompat.getDrawable(ProfileActivity.this,R.color.colorPrimaryDark));
        serial3.setTextColor(Color.parseColor("#ffffffff"));
        serial4.setVisibility(View.VISIBLE);
        contactPerson.setVisibility(View.VISIBLE);
        contactPersonEditText.setVisibility(View.VISIBLE);
        contactPersonEditText.requestFocus();
        forceShow(ProfileActivity.this);
        //next4 button
        final Button next4=findViewById(R.id.next4);
        contactPersonEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null && s.length()>=3)
                    next4.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void next4clicked(View view)
    {
        //when contactPerson field is completed
        FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("ContactPerson").setValue(contactPersonEditText.getText().toString());
        serial4.setBackground(ContextCompat.getDrawable(ProfileActivity.this,R.color.colorPrimaryDark));
        serial4.setTextColor(Color.parseColor("#ffffffff"));
        forceHide(ProfileActivity.this,contactPersonEditText);
        Toast.makeText(ProfileActivity.this, "Profile completed!!", Toast.LENGTH_SHORT).show();
    }
}