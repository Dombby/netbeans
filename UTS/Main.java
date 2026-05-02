package UTS;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        Penerbit penerbit = new Penerbit();
        Buku buku = new Buku();

        System.out.println("--- TESTING USER ---");
        user.index(); user.store(); user.update(); user.destroy();

        System.out.println("\n--- TESTING PENERBIT ---");
        penerbit.index(); penerbit.store(); penerbit.update(); penerbit.destroy();

        System.out.println("\n--- TESTING BUKU ---");
        buku.index(); buku.store(); buku.update(); buku.destroy();
    }
}
