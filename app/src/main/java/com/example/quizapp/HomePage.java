package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class HomePage extends AppCompatActivity {

    AppCompatButton easy_button, medium_button, expert_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(super.getSupportActionBar()).hide();
        setContentView(R.layout.activity_home_page);
        easy_button = findViewById(R.id.button_easy);
        medium_button = findViewById(R.id.button_medium);
        expert_button = findViewById(R.id.button_expert);

    }

    public void easy_level(View view) {
        Intent intent = new Intent(this, Quiz_Start_Page.class);
        Bundle bundle = new Bundle();
        bundle.putString("key1","Easy");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void medium_level(View view) {
        Intent intent = new Intent(this, Quiz_Start_Page.class);
        Bundle bundle = new Bundle();
        bundle.putString("key1","Medium");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void expert_level(View view) {
        Intent intent = new Intent(this, Quiz_Start_Page.class);
        Bundle bundle = new Bundle();
        bundle.putString("key1","Expert");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}