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
        setSize(680, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
            new Object[]{"ID", "Nama Pokemon", "Tipe", "Level", "Trainer"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBtn = new JPanel();
        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnTutup = new JButton("Tutup");
        panelBtn.add(btnTambah);
        panelBtn.add(btnUbah);
        panelBtn.add(btnHapus);
        panelBtn.add(btnRefresh);
        panelBtn.add(btnTutup);
        add(panelBtn, BorderLayout.SOUTH);

        btnTambah.addActionListener(e -> {
            new Pokemon(null).setVisible(true);
            loadData();
        });

        btnUbah.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data pokemon terlebih dahulu.");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            new Pokemon(id).setVisible(true);
            loadData();
        });

        btnHapus.addActionListener(e -> hapusData());
        btnRefresh.addActionListener(e -> loadData());
        btnTutup.addActionListener(e -> this.dispose());

        loadData();
    }

    public void loadData() {
        model.setRowCount(0);
        String sql = "SELECT p.id_pokemon, p.nama_pokemon, p.tipe, p.level, t.nama_trainer " +
                     "FROM tb_pokemon p " +
                     "LEFT JOIN tb_trainer t ON p.id_trainer = t.id_trainer " +
                     "ORDER BY t.nama_trainer, p.level DESC";
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
            JOptionPane.showMessageDialog(this, "Pilih data pokemon terlebih dahulu.");
            return;
        }
        String namaPokemon = (String) model.getValueAt(row, 1);
        int id = (int) model.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Hapus pokemon \"" + namaPokemon + "\"?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
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