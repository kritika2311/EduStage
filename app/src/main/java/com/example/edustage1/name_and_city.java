package com.example.edustage1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class name_and_city extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    EditText name, city;
    Button next_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_and_city);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.name);
        city = findViewById(R.id.city);
        next_btn = findViewById(R.id.next_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().isEmpty() && !city.getText().toString().isEmpty()) {

                    Map<String, Object> user = new HashMap<>();
                    user.put("USERNAME", name.getText().toString());
                    user.put("CITY", city.getText().toString());

                    fstore.collection("Users").document(fAuth.getCurrentUser().getPhoneNumber()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(name_and_city.this, Home.class));
                                finish();
                            }else{
                                Toast.makeText(name_and_city.this, "Please try again after sometime.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    return;
                }
            }
        });
    }
}
