package com.example.benkyo.word;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class WordDTO {

    @NoArgsConstructor
    @Setter
    @Getter
    public static class Read {
        public ObjectId _id;
        public String slug;
        public String kanji;
        public String hiragana;
        public String meaning;

        public Read(Word word) {
            this._id = word.get_id();
            this.slug = word.getSlug();
            this.kanji = word.getKanji();
            this.hiragana = word.getHiragana();
            this.meaning = word.getMeaning();
        }
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class Write {
        public String slug;
        public String type;
        public String hiragana;
    }

    @ModelAttribute("word")
    public WordDTO.Write word() {
        return new WordDTO.Write();
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class WriteV2 {
        public String kanji;
        public String hiragana;
        public String meaning;
    }
}