package com.ailaysa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ExtractionController {

    @PostMapping("/extractterminology")
    public ResponseEntity<List<String>> extractTerminology(@RequestBody String plainText) {
        List<String> terminology = extractTermsFromPlainText(plainText);
        return ResponseEntity.ok(terminology);
    }

    private List<String> extractTermsFromPlainText(String plainText) {
        try {
            // Load the model for Named Entity Recognition (NER)
            InputStream modelStream = new FileInputStream("path/to/en-ner-model.bin");
            TokenNameFinderModel model = new TokenNameFinderModel(modelStream);
            NameFinderME nameFinder = new NameFinderME(model);

            // Tokenize the input text
            String[] tokens = SimpleTokenizer.INSTANCE.tokenize(plainText);

            // Find named entities (terms) in the text
            Span[] spans = nameFinder.find(tokens);

            // Extract the terms from the spans
            List<String> extractedTerms = new ArrayList<>();
            for (Span span : spans) {
                String term = concatenateTokens(tokens, span.getStart(), span.getEnd());
                extractedTerms.add(term);
            }

            return extractedTerms;
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private String concatenateTokens(String[] tokens, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(tokens[i]);
            if (i < end - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}