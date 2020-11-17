package com.example.biopunch;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PunchActivity extends AppCompatActivity {
     private String no,hrno;
     boolean empdash;
     static int j=0;
    @Override
    public void onBackPressed() {
        if(j==0) {
            Intent i = new Intent(getApplicationContext(), PunchActivity.class);
            i.putExtra("phoneNumber", no);
            startActivity(i);
            j=1;
        }
        else
        {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);
        no=getIntent().getStringExtra("phoneNumber");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("Employees");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("MyNo").getValue(String.class).equals(no))
                    {
                        hrno=ds.child("HrNo").getValue(String.class);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

        empdash=getIntent().getBooleanExtra("Empdash",false);
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
                                        new Builder(PunchActivity.this).setIcon(android.R.drawable.ic_popup_reminder).setTitle("No fingerprint Registered")
                                                .setMessage("There is no fingerprint registered on this device.Goto Settings->Passwords and Security->Fingerprint Unlock")
                                                .setPositiveButton("ADD A FINGERPRINT", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                                                    }
                                                })
                                                .setNegativeButton("GO BACK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if(empdash==false) {
                                                            Intent i = new Intent(getApplicationContext(), DashBoardHR.class);
                                                            i.putExtra("phoneNumber", no);
                                                            startActivity(i);
                                                        }
                                                        else
                                                        {
                                                            Intent i = new Intent(getApplicationContext(), EmpDashboard.class);
                                                            i.putExtra("phone", no);
                                                            startActivity(i);
                                                        }
                                                    }
                                                })
                                                .show();

                                        //Do your UI operations like dialog opening or Toast here
                                    }
                                });
                            }
                        }.start();
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
                    if(empdash==false) {
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(no)
                                .child("Employee")
                                .child(no)
                                .child("Punched")
                                .setValue("YES");

                        Intent i = new Intent(getApplicationContext(), DashBoardHR.class);
                        i.putExtra("phoneNumber", no);
                        startActivity(i);
                    }
                    else
                    {
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(hrno)
                                .child("Employee")
                                .child(no)
                                .child("Punched")
                                .setValue("YES");
                        Intent i = new Intent(getApplicationContext(), EmpDashboard.class);
                        i.putExtra("phone", no);
                        startActivity(i);
                    }
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