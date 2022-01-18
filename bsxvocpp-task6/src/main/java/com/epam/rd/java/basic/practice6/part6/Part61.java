package com.epam.rd.java.basic.practice6.part6;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part61 {

    public static void frequency(Stream<String> s) {

        s.collect(
            Collectors.groupingBy(
                Function.identity(),
                LinkedHashMap::new,
                Collectors.counting())).
        entrySet().
        stream().
        sorted(Map.Entry.comparingByValue((x,y) -> Long.compare(y, x))).
        limit(3).
        sorted(Map.Entry.comparingByKey((x, y) -> y.compareTo(x))).
        forEach(x -> System.out.println(x.getKey() + " ==> " + x.getValue()));

    }
}