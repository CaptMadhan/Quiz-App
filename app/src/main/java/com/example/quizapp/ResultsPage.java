package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Objects;

public class ResultsPage extends AppCompatActivity {
    int score=0, num_of_questions=0,time_in_seconds=0;
    Quiz[] questions = new Quiz[0];
    TextView textView_score_result,textView_max_score,textView_result_feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(super.getSupportActionBar()).hide();
        setContentView(R.layout.activity_results_page);

        textView_score_result=findViewById(R.id.textView_score_result);
        textView_max_score = findViewById(R.id.textView_max_score);
        textView_result_feedback = findViewById(R.id.textView_result_feedback);

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("score");
        num_of_questions = bundle.getInt("num_of_questions");
        time_in_seconds = bundle.getInt("time_in_seconds");
        textView_score_result.setText(Integer.toString(score));
        textView_max_score.setText(Integer.toString(num_of_questions));
        if(score < num_of_questions/2)
            textView_result_feedback.setText("Failed,\n focus and try again");
        if(score > num_of_questions/2)
            textView_result_feedback.setText("Passed,\n but needs improvement");
        if(score == num_of_questions-1)
            textView_result_feedback.setText("Close to perfect");
        if(score == num_of_questions)
            textView_result_feedback.setText("Perfect Score!");
    }

    public void retry_quiz(View view) {
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }

    public void view_solution(View view) {
        String[] recorded_answer = (String[]) getIntent().getSerializableExtra("recorded_answer");
        Quiz[] curr_questionSet = (Quiz[]) getIntent().getSerializableExtra("curr_questionSet");
        Intent intent = new Intent(this, ViewSolution.class);
        intent.putExtra("curr_questionSet", (Serializable) curr_questionSet);
        intent.putExtra("recorded_answer",(Serializable)recorded_answer);
        startActivity(intent);
    }

}