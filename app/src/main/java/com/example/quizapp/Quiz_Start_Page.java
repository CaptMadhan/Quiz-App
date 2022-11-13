package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class Quiz_Start_Page extends AppCompatActivity {
    AppCompatTextView textBox_level,textBox_numberOfQuestions,textbox_timeLimit;
    AppCompatButton button_start;
    int num_of_questions =0,time_in_seconds = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(super.getSupportActionBar()).hide();
        setContentView(R.layout.activity_quiz_start_page);
        textBox_level = findViewById(R.id.textBox_level);
        textBox_numberOfQuestions = findViewById(R.id.textBox_numberOfQuestions);
        textbox_timeLimit = findViewById(R.id.textbox_timeLimit);
        button_start = findViewById(R.id.button_start);

        Bundle bundle = getIntent().getExtras();
        String level = bundle.getString("key1","Easy");
        switch (level){
            case "Easy" : num_of_questions = 5; time_in_seconds = 20; break;
            case "Medium": num_of_questions = 10; time_in_seconds = 30; break;
            case "Expert": num_of_questions = 15; time_in_seconds = 60; break;
            default: num_of_questions = 5; time_in_seconds = 50;
        }
        textBox_level.setText(String.format("Level : %s", level));
        textBox_numberOfQuestions.setText(Integer.toString(num_of_questions));
        textbox_timeLimit.setText(Integer.toString(time_in_seconds));

    }

    public void start_quiz(View view) {
        Intent intent = new Intent(this,QuizPage.class);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("num_of_questions",num_of_questions);
        bundle2.putInt("time_in_seconds",time_in_seconds);
        intent.putExtras(bundle2);
        startActivity(intent);
    }
}