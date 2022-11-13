package com.example.quizapp;

public class Quiz {
    public String question, optionA, optionB, optionC, optionD, answer;
    public Quiz(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Quiz(String question,String optionA,String optionB,String optionC,String optionD,String answer){
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
    }
}
