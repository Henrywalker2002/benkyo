package com.example.benkyo.word;

import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "word")
public class Word {
    @Id
    private ObjectId _id;

    @Indexed(unique = true)
    private String slug;
    private String kanji;
    private String hiragana;
    private String meaning;  
    private Binary audio;

    public Word() {
    }

    public Word(String slug, String kanji, String hiragana, String meaning, Binary audio) {
        this.slug = slug;
        this.kanji = kanji;
        this.hiragana = hiragana;
        this.meaning = meaning;
        this.audio = audio;
    }

    public Word(String slug, String kanji, String hiragana, String meaning) {
        this.slug = slug;
        this.kanji = kanji;
        this.hiragana = hiragana;
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return this.hiragana + " " + this.meaning;
    }
}