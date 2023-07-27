package com.ailaysa.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordCountController {

    @PostMapping("/wordcount")
    public ResponseEntity<Integer> getWordCount(@RequestBody String sentence) {
        int wordCount = countWords(sentence);
        return ResponseEntity.ok(wordCount);
    }

    private int countWords(String sentence) {
        String[] words = sentence.trim().split("\\s+");
        return words.length;
    }
}