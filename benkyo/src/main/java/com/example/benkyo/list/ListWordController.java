package com.example.benkyo.list;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.benkyo.word.*;
import com.example.benkyo.quiz.*;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/api")
public class ListWordController {
    @Autowired
    ListWordRepository listWordRepository;

    @Autowired
    WordRepository wordRepository;

    @GetMapping("/list")
    public ResponseEntity<?> getLists() {
        return ResponseEntity.ok(listWordRepository.findAll());
    }

    @GetMapping("/list/{codeName}")
    public ResponseEntity<?> getList(@PathVariable String codeName) {
        Optional <ListWord> list = listWordRepository.findByCodeName(codeName);
        if (list.isPresent()) {
            return ResponseEntity.ok(new ListWordDTO.Read(list.get()));
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/list")
    public ResponseEntity<?> createList(@Valid @RequestBody ListWordDTO.Write list, BindingResult result) {

        Optional <ListWord> existingList = listWordRepository.findByCodeName(list.getCodeName());
        if (existingList.isPresent()) {
            return ResponseEntity.badRequest().body("List already exists");
        }
        ListWord newList = new ListWord(list.getFriendlyName(), list.getCodeName(), list.getDescription());
        listWordRepository.insert(newList);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        return ResponseEntity.ok(new ListWordDTO.Read(newList));
    }

    @PostMapping("/list/{codeName}/word")
    public ResponseEntity<?> appendWord(@PathVariable String codeName, 
            @Valid @RequestBody ListWordDTO.AppendWord word, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Optional <ListWord> list = listWordRepository.findByCodeName(codeName);
        Optional <Word> wordObj = wordRepository.findBySlug(word.getSlug());
        if (list.isPresent() && wordObj.isPresent()) {
            list.get().appendWord(wordObj.get().get_id());
            listWordRepository.save(list.get());
            return new ResponseEntity<>(new ListWordDTO.Read(list.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/list/{codeName}")
    public ResponseEntity<?> deleteList(@PathVariable String codeName) {
        listWordRepository.deleteByCodeName(codeName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list/{codeName}/get-quiz")
    public ResponseEntity<?> getQuiz(@PathVariable String codeName) {
        Optional <ListWord> list = listWordRepository.findByCodeName(codeName);
        if (list.isPresent()) {
            return ResponseEntity.ok(new QuizList(list.get()));
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/{codeName}/get-hard-quiz")
    public ResponseEntity<?> getHardQuiz(@PathVariable String codeName) {
        Optional <ListWord> list = listWordRepository.findByCodeName(codeName);
        if (list.isPresent()) {
            return ResponseEntity.ok(new HardMode(list.get()));
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}