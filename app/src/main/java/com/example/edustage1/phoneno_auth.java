package com.example.edustage1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class phoneno_auth extends AppCompatActivity {

    private static final String TAG = "phoneNum";
    EditText phoneNo;
    CheckBox checkBox;
    Button agree_continue_btn;
    LinearLayout shake_text;
    CountryCodePicker codePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phoneno_auth);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        phoneNo = findViewById(R.id.phoneNo);
        checkBox = findViewById(R.id.checkbox);
        agree_continue_btn = findViewById(R.id.agree_continue_btn);
        shake_text = findViewById(R.id.shake_text);
        codePicker = findViewById(R.id.ccp);

        TextView text_links = findViewById(R.id.link_texts);
        String text = "By clicking, you are accepting our privacy policy \nand terms & conditions.";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://social-media-integra-1.flycricket.io/privacy.html")));
            }
        };

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://social-media-integra-1.flycricket.io/terms.html")));
            }
        };

        ss.setSpan(clickableSpan,35,49,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan1,55,73,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.rgb(0,29,59)), 35, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.rgb(0,29,59)), 55, 73, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text_links.setText(ss);
        text_links.setMovementMethod(LinkMovementMethod.getInstance());

        agree_continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!phoneNo.getText().toString().isEmpty()) {

                    if(checkBox.isChecked()) {

                        Intent intent = new Intent(phoneno_auth.this, otp_auth.class);
                        intent.putExtra("countryCode", "+" + codePicker.getSelectedCountryCode());
                        intent.putExtra("phoneNo", phoneNo.getText().toString());
                        startActivity(intent);
                        finish();

                    }else{
                        Animation animShake = AnimationUtils.loadAnimation(phoneno_auth.this, R.anim.shake);
                        shake_text.startAnimation(animShake);
                    }
                }else {
                    return;
                }
            }
        });
    }
}
