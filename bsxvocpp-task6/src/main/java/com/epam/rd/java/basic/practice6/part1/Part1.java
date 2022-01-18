package com.epam.rd.java.basic.practice6.part1;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

public class Part1 {

    static Logger logger = Logger.getAnonymousLogger();
    private static final String LS = System.lineSeparator();
	
	public static void main(String[] args) {
        InputStream oldIn = System.in;
        System.setIn(
            new ByteArrayInputStream(
                ("asd 43 asdf asd 43" + LS +
                "434 asdf " + LS +
                "kasdf asdf stop asdf " + LS +
                "stop").getBytes()
        ));

        WordContainer.main(null);
        
        System.setIn(oldIn);
	}
}