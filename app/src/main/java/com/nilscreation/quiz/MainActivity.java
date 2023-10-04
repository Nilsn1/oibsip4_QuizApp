package com.nilscreation.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ImageView btnBack;
    TextView toolbarTitle, txtCurrentQuestion, txtAllQuestionno, txtQuestion, optionA, optionB, optionC, optionD, txtNext;
    ProgressBar progressbar;
    ArrayList<QuizModel> datalist;
    int indexNumber = 0;
    int currentQuestionNumber = 1;
    int datalistSize;
    int correctAnswers = 0;
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBack = findViewById(R.id.btnBack);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        txtCurrentQuestion = findViewById(R.id.txtCurrentQuestion);
        txtAllQuestionno = findViewById(R.id.txtAllQuestionno);
        txtQuestion = findViewById(R.id.txtQuestion);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        txtNext = findViewById(R.id.txtNext);
        progressbar = findViewById(R.id.progressbar);

        datalist = new ArrayList<>();

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("Category");

        getQuestions();
        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNext.setClickable(false);
                indexNumber = indexNumber + 1;
                currentQuestionNumber = currentQuestionNumber + 1;
                setQuestions();
                enableButtons();
//                Toast.makeText(MainActivity.this, " " + correctAnswers, Toast.LENGTH_SHORT).show();
            }
        });

        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButtons();
                optionA.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                if (datalist.get(indexNumber).getOptionA().equals(datalist.get(indexNumber).getAnswer())) {
                    correctAnswers = correctAnswers + 1;
                    optionA.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
                } else {
                    optionA.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.red_card));
                    getRightAnswer();
                }
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButtons();
                optionB.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                if (datalist.get(indexNumber).getOptionB().equals(datalist.get(indexNumber).getAnswer())) {
                    correctAnswers = correctAnswers + 1;
                    optionB.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
                } else {
                    optionB.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.red_card));
                    getRightAnswer();
                }
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButtons();
                optionC.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                if (datalist.get(indexNumber).getOptionC().equals(datalist.get(indexNumber).getAnswer())) {
                    correctAnswers = correctAnswers + 1;
                    optionC.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
                } else {
                    optionC.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.red_card));
                    getRightAnswer();
                }
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButtons();
                optionD.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                if (datalist.get(indexNumber).getOptionD().equals(datalist.get(indexNumber).getAnswer())) {
                    correctAnswers = correctAnswers + 1;
                    optionD.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
                } else {
                    optionD.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.red_card));
                    getRightAnswer();
                }
            }
        });
        txtNext.setClickable(false);
        optionA.setClickable(false);
        optionB.setClickable(false);
        optionC.setClickable(false);
        optionD.setClickable(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getQuestions() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quiz DB");
        databaseReference.child(categoryName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    QuizModel quizModel = dataSnapshot.getValue(QuizModel.class);
                    datalist.add(quizModel);
                }
                Collections.shuffle(datalist);
                datalistSize = datalist.size();
                setQuestions();
                txtAllQuestionno.setText("" + datalistSize);

                progressbar.setVisibility(View.GONE);
                enableButtons();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setQuestions() {

        if (indexNumber < datalistSize) {
            toolbarTitle.setText(categoryName + " Quiz");
            txtCurrentQuestion.setText("" + currentQuestionNumber);
            txtQuestion.setText("Q. " + datalist.get(indexNumber).getQuestion());
            optionA.setText("A) " + datalist.get(indexNumber).getOptionA());
            optionB.setText("B) " + datalist.get(indexNumber).getOptionB());
            optionC.setText("C) " + datalist.get(indexNumber).getOptionC());
            optionD.setText("D) " + datalist.get(indexNumber).getOptionD());
        } else {
            Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
            intent.putExtra("CorrectAnswers", correctAnswers);
            intent.putExtra("TotalQuestions", datalistSize);
            startActivity(intent);
            finish();
        }
    }

    private void enableButtons() {
        optionA.setClickable(true);
        optionB.setClickable(true);
        optionC.setClickable(true);
        optionD.setClickable(true);
        optionA.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.white_card));
        optionB.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.white_card));
        optionC.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.white_card));
        optionD.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.white_card));
        optionA.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.primary_text));
        optionB.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.primary_text));
        optionC.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.primary_text));
        optionD.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.primary_text));
    }

    private void disableButtons() {
        optionA.setClickable(false);
        optionB.setClickable(false);
        optionC.setClickable(false);
        optionD.setClickable(false);
        txtNext.setClickable(true);
    }

    private void getRightAnswer() {
        if (datalist.get(indexNumber).getOptionA().equals(datalist.get(indexNumber).getAnswer())) {
            optionA.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
            optionA.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        } else if (datalist.get(indexNumber).getOptionB().equals(datalist.get(indexNumber).getAnswer())) {
            optionB.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
            optionB.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        } else if (datalist.get(indexNumber).getOptionC().equals(datalist.get(indexNumber).getAnswer())) {
            optionC.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
            optionC.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        } else {
            optionD.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
            optionD.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        }
    }
}