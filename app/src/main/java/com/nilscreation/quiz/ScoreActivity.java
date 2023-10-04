package com.nilscreation.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class ScoreActivity extends AppCompatActivity {
    CircularProgressBar circularProgressBar;

    TextView txtResult, txtAllQuestion, txtRight, txtWrong, btnHome, btnexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        txtResult = findViewById(R.id.txtResult);
        txtAllQuestion = findViewById(R.id.txtAllQuestion);
        txtRight = findViewById(R.id.txtRight);
        txtWrong = findViewById(R.id.txtWrong);
        btnHome = findViewById(R.id.btnHome);
        btnexit = findViewById(R.id.btnexit);

        Intent intent = getIntent();
        int mCorrectAnswers = intent.getIntExtra("CorrectAnswers", 0);
        int mTotalQuestions = intent.getIntExtra("TotalQuestions", 0);
        int mWrongAnswers = mTotalQuestions - mCorrectAnswers;

        txtResult.setText("" + mCorrectAnswers + " / " + mTotalQuestions);
        txtAllQuestion.setText("Total Questions: " + mTotalQuestions);
        txtRight.setText("Right Answers: " + mCorrectAnswers);
        txtWrong.setText("Wrong Answers: " + mWrongAnswers);

        circularProgressBar.setProgress(mCorrectAnswers);
        circularProgressBar.setProgressMax(mTotalQuestions);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScoreActivity.this, DashboardActivity.class));
                finish();
            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
}