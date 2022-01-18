package com.epam.rd.java.basic.practice6.part6;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part62 {

    public static void length(Stream<String> s) {
        s.
        collect(
            Collectors.toMap(
                x -> x,
                String::length,
                (x, y) -> x,
                LinkedHashMap::new)).
        entrySet().
        stream().
        sorted(
            Map.Entry.comparingByValue((x,y)-> y.compareTo(x))).
        limit(3).
        forEach(x -> System.out.println(x.getKey() + " ==> " + x.getValue()));
    }
}
