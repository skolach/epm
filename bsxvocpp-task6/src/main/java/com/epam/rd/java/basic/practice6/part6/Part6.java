package com.epam.rd.java.basic.practice6.part6;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Part6 {

    private enum TASK{ FREQUENCY, LENGTH, DUPLICATES }

    static Logger logger = Logger.getAnonymousLogger();

    private static final String INPUT_REGEX = 
        "(?<=-i )\\S{1,}(?=(\\S{0,}))|(?<=--input )\\S{1,}(?=(\\S{0,}))";
    private static final String TASK_REGEX = 
        "(?<=-t )\\S{1,}(?=(\\S{0,}))|(?<=--task )\\S{1,}(?=(\\S{0,}))";

    

    public static void main(String[] args) {
        
        String input;
        TASK task;

        String arguments = String.join(" ", args);
        Matcher inputMatcher = Pattern.compile(INPUT_REGEX).matcher(arguments);
        Matcher taskMatcher = Pattern.compile(TASK_REGEX).matcher(arguments);
        if (inputMatcher.find() && taskMatcher.find()) {
            task = TASK.valueOf(taskMatcher.group().toUpperCase());
            input = inputMatcher.group();
        } else {
            throw new IllegalArgumentException();
        }

        String text = getInput(input);
        Stream<String> str = Pattern.compile("\\s{1,}").splitAsStream(text);
        switch (task) {
            case FREQUENCY:
                Part61.frequency(str);
                break;
            case LENGTH:
                Part62.length(str);
                break;
            case DUPLICATES:
                Part63.duplicates(str);
                break;                
        }
    }

    public static String getInput(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(fileName), "cp1251");
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append(System.lineSeparator());
            }
            scanner.close();
            return sb.toString().trim();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "msg", ex);
        }
        return sb.toString();
    }

}