package com.example.docappli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Objects;

public class MakeAppointmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = MakeAppointmentActivity.class.getName();
    private FirebaseUser user;

    String loggedInUser;

    EditText valueET;
    TextView dateChosenTV;
    TextView timeChosenTV;

    private FirebaseFirestore myStore;
    private CollectionReference myUsers;
    private CollectionReference allAppointments;

    private Notification myNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        myStore = FirebaseFirestore.getInstance();
        myUsers = myStore.collection("Users");
        allAppointments = myStore.collection("Appointments");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user");
            loggedInUser = user.getUid();
        } else {
            Log.d(LOG_TAG, "Unauthenticated user");
            finish();
        }

        dateChosenTV = findViewById(R.id.dateChosenTextView);
        timeChosenTV = findViewById(R.id.timeChosenTextView);


        myNotification = new Notification(this);
    }

    public void makeAppointment(View view) {

        if (dateChosenTV.getText().toString().isEmpty()
                || timeChosenTV.getText().toString().isEmpty()) {

            new AlertDialog.Builder(this)
                    .setTitle("Make Appointment").setMessage("Please set the date and time of you appointment!")
                    .setPositiveButton("OK", null).show();
        }
        else {
            String date = dateChosenTV.getText().toString();
            String time = timeChosenTV.getText().toString();


            Appointment appointment = new Appointment(loggedInUser, time, date);
            allAppointments.add(appointment);
            myNotification.send("Appointment made!");

        }
    }


    public void pickDate(View view) {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MakeAppointmentActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        dateChosenTV.setText(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void pickTime(View view) {
        final Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MakeAppointmentActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        timeChosenTV.setText(time);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = adapterView.getItemAtPosition(i).toString();
        Log.i(LOG_TAG, selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}