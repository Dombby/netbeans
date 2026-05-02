package UTS;

public class Penerbit {
    private int id;
    private String namaPenerbit;
    private String alamatPenerbit;

    public void index(){ 
        System.out.println("SQL: SELECT * FROM penerbit;"); 
    }
    
    public void create(){ 
        System.out.println("Form: Menampilkan halaman tambah penerbit."); 
    }
    
    public void store(){ 
        System.out.println("SQL: INSERT INTO penerbit (namaPenerbit, alamatPenerbit) VALUES ('...', '...');"); 
    }
    
    public void edit(){ 
        System.out.println("Form: Menampilkan halaman edit penerbit."); 
    }
    
    public void update(){ 
        System.out.println("SQL: UPDATE penerbit SET namaPenerbit='...', alamatPenerbit='...' WHERE id=...;"); 
    }
    public void destroy(){ 
        System.out.println("SQL: DELETE FROM penerbit WHERE id=...;"); 
    }
}
