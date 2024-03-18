package com.example.benkyo.list;

import java.util.List;

import com.example.benkyo.word.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ListWordDTO {

    @NoArgsConstructor
    @Setter
    @Getter
    public static class Read {
        public String friendlyName;
        public String codeName;
        public String description;
        public List<WordDTO.Read> words;

        public Read(ListWord list) {
            this.friendlyName = list.getFriendlyName();
            this.codeName = list.getCodeName();
            this.description = list.getDescription();
            this.words = list.getWords();
        }
    }
    
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Write {
        public String friendlyName;
        public String codeName;
        public String description;
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class AppendWord {
        public String slug;
    }


}