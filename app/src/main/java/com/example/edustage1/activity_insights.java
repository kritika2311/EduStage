package com.example.edustage1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.chinodev.androidneomorphframelayout.NeomorphFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_insights extends AppCompatActivity {

    CircleImageView profile_pic;
    FloatingActionButton floatingbutton;
    TextView Username, Country , userscore;
    CircularProgressBar progressBar;
    String profileimageURIfromDb, scorefromDb;
    NeomorphFrameLayout logout_dialog, backto_home;

    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    FirebaseUser user;
    ProgressDialog progressDialog;

    private int PICK_IMAGE = 10001;
    private static final int PERMISSION_STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);

        profile_pic = findViewById(R.id.profile_picture);
        floatingbutton = findViewById(R.id.floatingbutton);
        Username = findViewById(R.id.username);
        Country = findViewById(R.id.country_name);
        progressBar = findViewById(R.id.progress_bar);
        userscore = findViewById(R.id.userscore);
        logout_dialog = findViewById(R.id.logout_dialog);
        backto_home = findViewById(R.id.backto_home);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(activity_insights.this);

        Userprofile();

        backto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_insights.this, Home.class));
                finish();
            }
        });

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                checkPermissions();
            }
        });

        floatingbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                checkPermissions();
            }
        });

        logout_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder_logoutbutton = new AlertDialog.Builder(activity_insights.this);
                builder_logoutbutton.setTitle("Really Logout?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                fAuth.signOut();

                                Intent intent = new Intent(activity_insights.this, MainActivity.class);
                                intent.putExtra("finish", true);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("No", null);
                AlertDialog alertexit = builder_logoutbutton.create();
                alertexit.show();

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_STORAGE_CODE);
            }else {
                selectImage();
            }
        } else {
            selectImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE : {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Toast.makeText(this, "Storage Permissions Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    private void uploadImage(Bitmap bitmap) {
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profilePictures")
                .child(fAuth.getCurrentUser().getPhoneNumber()+".jpeg");
        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(activity_insights.this, "Please try again after sometime", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setProfilePicture(uri);
                    }
                });
    }

    private void setProfilePicture(Uri uri) {
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        fAuth.getCurrentUser().updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_insights.this, "Profile Image Updated", Toast.LENGTH_SHORT).show();
                        storeImage(uri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(activity_insights.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void storeImage( Uri uri) {

        Map<String, Object> userData = new HashMap<>();
        userData.put("USERIMAGE", uri.toString());

        Log.d("TAG", "storeImage: download Uri is "+uri.toString());

        fstore.collection("Users").document(fAuth.getCurrentUser().getPhoneNumber()).set(userData,SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(activity_insights.this, "Uploaded profile image successfully!", Toast.LENGTH_SHORT).show();
                            Userprofile();
                        }else{
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(activity_insights.this, "please try again after sometime", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImageURI = data.getData();
                try {
                    uploadImage(uriToBitmap(selectedImageURI));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private Bitmap uriToBitmap(Uri uri) throws IOException, IOException {
        return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
    }


    private void Userprofile() {
        fstore.collection("Users").document(fAuth.getCurrentUser().getPhoneNumber()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    Username.setText(documentSnapshot.getString("USERNAME"));
                    Country.setText(documentSnapshot.getString("CITY"));
//                    scorefromDb = documentSnapshot.getString("SCORE");
                    profileimageURIfromDb = documentSnapshot.getString("USERIMAGE");
                    scorefromDb = "60";
                    userscore.setText(scorefromDb);
                    progressBar.setProgress(Float.parseFloat(scorefromDb+"f"));

                    if (profileimageURIfromDb != null) {
                        Picasso.get().load(profileimageURIfromDb).placeholder(R.drawable.user).into(profile_pic);
                    }else{
                        Toast.makeText(activity_insights.this, "Please update your profile image", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(activity_insights.this, "Please try again after sometime", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
