package com.example.benkyo.list;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

import org.bson.types.ObjectId;

public interface ListWordRepository extends MongoRepository<ListWord, ObjectId>  {
    public Optional<ListWord> findByCodeName(String codeName);

    public void deleteByCodeName(String codeName);
}