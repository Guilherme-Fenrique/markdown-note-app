package dev.guilhermehf.MarkdownNote.service;

import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GrammarService {

    private final JLanguageTool langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-US"));

    public List<String> checkGrammar(String text){
        List<RuleMatch> matches;
        try {
            matches = langTool.check(text);
        } catch (IOException e) {
            throw new RuntimeException("Error matching: "+e);
        }
        return matches.stream()
                .map(m -> "Error in position " + m.getFromPos() + "-" + m.getToPos() + ": " + m.getMessage())
                .toList();


    }

}