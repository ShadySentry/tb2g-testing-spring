package org.springframework.samples.petclinic.sfg;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class HearingInterpreterTest {

    HearingInterpreter hearingInterpreter;

    @Before
    public void setUp() throws Exception{
        hearingInterpreter = new HearingInterpreter(new LaurelWordProducer());

    }

    @Test
    void whatIHeard() {
        String word = hearingInterpreter.whatIHeard();

        assertEquals("Laurel",word);
    }
}