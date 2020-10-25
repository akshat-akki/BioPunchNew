package com.example.biopunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
    Button next1;
    EditText companyName;
    EditText password;
    EditText companyEmailAddress;
    EditText contactPerson;
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
        serial1=findViewById(R.id.sn1);
        serial2=findViewById(R.id.sn2);
        serial3=findViewById(R.id.sn3);
        serial4=findViewById(R.id.sn4);
        next1=findViewById(R.id.next1);
        companyName=(EditText)findViewById(R.id.companyNameEditText);
        password=(EditText)findViewById(R.id.passwordEditText);
        companyEmailAddress=(EditText)findViewById(R.id.companyEmailEditText);
        contactPerson=(EditText)findViewById(R.id.contactPersonEditText);
        companyName.requestFocus();
        inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputMethodManager.showSoftInput(companyName, InputMethodManager.SHOW_IMPLICIT);
        forceShow(ProfileActivity.this);
        Intent intent=getIntent();
        UID=intent.getStringExtra("uid");

    }
    public void next1clicked(View view)
   {
       FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("CompanyName").setValue(companyName.getText().toString());
       //when companyName field is completed
       serial1.setBackground(ContextCompat.getDrawable(ProfileActivity.this,R.color.colorPrimaryDark));
       serial1.setTextColor(Color.parseColor("#ffffffff"));
       password.requestFocus();
       forceShow(ProfileActivity.this);
      // inputMethodManager.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);
   }
    public void next2clicked(View view)
    {
        //when password field is completed
        FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("Password").setValue(password.getText().toString());
        serial2.setBackground(ContextCompat.getDrawable(ProfileActivity.this,R.color.colorPrimaryDark));
        serial2.setTextColor(Color.parseColor("#ffffffff"));
        companyEmailAddress.requestFocus();
        forceShow(ProfileActivity.this);
        // inputMethodManager.showSoftInput(companyEmailAddress, InputMethodManager.SHOW_IMPLICIT);
    }
    public void next3clicked(View view)
    {
        //when companyEmailAddress field is completed
        FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("Email").setValue(companyEmailAddress.getText().toString());
        serial3.setBackground(ContextCompat.getDrawable(ProfileActivity.this,R.color.colorPrimaryDark));
        serial3.setTextColor(Color.parseColor("#ffffffff"));
        contactPerson.requestFocus();
        forceShow(ProfileActivity.this);
        // inputMethodManager.showSoftInput(contactPerson, InputMethodManager.SHOW_IMPLICIT);
    }
    public void next4clicked(View view)
    {
        //when contactPerson field is completed
        FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("ContactPerson").setValue(contactPerson.getText().toString());
        serial4.setBackground(ContextCompat.getDrawable(ProfileActivity.this,R.color.colorPrimaryDark));
        serial4.setTextColor(Color.parseColor("#ffffffff"));
        forceHide(ProfileActivity.this,contactPerson);
        Toast.makeText(ProfileActivity.this, "Profile completed!!", Toast.LENGTH_SHORT).show();

    }
}