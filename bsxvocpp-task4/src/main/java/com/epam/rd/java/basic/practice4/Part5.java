package com.epam.rd.java.basic.practice4;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part5 {

    private static ResourceBundle enResourceBundle = 
        ResourceBundle.getBundle(
            "resources",
            new Locale("en"));

    private static ResourceBundle ruResourceBundle = 
        ResourceBundle.getBundle(
            "resources",
            new Locale("RU"));

    public static void main(String[] args) {
                
        Scanner in = new Scanner(System.in);
        String line;
        String key;
        String language;
        Pattern pattern = Pattern.compile("(\\A[a-zA-Z]+(?= ))|((?<= )[a-zA-Z]+\\Z)");
        Matcher m;
        
        while (((line = in.nextLine()) != null) && !line.equals("stop")) {
            m = pattern.matcher(line);
            if (m.find()){
                key = m.group();
                if (m.find()){
                    language = m.group();
                    System.out.println(loadResource(language, key));
                }
            }
        }
        in.close();
    }

    private static String loadResource(String language, String key){
        switch (language) {
            case "ru":
                return ruResourceBundle.getString(key);
            case "en":
                return enResourceBundle.getString(key);
            default:
                System.out.println("Anknown input.");
                return null;
        }
    }
}
