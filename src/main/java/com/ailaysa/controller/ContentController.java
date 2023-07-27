package com.ailaysa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;

@RestController
public class ContentController {

    @PostMapping("/content")
    public ResponseEntity<String> getMarkdownContent(@RequestBody String markdownContent) {
        String plainTextContent = convertMarkdownToPlainText(markdownContent);
        return ResponseEntity.ok(plainTextContent);
    }

    private String convertMarkdownToPlainText(String markdownContent) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownContent);

        TextContentRenderer textContentRenderer = TextContentRenderer.builder().build();
        return textContentRenderer.render(document);
    }
}