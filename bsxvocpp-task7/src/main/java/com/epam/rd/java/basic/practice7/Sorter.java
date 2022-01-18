package com.epam.rd.java.basic.practice7;

public class Sorter {

    private Sorter(){}

    public static void sortFlowersByName(Flowers flowers) {
        flowers.getFlowers().sort((f1, f2) -> f1.getName().compareTo(f2.getName()));
    }

    public static void sortFlowersByAveLen(Flowers flowers) {
        flowers.getFlowers().sort((f1, f2) -> 
            f2.getVisualParameters().getAveLenFlower().getValue().compareTo(
                f1.getVisualParameters().getAveLenFlower().getValue()));
    }

    public static void sortFlowersByWatering(Flowers flowers) {
        flowers.getFlowers().sort((f1, f2) -> 
            f2.getGrowingTips().getWatering().getValue().compareTo(
                f1.getGrowingTips().getWatering().getValue()));
    }

}
