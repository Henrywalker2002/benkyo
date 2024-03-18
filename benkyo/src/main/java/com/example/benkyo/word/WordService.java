package com.example.benkyo.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import org.bson.types.ObjectId;

@Service
public class WordService {
    @Autowired
    WordRepository wordRepository;

    public List <WordDTO.Read> getWords(List<ObjectId> ids) {
        List<Word> lst = wordRepository.findAllById(ids);
        List<WordDTO.Read> res = new ArrayList<WordDTO.Read>();
        for (Word word : lst) {
            res.add(new WordDTO.Read(word));
        }
        return res;
    }
}