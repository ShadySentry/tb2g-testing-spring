package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Component;

@Component
public class YannyWordProducer implements WordProduces {
    @Override
    public String getWord() {
        return "Yanny";
    }
}
