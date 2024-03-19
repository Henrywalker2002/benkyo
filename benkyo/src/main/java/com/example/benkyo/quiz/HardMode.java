package com.example.benkyo.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.benkyo.list.ListWord;
import com.example.benkyo.word.WordDTO;


@NoArgsConstructor
@Getter
@Setter
public class HardMode {

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Question {
        public String question;
        public String answer;

        public Question(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }
    }

    List<Question> questions;

    public HardMode(ListWord listWord) {
        List <Question> res = new ArrayList<Question>();
        for (WordDTO.Read word : listWord.getWords()) {
            res.add(new Question(word.getHiragana(), word.getMeaning()));
            res.add(new Question(word.getMeaning(), word.getHiragana()));
        }
        Collections.shuffle(res);
        this.questions = res;
    }

}
