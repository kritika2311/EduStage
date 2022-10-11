package com.example.edustage1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class personality_type extends AppCompatActivity {

    String type;
    TextView letter1, letter2, letter3, letter4, text, job1, job2, job3, job4, job5;
    Button continue_btn;

    FirebaseFirestore fstore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personality_type);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        letter1 = findViewById(R.id.letter1);
        letter2 = findViewById(R.id.letter2);
        letter3 = findViewById(R.id.letter3);
        letter4 = findViewById(R.id.letter4);
        text = findViewById(R.id.text);
        job1 = findViewById(R.id.job1);
        job2 = findViewById(R.id.job2);
        job3 = findViewById(R.id.job3);
        job4 = findViewById(R.id.job4);
        job5 = findViewById(R.id.job5);
        continue_btn = findViewById(R.id.continue_btn);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("TYPE");

        checkType(type);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> user = new HashMap<>();
                user.put("TYPE", type);

                fstore.collection("Users").document(fAuth.getCurrentUser().getPhoneNumber()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            startActivity(new Intent(personality_type.this, Home.class));
                            finish();

                        }else{
                            Toast.makeText(personality_type.this, "Please try again after sometime.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void checkType(String type) {

        if(type.equals("ESTJ")){

            letter1.setText("E");
            letter2.setText("S");
            letter3.setText("T");
            letter4.setText("J");

            text.setText("Realists who are quick to make practical desicions.");

            job1.setText("Insurance sales agent");
            job2.setText("Pharmacist");
            job3.setText("Lawyer");
            job4.setText("Project manager");
            job5.setText("Judge");

        }else if(type.equals("ISTJ")){

            letter1.setText("I");
            letter2.setText("S");
            letter3.setText("T");
            letter4.setText("J");

            text.setText("Hard workers who value their responsibilities and commitments.");

            job1.setText("Auditor");
            job2.setText("Accountant");
            job3.setText("Chief financial officer");
            job4.setText("Web development engineer");
            job5.setText("Government employee");

        }else if(type.equals("ESFJ")){

            letter1.setText("E");
            letter2.setText("S");
            letter3.setText("F");
            letter4.setText("J");

            text.setText("Gregarious traditionalists motivated to help others.");

            job1.setText("Sales representative");
            job2.setText("Nurse/Healthcare worker");
            job3.setText("Social worker");
            job4.setText("PR account executive");
            job5.setText("Loan officer");

        }else if(type.equals("ISFJ")){

            letter1.setText("I");
            letter2.setText("S");
            letter3.setText("F");
            letter4.setText("J");

            text.setText("Modest and determined workers who enjoy helping others.");

            job1.setText("Dentist");
            job2.setText("Elementary school teacher");
            job3.setText("Librarian");
            job4.setText("Franchise owner");
            job5.setText("Customer service representative");

        }else if(type.equals("ESTP")){

            letter1.setText("E");
            letter2.setText("S");
            letter3.setText("T");
            letter4.setText("P");

            text.setText("Pragmatists who love excitement and excel in a crisis.");

            job1.setText("Detective");
            job2.setText("Banker");
            job3.setText("Investor");
            job4.setText("Entertainment agent");
            job5.setText("Sports coach");

        }else if(type.equals("ISTP")){

            letter1.setText("I");
            letter2.setText("S");
            letter3.setText("T");
            letter4.setText("P");

            text.setText("Straightforward and honest people who prefer action to conversation.");

            job1.setText("Civil engineer");
            job2.setText("Economist");
            job3.setText("Pilot");
            job4.setText("Data communications analyst");
            job5.setText("Emergency room physician");

        }else if(type.equals("ESFP")){

            letter1.setText("E");
            letter2.setText("S");
            letter3.setText("F");
            letter4.setText("P");

            text.setText("Lively and playful people who value common sense.");

            job1.setText("Child welfare counselor");
            job2.setText("Primary care physician");
            job3.setText("Actor");
            job4.setText("Interior designer");
            job5.setText("Environmental scientist");

        }else if(type.equals("ISFP")){

            letter1.setText("I");
            letter2.setText("S");
            letter3.setText("F");
            letter4.setText("P");

            text.setText("Warm and sensitive types who like to help people in tangible ways.");

            job1.setText("Fashion designer");
            job2.setText("Physician therapist");
            job3.setText("Massage therapist");
            job4.setText("Landscape architect");
            job5.setText("Storekeeper");

        }else if(type.equals("ENTJ")){

            letter1.setText("E");
            letter2.setText("N");
            letter3.setText("T");
            letter4.setText("J");

            text.setText("Natural leaders who are logical, analytical, and good strategic planners.");

            job1.setText("Executive");
            job2.setText("Lawyer");
            job3.setText("Market research analyst");
            job4.setText("Management/Business consultant");
            job5.setText("Venture capitalist");

        }else if(type.equals("INTJ")){

            letter1.setText("I");
            letter2.setText("N");
            letter3.setText("T");
            letter4.setText("J");

            text.setText("Creative perfectionists who prefer to do things their own way.");

            job1.setText("Investment banker");
            job2.setText("Personal financial adviser");
            job3.setText("Software developer");
            job4.setText("Economist");
            job5.setText("Executive");

        }else if(type.equals("ENFJ")){

            letter1.setText("E");
            letter2.setText("N");
            letter3.setText("F");
            letter4.setText("J");

            text.setText("People-lovers who are energetic, articulate, and diplomatic.");

            job1.setText("Advertising executive");
            job2.setText("Public relations specialist");
            job3.setText("Corporate coach/Trainer");
            job4.setText("Sales manager");
            job5.setText("Employment specialist/HR professional");

        }else if(type.equals("INFJ")){

            letter1.setText("I");
            letter2.setText("N");
            letter3.setText("F");
            letter4.setText("J");

            text.setText("Thoughtful, creative people driven by firm principles and personal integrity.");

            job1.setText("Therapist/Mental health counselor");
            job2.setText("Social worker");
            job3.setText("HR diversity manager");
            job4.setText("Organizational development consultant");
            job5.setText("Customer relations manager");

        }else if(type.equals("ENTP")){

            letter1.setText("E");
            letter2.setText("N");
            letter3.setText("T");
            letter4.setText("P");

            text.setText("Enterprising creative people who enjoy new challenges.");

            job1.setText("Entrepreneur");
            job2.setText("Real estate developer");
            job3.setText("Advertising creative director");
            job4.setText("Marketing director");
            job5.setText("Politician/Political consultant");

        }else if(type.equals("INTP")){

            letter1.setText("I");
            letter2.setText("N");
            letter3.setText("T");
            letter4.setText("P");

            text.setText("Independent and creative problem-solvers.");

            job1.setText("Computer programmer/Software designer");
            job2.setText("Financial analyst");
            job3.setText("Architect");
            job4.setText("College professor");
            job5.setText("Economist");

        }else if(type.equals("ENFP")){

            letter1.setText("E");
            letter2.setText("N");
            letter3.setText("F");
            letter4.setText("p");

            text.setText("Curious and confident creative types who see possibilities everywhere.");

            job1.setText("Journalist");
            job2.setText("Advertising creative director");
            job3.setText("Consultant");
            job4.setText("Restaurateur");
            job5.setText("Event planner");

        }else if(type.equals("INFP")){

            letter1.setText("I");
            letter2.setText("N");
            letter3.setText("F");
            letter4.setText("P");

            text.setText("Sensitive idealists motivated by their deeper personal values.");

            job1.setText("Graphic designer");
            job2.setText("Psychologist/Therapist");
            job3.setText("Writer/Editor");
            job4.setText("Physical therapist");
            job5.setText("HR development trainer");
        }
    }
}
