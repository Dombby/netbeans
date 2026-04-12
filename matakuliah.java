package com.mycompany.kuliah.akademis;

public class MataKuliah {
    public static String kode_mk;
    public static String nama_mk;

    public static String getKode_mk() {
        return kode_mk;
    }

    public static String getNama_mk() {
        return nama_mk;
    }

    public static void setKode_mk(String kodeBaru) {
        kode_mk = kodeBaru;
    }

    public static void setNama_mk(String namaBaru) {
        nama_mk = namaBaru;
    }

    public static void tampilMataKuliah() {
        System.out.println("Kode MK : " + kode_mk);
        System.out.println("Nama MK : " + nama_mk);
    }
}

