package com.epam.rd.java.basic.practice6.part6;

import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part63{

    public static void duplicates(Stream<String> s) {

        s.collect(
            Collectors.groupingBy(
                Function.identity(),
                LinkedHashMap::new,
                Collectors.counting())).
        entrySet().
        stream().
        filter(x -> x.getValue() > 1).
        limit(3).
        forEach(x -> System.out.println(
            new StringBuilder(x.getKey().toUpperCase()).reverse().toString()
        ));
    }
}