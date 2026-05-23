package tugas;

public class petshop {

    private int idHewan;
    private String namaHewan;
    private String jenisHewan; 
    private double harga;

    public petshop() {
        this.idHewan = 0;
        this.namaHewan = "Belum Ada Nama";
        this.jenisHewan = "Belum Diketahui";
        this.harga = 0.0;
    }

    public petshop(String namaHewan, String jenisHewan, double harga) {
        this.namaHewan = namaHewan;
        this.jenisHewan = jenisHewan;
        this.harga = harga;
    }

    public petshop(int idHewan, String namaHewan, String jenisHewan, double harga) {
        this.idHewan = idHewan;
        this.namaHewan = namaHewan;
        this.jenisHewan = jenisHewan;
        this.harga = harga;
    }

    public void createPet() {
        String sql = "INSERT INTO t_petshop (nama_hewan, jenis_hewan, harga) VALUES ('" 
                     + this.namaHewan + "', '" + this.jenisHewan + "', " + this.harga + ");";
        System.out.println("Menjalankan SQL Create...");
        System.out.println("SQL: " + sql);
        System.out.println("Hewan '" + this.namaHewan + "' (" + this.jenisHewan + ") berhasil ditambahkan!\n");
    }

    public String readPet() {
        String sql = "SELECT * FROM t_petshop;";
        System.out.println("Menjalankan SQL Read...");
        return sql; 
    }

    public void updatePet(int idTarget) {
        String sql = "UPDATE t_petshop SET nama_hewan = '" + this.namaHewan + "', jenis_hewan = '" + this.jenisHewan 
                     + "', harga = " + this.harga + " WHERE id_hewan = " + idTarget + ";";             
        System.out.println("Menjalankan SQL Update...");
        System.out.println("SQL: " + sql);
        System.out.println("Data hewan dengan ID " + idTarget + " berhasil diperbarui!\n");
    }

    public boolean deletePet(int idTarget) {
        String sql = "DELETE FROM t_petshop WHERE id_hewan = " + idTarget + ";";
        System.out.println("Menjalankan SQL Delete...");
        System.out.println("SQL: " + sql);

        return true; 
    }
}
