package com.example.edustage1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chinodev.androidneomorphframelayout.NeomorphFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class consult_experts extends AppCompatActivity {

    NeomorphFrameLayout backto_home;

    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    CustomerAdapter_consultExperts adapter;

    FirebaseFirestore fstore;
    ProgressDialog progressDialog;
    private String TAG ="TAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consult_experts);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backto_home = findViewById(R.id.backto_home);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        fstore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        showData();

        backto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(consult_experts.this, Home.class));
                finish();
            }
        });
    }

    private void showData() {
        progressDialog.setTitle("Loading");
        progressDialog.show();

        fstore.collection("Industry Experts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {

                    modelList.clear();
                    for (DocumentSnapshot doc : task.getResult()) {
                        progressDialog.dismiss();
                        Model model = new Model(doc.getString("NAME"),
                                doc.getString("IMAGE"),
                                doc.getString("EXPERIENCE"),
                                doc.getString("MEMBERS"));
                        modelList.add(model);
                    }
                    adapter = new CustomerAdapter_consultExperts(consult_experts.this, modelList);
                    mRecyclerView.setAdapter(adapter);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(consult_experts.this, "Error !" +task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void selectExpert(int position) {
        Intent intent = new Intent(consult_experts.this, meeting_time_date.class);
        intent.putExtra("expert name", modelList.get(position).getName());
        startActivity(intent);
    }
}
