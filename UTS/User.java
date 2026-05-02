package UTS;

public class User {
    private int id;
    private String email;
    private String password;

    public void index(){ 
        System.out.println("SQL: SELECT * FROM user;"); 
    }
    
    public void create(){ 
        System.out.println("Form: Menampilkan halaman tambah user."); 
    }
    
    public void store(){ 
        System.out.println("SQL: INSERT INTO user (email, password) VALUES ('...', '...');"); 
    }
    
    public void edit(){ 
        System.out.println("Form: Menampilkan halaman edit user."); 
    }
    
    public void update(){ 
        System.out.println("SQL: UPDATE user SET email='...', password='...' WHERE id=...;"); 
    }
    
    public void destroy(){ 
        System.out.println("SQL: DELETE FROM user WHERE id=...;"); 
    }
}
