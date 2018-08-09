package com.thoughtworks.training.kmj;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;

public class SpellCheckerTest {
    @Test
    public void testCheckSpell() throws IOException {
        String sentence = "hello world";
        JLanguageTool languageTool = new JLanguageTool(new AmericanEnglish());
        List<RuleMatch> matches = languageTool.check(sentence);
        System.out.println(matches.size());
    }
}
