package com.example.biopunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    String verificationCodeBySystem;

    //The edittext to input the code
    EditText editTextCode;
    ProgressBar progressBar;
    Button button;
    //firebase auth object
    private FirebaseAuth mAuth;
    String mobile;
    private boolean role,activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.editTextCode);
        progressBar = findViewById(R.id.progressbar);
        button = findViewById(R.id.signinbutton);
        progressBar.setVisibility(View.GONE);
        //getting mobile number from the previous activity
        //and sending the verification code to the number
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        role=intent.getBooleanExtra("roleHr",true);
        activity=intent.getBooleanExtra("activityPassword",false);
        sendVerificationCodeToUser(mobile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=editTextCode.getText().toString();
                if(code.isEmpty()||code.length()<6)
                {
                    editTextCode.setError("Wrong OTP...");
                    editTextCode.requestFocus();
                    return;
                }
                Log.i("hello","after if");
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });

    }

    private void sendVerificationCodeToUser(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    private void verifyCode(String codeByUser)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
        signInTheUserByCredentials(credential);

    }


    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if(activity==false) {

                                FirebaseDatabase.getInstance().getReference().child("users").child(mobile).child("phone").setValue(mobile);

                                String UID = mobile;
                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                intent.putExtra("phone", UID);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else
                            {
                                if(role==true)
                                {
                                    Intent i=new Intent(getApplicationContext(),ForgotPassword.class);
                                    i.putExtra("role","HR");
                                    i.putExtra("phone",mobile);
                                    startActivity(i);
                                }
                                else
                                {
                                    Intent i=new Intent(getApplicationContext(),ForgotPassword.class);
                                    i.putExtra("role","employee");
                                    i.putExtra("phone",mobile);
                                    startActivity(i);
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}