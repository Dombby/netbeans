package uas;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Trainer extends JFrame {

    private JTextField txtNama, txtKota, txtTanggal;
    private Integer idTrainer;

    public Trainer(Integer idTrainer) {
        this.idTrainer = idTrainer;
        setTitle(idTrainer == null ? "Tambah Trainer" : "Ubah Trainer");
        setSize(380, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtNama = new JTextField();
        txtKota = new JTextField();
        txtTanggal = new JTextField();

        panel.add(new JLabel("Nama Trainer:"));
        panel.add(txtNama);

        panel.add(new JLabel("Kota Asal:"));
        panel.add(txtKota);

        panel.add(new JLabel("Tgl Daftar (yyyy-mm-dd):"));
        panel.add(txtTanggal);

        JButton btnSimpan = new JButton("Simpan");
        panel.add(new JLabel()); 
        panel.add(btnSimpan);

        add(panel);

        btnSimpan.addActionListener(e -> simpan());

        if (idTrainer != null) {
            loadData();
        } else {
            txtTanggal.setText(java.time.LocalDate.now().toString());
        }
    }

    private void loadData() {
        String sql = "SELECT * FROM tb_trainer WHERE id_trainer=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTrainer);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNama.setText(rs.getString("nama_trainer"));
                txtKota.setText(rs.getString("kota_asal"));
                txtTanggal.setText(rs.getDate("tanggal_daftar").toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void simpan() {
        String nama = txtNama.getText().trim();
        String kota = txtKota.getText().trim();
        String tanggal = txtTanggal.getText().trim();

        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama trainer wajib diisi.");
            return;
        }

        String sql = idTrainer == null
            ? "INSERT INTO tb_trainer (nama_trainer, kota_asal, tanggal_daftar) VALUES (?,?,?)"
            : "UPDATE tb_trainer SET nama_trainer=?, kota_asal=?, tanggal_daftar=? WHERE id_trainer=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, kota);
            ps.setDate(3, Date.valueOf(tanggal));
            if (idTrainer != null) ps.setInt(4, idTrainer);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
            this.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal salah. Gunakan yyyy-mm-dd.\nContoh: 2026-01-15");
        }
    }
}