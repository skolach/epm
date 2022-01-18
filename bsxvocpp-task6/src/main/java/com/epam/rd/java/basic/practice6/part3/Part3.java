package com.epam.rd.java.basic.practice6.part3;

public class Part3 {

    public static void main(String[] args) {

        Parking parking = new Parking(4);

        boolean res = parking.arrive(2);
        System.out.println("parking.arrive(2)  // " + parking + ", " + res);//NOSONAR

        res = parking.arrive(3);
        System.out.println("parking.arrive(3)  // " + parking + ", " + res);

        res = parking.arrive(2);
        System.out.println("parking.arrive(2)  // " + parking + ", " + res);

        res = parking.arrive(2);
        System.out.println("parking.arrive(2)  // " + parking + ", " + res);

        res = parking.arrive(2);
        System.out.println("parking.arrive(2)  // " + parking + ", " + res);

        res = parking.depart(1);
        System.out.println("parking.depart(1)  // " + parking + ", " + res);

        res = parking.depart(1);
        System.out.println("parking.depart(1)  // " + parking + ", " + res);
    }

}