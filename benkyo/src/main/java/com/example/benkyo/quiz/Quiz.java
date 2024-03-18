package com.example.benkyo.quiz;

import java.util.HashMap;


public class Quiz {
    public String question;
    public HashMap<String, String> answers;
    public String correctAnswer;

    public Quiz(String question, HashMap<String, String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
