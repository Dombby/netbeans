package com.mycompany.mavenproject2;

public class Main {
    public static void main(String[] args) {

        Hamster h1 = new Hamster();
        Hamster h2 = new Hamster();

        Hamster h3 = new Hamster("Mocha", "Abu putih", 9);
        Hamster h4 = new Hamster("Mochi", "Putih Abu", 12);

        h1.tampilData();
        System.out.println(h1.deskripsi());
        System.out.println();

        h2.tampilData();
        System.out.println(h2.deskripsi());
        System.out.println();

        h3.tampilData();
        System.out.println(h3.deskripsi());
        System.out.println();

        h4.tampilData();
        System.out.println(h4.deskripsi());
    }
}