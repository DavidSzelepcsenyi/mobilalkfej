package com.example.docappli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HubActivity extends AppCompatActivity  {
    private static final String LOG_TAG = HubActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    final int PERMISSION_REQUEST_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user");
            finish();
        }

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences pref = this.getSharedPreferences("PACKAGE.NAME",MODE_PRIVATE);
        boolean firstTime = pref.getBoolean("firstTime",true);
        if(firstTime){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!shouldShowRequestPermissionRationale("99")){
                    getNotificationPermission();
                }
            }

            pref.edit().putBoolean("firstTime",false).apply();
        }

    }

    public void getNotificationPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    PERMISSION_REQUEST_CODE);
        }
    }




    public void toMakeAppointment(View view) {
        Intent intent = new Intent(this, MakeAppointmentActivity.class);
        startActivity(intent);
    }


    public void signOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("Logout").setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(intent);
                        Log.d(LOG_TAG, "User logged out.");
                    }
                }).setNegativeButton("No", null).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("Logout").setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(intent);
                        Log.d(LOG_TAG, "User logged out.");
                    }
                }).setNegativeButton("No", null).show();
    }
}
