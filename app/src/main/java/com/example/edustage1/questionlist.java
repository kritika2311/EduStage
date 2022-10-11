package com.example.edustage1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chinodev.androidneomorphframelayout.NeomorphFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.chinodev.androidneomorphframelayout.NeomorphFrameLayout.*;


public class questionlist extends AppCompatActivity {

    FirebaseFirestore fstore;
    FirebaseAuth fAuth;

    questionLibrary mQuestionLibrary = new questionLibrary();
    TextView mQuestion1, mQuestion2, mQuestion3, mQuestion4;
    RadioGroup mOptions1, mOptions2, mOptions3, mOptions4;
    RadioButton mOptionA1, mOptionB1, mOptionA2, mOptionB2, mOptionA3, mOptionB3, mOptionA4, mOptionB4;
    Button next_btn;
    ImageView design;
    NeomorphFrameLayout backto_home;
    RelativeLayout submit_btn, question2, question3, question4;

    int index1 = 0, index2 = 0, mQuestionNumber=0, questionsCount=0, col1A=0, col1B=0, col2A=0, col2B=0, col3A=0, col3B=0, col4A=0, col4B=0, col5A=0, col5B=0, col6A=0, col6B=0, col7A=0, col7B=0;
    char answers[] = new char[70];
    char type[] = new char[4];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_questionslist);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        mQuestion1 = findViewById(R.id.question1);
        mQuestion2 = findViewById(R.id.question2);
        mQuestion3 = findViewById(R.id.question3);
        mQuestion4 = findViewById(R.id.question4);
        mOptionA1 = findViewById(R.id.optionA1);
        mOptionB1 = findViewById(R.id.optionB1);
        mOptionA2 = findViewById(R.id.optionA2);
        mOptionB2 = findViewById(R.id.optionB2);
        mOptionA3 = findViewById(R.id.optionA3);
        mOptionB3 = findViewById(R.id.optionB3);
        mOptionA4 = findViewById(R.id.optionA4);
        mOptionB4 = findViewById(R.id.optionB4);
        backto_home = findViewById(R.id.backto_home);
        next_btn = findViewById(R.id.next_btn);
        design = findViewById(R.id.design);
        mOptions1 = findViewById(R.id.options1);
        mOptions2 = findViewById(R.id.options2);
        mOptions3 = findViewById(R.id.options3);
        mOptions4 = findViewById(R.id.options4);
        submit_btn = findViewById(R.id.submit_btn);
        question2 = findViewById(R.id.Question2);
        question3 = findViewById(R.id.Question3);
        question4 = findViewById(R.id.Question4);

        updateQuestions();

        backto_home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(questionlist.this, Home.class));
                finish();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (questionsCount != 70) {

                    if(check_answer1() && check_answer2() && check_answer3() && check_answer4()){
                        design.setVisibility(VISIBLE);
                        backto_home.setVisibility(INVISIBLE);

                        updateQuestions();

                        mOptions1.clearCheck();
                        mOptions2.clearCheck();
                        mOptions3.clearCheck();
                        mOptions4.clearCheck();

                    }else{
                        return;
                    }
                }else if(questionsCount == 70) {

                    if(check_answer1() && check_answer2()) {
                        checkType();

                        Map<String, Object> user = new HashMap<>();
                        user.put("TYPE", String.valueOf(type));
                        user.put("ANSWERS", answers);

                        fstore.collection("Users").document(fAuth.getCurrentUser().getPhoneNumber()).set(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Intent intent = new Intent(questionlist.this, personality_type.class);
                                    intent.putExtra("TYPE", String.valueOf(type));
                                    startActivity(intent);
                                    finish();

                                }else{
                                }

                            }
                        });
                    }else{
                        return;
                    }
                }
            }
        });

    }

    private Boolean check_answer1() {
        if(mOptionA1.isChecked()){

            answers[index1]='A';
            index1++;

            return true;
        }else if(mOptionB1.isChecked()){

            answers[index1]='B';
            index1++;

            return true;
        }else{
            return false;
        }
    }

    private Boolean check_answer2() {
        if(mOptionA2.isChecked()){

            answers[index1]='A';
            index1++;

            return true;
        }else if(mOptionB2.isChecked()){

            answers[index1]='B';
            index1++;

            return true;
        }else{
            return false;
        }
    }

    private Boolean check_answer3() {
        if(mOptionA3.isChecked()){

            answers[index1]='A';
            index1++;

            return true;
        }else if(mOptionB3.isChecked()){

            answers[index1]='B';
            index1++;

            return true;
        }else{
            return false;
        }
    }

    private Boolean check_answer4() {
        if(mOptionA4.isChecked()){

            answers[index1]='A';
            index1++;

            return true;
        }else if(mOptionB4.isChecked()){

            answers[index1]='B';
            index1++;

            return true;
        }else{
            return false;
        }
    }


    private void updateQuestions(){

        mQuestion1.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mOptionA1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mOptionB1.setText(mQuestionLibrary.getChoice2(mQuestionNumber));

        mQuestion2.setText(mQuestionLibrary.getQuestion(mQuestionNumber+1));
        mOptionA2.setText(mQuestionLibrary.getChoice1(mQuestionNumber+1));
        mOptionB2.setText(mQuestionLibrary.getChoice2(mQuestionNumber+1));

        questionsCount = questionsCount+2;

        if(questionsCount != 70) {

            mQuestion3.setText(mQuestionLibrary.getQuestion(mQuestionNumber + 2));
            mOptionA3.setText(mQuestionLibrary.getChoice1(mQuestionNumber + 2));
            mOptionB3.setText(mQuestionLibrary.getChoice2(mQuestionNumber + 2));

            mQuestion4.setText(mQuestionLibrary.getQuestion(mQuestionNumber + 3));
            mOptionA4.setText(mQuestionLibrary.getChoice1(mQuestionNumber + 3));
            mOptionB4.setText(mQuestionLibrary.getChoice2(mQuestionNumber + 3));

            questionsCount = questionsCount+2;
        }

        mQuestionNumber = mQuestionNumber+4;
        if(questionsCount == 70){
            question3.setVisibility(INVISIBLE);
            question4.setVisibility(INVISIBLE);

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) submit_btn.getLayoutParams();
            layoutParams.addRule(RelativeLayout.BELOW, question2.getId());
            next_btn.setText("Submit");
        }
    }

    private void checkType() {

        for(int i=1; i<70; i=i+7){
            if(answers[i]=='A'){
                col1A = col1A+1;
            }else if(answers[i]=='B'){
                col1B = col1B+1;
            }
        }

        for(int i=2; i<70; i=i+7){
            if(answers[i]=='A'){
                col2A = col2A+1;
            }else if(answers[i]=='B'){
                col2B = col2B+1;
            }
        }

        for(int i=3; i<70; i=i+7){
            if(answers[i]=='A'){
                col3A = col3A+1;
            }else if(answers[i]=='B'){
                col3B = col3B+1;
            }
        }

        for(int i=4; i<70; i=i+7){
            if(answers[i]=='A'){
                col4A = col4A+1;
            }else if(answers[i]=='B'){
                col4B = col4B+1;
            }
        }

        for(int i=5; i<70; i=i+7){
            if(answers[i]=='A'){
                col5A = col5A+1;
            }else if(answers[i]=='B'){
                col5B = col5B+1;
            }
        }

        for(int i=6; i<70; i=i+7){
            if(answers[i]=='A'){
                col6A = col6A+1;
            }else if(answers[i]=='B'){
                col6B = col6B+1;
            }
        }

        for(int i=7; i<70; i=i+7){
            if(answers[i]=='A'){
                col7A = col7A+1;
            }else if(answers[i]=='B'){
                col7B = col7B+1;
            }
        }

        col3A = col3A + col2A;
        col3B = col3B + col2B;

        col5A = col5A + col4A;
        col5B = col5B + col4B;

        col7A = col7A + col6A;
        col7B = col7B + col6B;

        if(col1A > col1B){
            type[index2] = 'E';
            index2++;
        }else{
            type[index2] = 'I';
            index2++;
        }

        if(col3A > col3B){
            type[index2] = 'S';
            index2++;
        }else{
            type[index2] = 'N';
            index2++;
        }

        if(col5A > col5B){
            type[index2] = 'T';
            index2++;
        }else{
            type[index2] = 'F';
            index2++;
        }

        if(col7A > col7B){
            type[index2] = 'J';
            index2++;
        }else{
            type[index2] = 'P';
            index2++;
        }
    }

}
