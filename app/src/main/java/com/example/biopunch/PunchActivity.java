package com.example.biopunch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PunchActivity extends AppCompatActivity {
     String no;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);
        no=getIntent().getStringExtra("phoneNumber");
        try {
            Executor newExecutor = Executors.newSingleThreadExecutor();
            FragmentActivity activity = this;

            final BiometricPrompt myBiometricPrompt = new BiometricPrompt(activity, newExecutor, new BiometricPrompt.AuthenticationCallback() {
                @Override

//onAuthenticationError is called when a fatal error occurs//

                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    if (errorCode == BiometricPrompt.ERROR_NO_BIOMETRICS) {
                        new Thread()
                        {
                            public void run()
                            {
                                PunchActivity.this.runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        new AlertDialog.Builder(PunchActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("No fingerprint Registered")
                                                .setMessage("There is no fingerprint registered on this device")
                                                .setPositiveButton("ADD A FINGERPRINT",null)
                                                .setNegativeButton("GO BACK",null)
                                                .show();

                                        //Do your UI operations like dialog opening or Toast here
                                    }
                                });
                            }
                        }.start();
                        //  Intent i = new Intent(getApplicationContext(), DashBoardHR.class);
                       // i.putExtra("phoneNumber",no+" false");
                        //startActivity(i);
                    } else {
                       // Toast.makeText(ge, "Error occurred!! Try again", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), DashBoardHR.class);

                        i.putExtra("phoneNumber",no);
                        startActivity(i);
                        Log.i("error", "An unrecoverable error occurred");
                    }
                }

//onAuthenticationSucceeded is called when a fingerprint is matched successfully//

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);

//Print a message to Logcat//

                    Log.d("recognised", "Fingerprint recognised successfully");
                }

//onAuthenticationFailed is called when the fingerprint doesn’t match//

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();

//Print a message to Logcat//

                    Log.d("recognition", "Fingerprint not recognised");
                }
            });

//Create the BiometricPrompt instance//

            final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()

//Add some text to the dialog//

                    .setTitle("FingerPrint Authentication!!")
                    .setSubtitle("Authentication required to punch")
                    .setNegativeButtonText("Cancel")

//Build the dialog//

                    .build();

//Assign an onClickListener to the app’s “Authentication” button//


            myBiometricPrompt.authenticate(promptInfo);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}