package UTS;

public class Buku {
    private int id;
    private String judul;
    private String penulis;
    private int tahun;
    private Penerbit Penerbit; // Relasi ke Class Penerbit

    public void index(){ 
        System.out.println("SQL: SELECT * FROM buku;"); 
    }
    public void create(){ 
        System.out.println("Form: Menampilkan halaman tambah buku."); 
    }
    public void store(){ 
        System.out.println("SQL: INSERT INTO buku (judul, penulis, tahun, id_penerbit) VALUES ('...', '...', ..., ...);"); 
    }
    public void edit() { 
        System.out.println("Form: Menampilkan halaman edit buku."); 
    }
    public void update(){ 
        System.out.println("SQL: UPDATE buku SET judul='...', penulis='...', tahun=..., id_penerbit=... WHERE id=...;"); 
    }
    public void destroy(){ 
        System.out.println("SQL: DELETE FROM buku WHERE id=...;"); 
    }
}
