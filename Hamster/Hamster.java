package com.mycompany.mavenproject2;

public class Hamster {
    String nama;
    String warnabulu;
    int umur;
    

    public Hamster() {
        nama = "Mochi";
        warnabulu = "Putih Abu";
        umur = 0;
    }

    public Hamster(String nama, String warnabulu, int umur) {
        this.nama = nama;
        this.warnabulu = warnabulu;
        this.umur = umur;
    }

    public void tampilData() {
        System.out.println("=== Data Hamster ===");
        System.out.println("Nama       : " + nama);
        System.out.println("Warna Bulu : " + warnabulu);
        System.out.println("Umur       : " + umur + " bulan");
    }

    public String deskripsi() {
        return nama + " adalah hamster berwarna " + warnabulu +
               " dan berumur " + umur + " bulan.";
    }
}
