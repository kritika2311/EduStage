package com.example.edustage1;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class insights extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    String username, city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insights);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

//        Userprofile(fAuth.getCurrentUser().getPhoneNumber());
//
//        profileimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkandroidversion();
//            }
//        });
//
//        floatingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkandroidversion();
//            }
//        });
//    }
//
//    private void Userprofile(String UID) {
//        fstore.collection("Users").document(UID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    username = documentSnapshot.getString("USERNAME");
//                    city = documentSnapshot.getString("CITY");
//
//                    Viewusername.setText(usernamefromDb);
//                    phonenumber.setText(phonenumberfromDb);
//                    profileimageURIfromDb = documentSnapshot.getString("USERIMAGE");
//                    if (profileimageURIfromDb != null) {
//                        Picasso.get().load(profileimageURIfromDb).into(profileimage);
//                    }else{
//                        Toast.makeText(profile.this, "Please update your profile image", Toast.LENGTH_SHORT).show();
//                    }
//                    progressBar.setVisibility(View.INVISIBLE);
//                } else {
//                    Toast.makeText(insights.this, "Please try again after sometime", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
