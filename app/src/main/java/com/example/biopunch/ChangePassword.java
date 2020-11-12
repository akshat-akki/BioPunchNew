package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePassword extends AppCompatActivity {

    EditText change;
    EditText newP;
    EditText confirm;
    String mobileNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent i=getIntent();
        mobileNo=i.getStringExtra("phoneNumber");
        Toast.makeText(this, mobileNo, Toast.LENGTH_SHORT).show();
        findViewById(R.id.changePasswordButton).setVisibility(View.INVISIBLE);
       change=(EditText)findViewById(R.id.currentPassword);
       newP=(EditText)findViewById(R.id.newPassword);
       confirm=(EditText)findViewById(R.id.confirmPassword);
       change.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
             //check if password entered is correct
              // if incorrect do this:
              //findViewById(R.id.wrongPassword).setVisibility(View.VISIBLE);
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
       newP.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
       confirm.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
            //see if newP and confirm matches
               //if not do this:
               //findViewById(R.id.confirmPasswordWrong).setVisibility(View.VISIBLE);
               //then make the button visible
               //and change the new password
               findViewById(R.id.changePasswordButton).setVisibility(View.VISIBLE);
               findViewById(R.id.changePasswordButton).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                   }
               });
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
    }

}