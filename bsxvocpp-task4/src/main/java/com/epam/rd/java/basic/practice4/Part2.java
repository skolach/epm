package com.epam.rd.java.basic.practice4;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

    static Logger logger = Logger.getAnonymousLogger();

    private static final String USER_DIR = "user.dir";
    static Random r = new SecureRandom();

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 10; i++) {
            sb.append(r.nextInt(51) + (i == 9 ? "" : " "));
        }

        try (PrintWriter out = new PrintWriter(System.getProperty(USER_DIR) + "/part2.txt")) {
            out.print(sb.toString());
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, "msg", ex);
        }

        String input = Part1.getInput(System.getProperty(USER_DIR) + "/part2.txt");

        Matcher m = Pattern.compile("\\d+").matcher(input);
        ArrayList<Integer> numbers = new ArrayList<>();
        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }

        StringBuilder output = new StringBuilder("");
        for (int element : intArraySort(numbers)) {
            output.append(String.valueOf(element) + " ");
        }

        try (PrintWriter out = new PrintWriter(System.getProperty(USER_DIR) +
                                                "/part2_sorted.txt")) {
            out.print(output.toString().trim());
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, "msg", ex);
        }

        System.out.println("input ==> " + sb.toString().trim());
        System.out.println("output ==> " + output.toString().trim());
    }

    public static List<Integer> intArraySort(List<Integer> a){
        boolean changed = true;
        int tmpInt;
        while (changed) {
            changed = false;
            for (int i = 0; i < a.size() - 1; i++) {
                if (a.get(i) > a.get(i + 1)) {
                    tmpInt = a.get(i);
                    a.set(i, a.get(i + 1));
                    a.set(i + 1, tmpInt);
                    changed = true;
                }
            }
        }
        return a;
    }

}
