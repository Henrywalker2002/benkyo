package com.example.benkyo.list;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.example.benkyo.word.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Document(collection = "listWord")
@Component
public class ListWord implements ApplicationContextAware {

    @Id
    private ObjectId _id;
    private Set<ObjectId> words;
    private String friendlyName;

    @Indexed(unique = true)
    private String codeName;
    
    private String description;

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public ListWord(String friendlyName, String codeName, String description ) {
        this.codeName = codeName;
        this.friendlyName = friendlyName;
        this.description = description;
        this.words = new HashSet<ObjectId>();
    }

    public boolean appendWord(ObjectId wordOd) {
        if (this.words == null) {
            this.words = new HashSet<ObjectId>();
        }
        return this.words.add(wordOd);
    }

    public List<WordDTO.Read> getWords() {
        
        WordRepository wordRepository = context.getBean(WordRepository.class);
        if (this.words == null) {
            return new ArrayList<WordDTO.Read>();
        }
        List<Word> lst = wordRepository.findAllById(this.words);
        List<WordDTO.Read> res = new ArrayList<WordDTO.Read>();
        for (Word word : lst) {
            res.add(new WordDTO.Read(word));
        }
        return res;
    }
}