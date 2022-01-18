package com.epam.rd.java.basic.practice4;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;

public class Part1 {

    static Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        printToConsoleModified(System.getProperty("user.dir") + "/part1.txt");
    }

    //Print to console input modified according to the Task1 specification.
    public static void printToConsoleModified(String filePath) {
        String s = getInput(filePath);
        Matcher m = Pattern.compile("\\b(\\p{L}{2})(?=(\\p{L}{2,})\\b)").matcher(s);
        System.out.println(m.replaceAll(""));
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
