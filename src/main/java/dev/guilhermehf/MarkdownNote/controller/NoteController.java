package dev.guilhermehf.MarkdownNote.controller;

import dev.guilhermehf.MarkdownNote.dto.TextRequest;
import dev.guilhermehf.MarkdownNote.service.GrammarService;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.io.IOException;


@RestController
public class NoteController {

    private final GrammarService grammarService;
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    public NoteController(GrammarService grammarService) {
        this.grammarService = grammarService;
    }

    @PostMapping("/check-grammar")
    public ResponseEntity<List<String>> checkGrammar(@RequestParam TextRequest request){
        if (request.text().isEmpty()) return ResponseEntity.badRequest().build();

        List<String> response = this.grammarService.checkGrammar(request.text());
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("save-note")
    public ResponseEntity<String> saveNote (@RequestParam TextRequest request ){
        String noteId = UUID.randomUUID().toString();
        File file = new File("notes/" + noteId + ".md");

        try {
            FileUtils.writeStringToFile(file, request.text(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Note saved with id: " + noteId);
    }

    @GetMapping("notes")
    public ResponseEntity<List<String>> getAllNotesId(){
        File dir = new File("notes/");
        List<String> response = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(File::isFile)
                .map(File::getName).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notes/{noteId}")
    public ResponseEntity<String> getNoteAsHtml(@PathVariable String noteId) {
        File file = new File("notes/" + noteId + ".md");
        File htmlFile = new File("html_notes/");

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
        }
        try {
            String markdownText = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Parser parser = Parser.builder().build();
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            Node document = parser.parse(markdownText);
            String htmlContent = renderer.render(document);

            FileUtils.writeStringToFile(htmlFile, htmlContent, StandardCharsets.UTF_8);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<>(htmlContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading note");
        }
    }
}
