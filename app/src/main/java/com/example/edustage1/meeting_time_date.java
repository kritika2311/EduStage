package com.example.edustage1;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chinodev.androidneomorphframelayout.NeomorphFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.Payment;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.app.Fragment;
import android.app.DialogFragment;
import java.util.Calendar;
import android.widget.TimePicker;

public class meeting_time_date extends AppCompatActivity implements PaymentResultListener {

    NeomorphFrameLayout backto_home;
    EditText dateformat, timeformat;
    String date, time;
    Button proceedtopay_btn;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    private String TAG="TAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_time_date);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Checkout.preload(getApplicationContext());

        backto_home = findViewById(R.id.backto_home);
        dateformat = findViewById(R.id.select_date);
        timeformat = findViewById(R.id.select_time);
        proceedtopay_btn = findViewById(R.id.proceedtopay_btn);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        dateformat.setInputType(InputType.TYPE_NULL);
        timeformat.setInputType(InputType.TYPE_NULL);

        backto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(meeting_time_date.this, Home.class));
                finish();
            }
        });

        dateformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(dateformat);
            }
        });

        timeformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(timeformat);
            }
        });

        proceedtopay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!dateformat.getText().toString().isEmpty() && !timeformat.getText().toString().isEmpty()){

                    date = dateformat.getText().toString().trim();
                    time = timeformat.getText().toString().trim();

                    setUpPayment();
                }else{
                    return;
                }
            }
        });

    }

    private void setUpPayment(){

        Checkout checkout = new Checkout();
        checkout.setImage(R.mipmap.logo);
        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();

            options.put("name", "Edustage");
            options.put("description", "Meeting Schedule Payment");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#BCDBEF");
            options.put("currency", "INR");
            options.put("amount", "50000");
            options.put("prefill.email", "example@gmail.com");
            options.put("prefill.contact", "+919063034608");
            checkout.open(activity, options);
        } catch(Exception e) {
            Toast.makeText(activity, "Error in payment: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void showDateDialog(EditText dateformat) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(meeting_time_date.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);

                dateformat.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
            }
        }, year, month, day
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    private void showTimeDialog(EditText timeformat) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");

                timeformat.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new TimePickerDialog(meeting_time_date.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    @Override
    public void onPaymentSuccess(String s) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String Currentdate = simpleDateFormat.format(calendar.getTime());

        String message1 = "Edustage - Thank you for payment! Your Payment ID: "+s+", Amount: ₹500, Date: "+Currentdate+", Transaction Status: Success.";

        Intent intent = new Intent(meeting_time_date.this, payment_status.class);
        intent.putExtra("Status", "Success");
        intent.putExtra("Payment ID", s);
        intent.putExtra("Date", date);
        intent.putExtra("Time", time);
        intent.putExtra("Message", message1);
        startActivity(intent);
        finish();
        ;
    }

    @Override
    public void onPaymentError(int i, String s) {
        Intent intent = new Intent(meeting_time_date.this, payment_status.class);
        intent.putExtra("Status", "Failure");
        intent.putExtra("Payment ID", s);
        intent.putExtra("Date", date);
        intent.putExtra("Time", time);
        startActivity(intent);
        finish();
    }
}
