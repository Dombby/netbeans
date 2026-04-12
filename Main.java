package com.mycompany.kuliah;

import com.mycompany.kuliah.akademis.MataKuliah;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Akademik ===");
        Mahasiswa.npm = "2410010498";
        Mahasiswa.nama = "Muhammad Daffaa Ramadhan";
        Mahasiswa.ipk = 3.76;
        Mahasiswa.semester = 4;
        Mahasiswa.getMahasiswa();

        MataKuliah.setKode_mk("IF101");
        MataKuliah.setNama_mk("Pemrograman Java");
        System.out.println("\n============");
        MataKuliah.tampilMataKuliah();

    }
}

    

