package com.example.benkyo.word;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.benkyo.list.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import crawler.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.net.URLEncoder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class WordController {
    
    @Autowired
    WordRepository wordRepository;

    @Autowired
    ListWordRepository listWordRepository;

    Crawler crawler = new Crawler();

    @GetMapping("/word")
    public ResponseEntity<?> getWords(
            @RequestParam(value = "search", required = false) String search ) {
        
        List<Word> words = null;
        if (search != null) {
            words = wordRepository.findByHiraganaLike(search);
        } else {
            words = wordRepository.findAll();
        }
        List <WordDTO.Read> wordDTOs = new ArrayList<>();
        for (Word word : words) {
            wordDTOs.add(new WordDTO.Read(word));
        }

        return ResponseEntity.ok(wordDTOs);
    }

    @PostMapping("/word")
    public ResponseEntity<?> createWord(@Valid @RequestBody WordDTO.Write word, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        String slug;
        if (word.getType().equals("hiragana")) {
            try {
                String url = "https://jisho.org/api/v1/search/words?keyword=" + URLEncoder.encode(word.getHiragana(), "UTF-8");
                URI uri = URI.create(url);
                HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection(); 
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream response = connection.getInputStream();
                    ObjectMapper mapper = new ObjectMapper();

                    @SuppressWarnings("unchecked")
                    Map<String, Object> jsonMap = mapper.readValue(response, Map.class);
                    @SuppressWarnings("unchecked")
                    ArrayList<Map<String, Object>> data = (ArrayList<Map<String, Object>>) jsonMap.get("data");
                    slug = (String) data.get(0).get("slug");
                }
                else {
                    InputStream response = connection.getErrorStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    String message = builder.toString();
                    return ResponseEntity.badRequest().body(message);
                }
            }
            catch (Exception e) {
                return ResponseEntity.badRequest().body("Error");
            }
        }
        else {
            slug = word.getSlug();
        }
        Optional <Word> wordFind = this.wordRepository.findBySlug(slug);
        Word newWord = new Word();
        if (!wordFind.isPresent()) {
            newWord = this.crawler.crawler(slug);
            wordRepository.save(newWord);
            return ResponseEntity.ok(new WordDTO.Read(newWord));
        }
        return ResponseEntity.ok(new WordDTO.Read(wordFind.get()));
    }
} 