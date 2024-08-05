package com.ricky;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/5
 * @className ACTrieTest
 * @desc
 */
class ACTrieTest {

    @Test
    public void parseText1() {
        // Given
        ACTrie trie = new ACTrie();
        trie.ignoreCase();
        trie.addKeyword("导航");
        trie.addKeyword("酒店");
        trie.addKeyword("希尔顿酒店");

        String text = "导航到附近的希尔顿酒店";

        // When
        Collection<ACTrie.PattenString> pattenStrings = trie.parseText(text);

        // Then
        assertThat(pattenStrings).isEqualTo(List.of(
                new ACTrie.PattenString("导航", 0, 1),
                new ACTrie.PattenString("希尔顿酒店", 6, 10),
                new ACTrie.PattenString("酒店", 9, 10)
        ));
    }

    @Test
    public void parseText2() {
        // Given
        ACTrie trie = ACTrie.builder()
                .ignoreCase()
                .addKeyword("say")
                .addKeyword("She")
                .addKeyword("shr")
                .addKeyword("He")
                .addKeyword("her")
                .build();

        String text = "sherhsay";

        // When
        Collection<ACTrie.PattenString> pattenStrings = trie.parseText(text);

        // Then
        assertThat(pattenStrings).isEqualTo(List.of(
                new ACTrie.PattenString("She", 0, 2),
                new ACTrie.PattenString("He", 1, 2),
                new ACTrie.PattenString("her", 1, 3),
                new ACTrie.PattenString("say", 5, 7)
        ));
    }

    @Test
    public void parseText3() {
        // Given
        ACTrie trie = ACTrie.builder()
                .ignoreCase()
                .addKeyword("say")
                .addKeyword("She")
                .addKeyword("shr")
                .addKeyword("He")
                .addKeyword("her")
                .build();

        String text = "sherhsay";

        // When
        Collection<ACTrie.PattenString> pattenStrings = trie.parseText(text);

        // Then
        assertThat(pattenStrings).isEqualTo(List.of(
                new ACTrie.PattenString("her", 1, 3),
                new ACTrie.PattenString("say", 5, 7)
        ));
    }

    @Test
    public void modifyText() {
        // Given
        ACTrie trie = ACTrie.builder()
                .ignoreCase()
                .addKeywords(List.of("wdnmd", "rnm", "sb", "cnm", "nmb", "gdx"))
                .build();

        String text = "你怎么玩的啊wrnmdggdxwdnmdcnmbcnmsb啊";

        // When
        String res = trie.modifyText(text, '*');

        // Then
        System.out.println(res);
    }

}