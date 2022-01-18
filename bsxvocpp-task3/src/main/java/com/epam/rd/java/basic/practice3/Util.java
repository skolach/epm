package com.epam.rd.java.basic.practice3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    static Logger logger = Logger.getAnonymousLogger();

    public static String readFile(String path) {

        String res = null;

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            res = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "msg", ex);
        }

        return res;
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

    public static void main(String[] args) {
        String input = Util.getInput("part1.txt");
        System.out.println(input);
    }
}