package uas;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class datapokemon extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public datapokemon() {
        setTitle("Data Pokemon");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Nama Pokemon", "Tipe", "Level", "Trainer"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBtn = new JPanel();
        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnRefresh = new JButton("Refresh");
        panelBtn.add(btnTambah);
        panelBtn.add(btnUbah);
        panelBtn.add(btnHapus);
        panelBtn.add(btnRefresh);
        add(panelBtn, BorderLayout.SOUTH);

        btnTambah.addActionListener(e -> {
            new Pokemon(null).setVisible(true);
            loadData();
        });

        btnUbah.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu.");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            new Pokemon(id).setVisible(true);
            loadData();
        });

        btnHapus.addActionListener(e -> hapusData());
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        String sql = "SELECT p.id_pokemon, p.nama_pokemon, p.tipe, p.level, t.nama_trainer " +
                     "FROM tb_pokemon p LEFT JOIN tb_trainer t ON p.id_trainer = t.id_trainer";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_pokemon"),
                    rs.getString("nama_pokemon"),
                    rs.getString("tipe"),
                    rs.getInt("level"),
                    rs.getString("nama_trainer")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void hapusData() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Hapus pokemon ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM tb_pokemon WHERE id_pokemon=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
