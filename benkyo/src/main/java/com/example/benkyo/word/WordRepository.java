package com.example.benkyo.word;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface WordRepository extends MongoRepository<Word, ObjectId>{
    List <Word> findByHiraganaLike(String Hiragana);

    Optional<Word> findBySlug(String slug);
}