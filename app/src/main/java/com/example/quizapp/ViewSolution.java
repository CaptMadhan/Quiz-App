package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class ViewSolution extends AppCompatActivity {
    AppCompatTextView textView_QuestionText, textView_question_num, button_optionA, button_optionB, button_optionC,button_optionD;
    Quiz recorded_answer,curr_questionSet;
    AppCompatButton button_next;
    int count =0,totalCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(super.getSupportActionBar()).hide();
        setContentView(R.layout.activity_view_solution);

        textView_QuestionText = findViewById(R.id.textView_QuestionText);
        textView_question_num = findViewById(R.id.textView_question_num);
        button_optionA = findViewById(R.id.button_optionA);
        button_optionB = findViewById(R.id.button_optionB);
        button_optionC = findViewById(R.id.button_optionC);
        button_optionD = findViewById(R.id.button_optionD);
        button_next = findViewById(R.id.button_next);

        String[] recorded_answer = (String[]) getIntent().getSerializableExtra("recorded_answer");
        Quiz[] curr_questionSet = (Quiz[]) getIntent().getSerializableExtra("curr_questionSet");
        totalCount =  curr_questionSet.length;
        startDisplaying(count,recorded_answer,curr_questionSet);

    }

    private void startDisplaying(int count, String[] recorded_answer, Quiz[] curr_questionSet) {
        if(count == 0)
            button_next.setText("Go back");
        textView_question_num.setText(Integer.toString(count+1));
        textView_QuestionText.setText(curr_questionSet[count].question);
        button_optionA.setText(curr_questionSet[count].optionA);
        button_optionB.setText(curr_questionSet[count].optionB);
        button_optionC.setText(curr_questionSet[count].optionC);
        button_optionD.setText(curr_questionSet[count].optionD);
        String correctAns = curr_questionSet[count].answer;
        String wrongAns = recorded_answer[count];

        if(button_optionA.getText().equals(wrongAns))
            button_optionA.setBackgroundResource(R.drawable.red_button_border);
        if(button_optionB.getText().equals(wrongAns))
            button_optionB.setBackgroundResource(R.drawable.red_button_border);
        if(button_optionC.getText().equals(wrongAns))
            button_optionC.setBackgroundResource(R.drawable.red_button_border);
        if(button_optionD.getText().equals(wrongAns))
            button_optionD.setBackgroundResource(R.drawable.red_button_border);

        if(button_optionA.getText().equals(correctAns))
            button_optionA.setBackgroundResource(R.drawable.green_button_border);
        if(button_optionB.getText().equals(correctAns))
            button_optionB.setBackgroundResource(R.drawable.green_button_border);
        if(button_optionC.getText().equals(correctAns))
            button_optionC.setBackgroundResource(R.drawable.green_button_border);
        if(button_optionD.getText().equals(correctAns))
            button_optionD.setBackgroundResource(R.drawable.green_button_border);
        count++;
    }

    public void next_question(View view) {
        if(button_next.getText() == "Go back"){
            super.onBackPressed();
        }
        String[] recorded_answer = (String[]) getIntent().getSerializableExtra("recorded_answer");
        Quiz[] curr_questionSet = (Quiz[]) getIntent().getSerializableExtra("curr_questionSet");
        startDisplaying(count,recorded_answer,curr_questionSet);
    }
}