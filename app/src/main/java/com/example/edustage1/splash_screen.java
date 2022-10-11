package com.example.edustage1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity {

    ImageView edustage;
    TextView e, d, u, s, t, a, g, e1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edustage = findViewById(R.id.edustage);
        e = findViewById(R.id.e);
        d = findViewById(R.id.d);
        u = findViewById(R.id.u);
        s = findViewById(R.id.s);
        t = findViewById(R.id.t);
        a = findViewById(R.id.a);
        g = findViewById(R.id.g);
        e1 = findViewById(R.id.e1);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                edustage.startAnimation(AnimationUtils.loadAnimation(splash_screen.this, R.anim.p_in));
                edustage.setVisibility(View.VISIBLE);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                e.startAnimation(AnimationUtils.loadAnimation(splash_screen.this, R.anim.p_in));
                e.setVisibility(View.VISIBLE);
            }
        }, 1250);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d.startAnimation(AnimationUtils.loadAnimation(splash_screen.this, R.anim.p_in));
                d.setVisibility(View.VISIBLE);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                u.startAnimation(AnimationUtils.loadAnimation(splash_screen.this, R.anim.p_in));
                u.setVisibility(View.VISIBLE);
            }
        }, 1750);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                s.startAnimation(AnimationUtils.loadAnimation(splash_screen.this, R.anim.p_in));
                s.setVisibility(View.VISIBLE);
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t.startAnimation(AnimationUtils.loadAnimation(splash_screen.this, R.anim.p_in));
                t.setVisibility(View.VISIBLE);
            }
        }, 2250);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                a.startAnimation(AnimationUtils.loadAnimation(splash_screen.this, R.anim.p_in));
                a.setVisibility(View.VISIBLE);
            }
        }, 2500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                g.startAnimation(AnimationUtils.loadAnimation(splash_screen.this, R.anim.p_in));
                g.setVisibility(View.VISIBLE);
            }
        }, 2750);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                e1.startAnimation(AnimationUtils.loadAnimation(splash_screen.this, R.anim.p_in));
                e1.setVisibility(View.VISIBLE);
            }
        }, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                startActivity(new Intent(splash_screen.this, MainActivity.class));
                finish();

            }
        }, 4500);
    }
}
