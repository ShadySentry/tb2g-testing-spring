package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Service;

@Service
public class HearingInterpreter {
    private final WordProduces wordProduces;

    public HearingInterpreter(WordProduces wordProduces) {
        this.wordProduces = wordProduces;
    }

    public String WhatIHeard(){
        String word = wordProduces.getWord();

        System.out.println(word);
        return word;
    }
}
