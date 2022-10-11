package com.example.edustage1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chinodev.androidneomorphframelayout.NeomorphFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class otp_auth extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    Button continue_btn;
    NeomorphFrameLayout backto_phoneauth;

    TextView phoneNo, resend_otp_btn;
    EditText otp_enter;
    RelativeLayout resend_otp_question;
    String  phoneNum, countryNameCode, verificationCodeBySystem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_auth);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        continue_btn = findViewById(R.id.continue_btn);
        backto_phoneauth = findViewById(R.id.backto_phoneauth);
        phoneNo = findViewById(R.id.phoneNo);
        resend_otp_btn = findViewById(R.id.resend_otp_btn);
        otp_enter = findViewById(R.id.otp);
        resend_otp_question = findViewById(R.id.resend_otp_question);

        Bundle bundle = getIntent().getExtras();
        phoneNo.setText(bundle.getString("phoneNo"));
        countryNameCode = bundle.getString("countryNameCode");
        phoneNum = bundle.getString("countryCode")+bundle.getString("phoneNo");

        backto_phoneauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(otp_auth.this, phoneno_auth.class));
                finish();
            }
        });

        sendVerificationCodeToUser(phoneNum);

        resend_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCodeToUser(phoneNum);
            }
        });

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = otp_enter.getText().toString();

                if (!code.isEmpty() && code.length() == 6) {
                    verifyCode(code);
                }else{
                    resend_otp_question.setVisibility(View.VISIBLE);
                    return;
                }

            }
        });
    }

    private void sendVerificationCodeToUser(String phoneNum) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(fAuth).setPhoneNumber(phoneNum).setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this).setCallbacks(mCallbacks).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            resend_otp_question.setVisibility(View.VISIBLE);
            Toast.makeText(otp_auth.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String codeByUser) {

        otp_enter.setText(codeByUser);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(otp_auth.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        resend_otp_question.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            checkUserProfile(fAuth.getCurrentUser().getPhoneNumber());
                        } else {
                            resend_otp_question.setVisibility(View.VISIBLE);
                            Toast.makeText(otp_auth.this, "Authentication failed! Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void checkUserProfile(String phoneNumber) {

        fstore.collection("Users").document(phoneNumber).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()) {

                    Toast.makeText(otp_auth.this, "Authentication Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }else{

                    Toast.makeText(otp_auth.this, "Authentication Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), name_and_city.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
