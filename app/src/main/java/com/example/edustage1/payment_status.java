package com.example.edustage1;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class payment_status extends AppCompatActivity {

    String status, payment_id_num, message, date, time, payment_msg;
    TextView thank_you, oops, line, line1, payment_id, payment_id_no, failure_msg, failure_msg1, line2, payment_failure_id_no;
    ImageView success_img, failure_img;
    Button continue_btn;
    LinearLayout payment_failure_id;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_status);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        thank_you = findViewById(R.id.thank_you);
        oops = findViewById(R.id.oops);
        line = findViewById(R.id.line);
        line1 = findViewById(R.id.line1);
        payment_id = findViewById(R.id.payment_id);
        payment_id_no = findViewById(R.id.payment_id_no);
        failure_msg = findViewById(R.id.failue_msg);
        failure_msg1 = findViewById(R.id.failure_msg1);
        line2 = findViewById(R.id.line2);
        payment_failure_id_no = findViewById(R.id.payment_failure_id_no);
        success_img = findViewById(R.id.success_img);
        failure_img = findViewById(R.id.failure_img);
        continue_btn = findViewById(R.id.continue_btn);
        payment_failure_id = findViewById(R.id.payment_failure_id);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        TextView queries = findViewById(R.id.queries);
        String text = "For any queries write a mail to: info@edustage.in.";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:info@edustage.in")));
            }
        };

        ss.setSpan(clickableSpan,33,49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.rgb(0,29,59)), 33, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        queries.setText(ss);
        queries.setMovementMethod(LinkMovementMethod.getInstance());

        Bundle bundle = getIntent().getExtras();
        status = bundle.getString("Status");
        payment_id_num = bundle.getString("Payment ID");
        date = bundle.getString("Date");
        time = bundle.getString("Time");

        if(status.equals("Success")){

            payment_msg = bundle.getString("Message");
            message = payment_msg+"\nCongrats! Your meeting is scheduled. Meeting date:"+date+", Meeting Time:"+time+".";

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                }
            }

            payment_id_no.setText(payment_id_num);

            thank_you.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            success_img.setVisibility(View.VISIBLE);
            payment_id.setVisibility(View.VISIBLE);
            payment_id_no.setVisibility(View.VISIBLE);

            saveData(payment_id_num, status, date, time);

        }else{

            payment_failure_id_no.setText(payment_id_num);

            oops.setVisibility(View.VISIBLE);
            line1.setVisibility(View.VISIBLE);
            failure_img.setVisibility(View.VISIBLE);
            failure_msg.setVisibility(View.VISIBLE);
            payment_failure_id.setVisibility(View.VISIBLE);
            failure_msg1.setVisibility(View.VISIBLE);

            saveData(payment_id_num, status, date, time);

        }

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(payment_status.this, Home.class));
                finish();
            }
        });

    }

    private void saveData(String payment_id_num, String status, String date, String time) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
        String current_date = simpleDateFormat.format(calendar.getTime());

        Map<String, Object> user = new HashMap<>();
        user.put("PAYMENT TD", payment_id_num);
        user.put("STATUS", status);
        user.put("DATE", date);
        user.put("TIME", time);

        fstore.collection("Users").document(fAuth.getCurrentUser().getPhoneNumber()).collection("Meetings").document("Meeting - "+current_date).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(payment_status.this, "Submitted!", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager sms = SmsManager.getDefault();
                    ArrayList<String> parts = sms.divideMessage(message);

                    ArrayList<PendingIntent> sendList = new ArrayList<>();
                    sendList.add(null);

                    ArrayList<PendingIntent> deliverList = new ArrayList<>();
                    deliverList.add(null);

                    sms.sendMultipartTextMessage(fAuth.getCurrentUser().getPhoneNumber(), null, parts, sendList, deliverList);
                } else {
                    Toast.makeText(getApplicationContext(), "Sms permission denied", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}
