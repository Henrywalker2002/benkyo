package com.example.benkyo.quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.example.benkyo.list.*;
import com.example.benkyo.word.*;

public class QuizList {
    List<Quiz> quizzes;
    
    public QuizList(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public QuizList(ListWord list) {
        List <WordDTO.Read> words = list.getWords();
        List <Quiz> res = new ArrayList<Quiz>();
        int count = 0;
        Random random = new Random();
        List<Integer> addedId = new ArrayList<Integer>();
        for (WordDTO.Read word : words) {
            String question = word.getHiragana();
            int sttCorrect = random.nextInt(4);
            HashMap<String, String> answer = new HashMap<>();
            for (int i = 0; i< 4;i++) {
                String key = "answer_" + i;
                String value;
                if (i == sttCorrect) {
                    value = word.getMeaning();
                }
                else {
                    int stt = random.nextInt(words.size());
                    while (stt == count || addedId.contains(stt)) {
                        stt = random.nextInt(words.size());
                    }
                    value = words.get(stt).getMeaning();
                    addedId.add(stt);
                }
                answer.put(key, value);
            }
            res.add(new Quiz(question, answer, "answer_" + sttCorrect));
            addedId.clear();    
            count++;
        }
        this.quizzes = res;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

}
