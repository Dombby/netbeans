package tugas;

public class main {
    public static void main(String[] args) {
        System.out.println("=== INSTANSIASI OBJECT PETSHOP (3 CONSTRUCTOR) ===\n");

        petshop pet1 = new petshop();
        System.out.println("Object 1 berhasil dibuat dengan Constructor 1.");
        
        petshop pet2 = new petshop("Lala", "Kucing Persi", 1500000);
        System.out.println("Object 2 berhasil dibuat dengan Constructor 2.");
        
        petshop pet3 = new petshop(5, "Mochi", "Hamster campbell", 4500000);
        System.out.println("Object 3 berhasil dibuat dengan Constructor 3.\n");


        System.out.println("=== PROSES CRUD DENGAN SQL ===\n");

        pet2.createPet();

        String querySelect = pet1.readPet(); 
        System.out.println("SQL: " + querySelect);
        System.out.println("Data hewan berhasil dimuat!\n");

        pet3.updatePet(5);

        boolean isDeleted = pet1.deletePet(5);
        if (isDeleted) {
            System.out.println("Status: Data hewan dengan ID 5 sukses dihapus dari database.");
        }
    }
}

