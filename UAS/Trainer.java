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
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nama Trainer:"), gbc);
        txtNama = new JTextField(15);
        gbc.gridx = 1;
        add(txtNama, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Kota Asal:"), gbc);
        txtKota = new JTextField(15);
        gbc.gridx = 1;
        add(txtKota, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Tgl Daftar (yyyy-mm-dd):"), gbc);
        txtTanggal = new JTextField(15);
        gbc.gridx = 1;
        add(txtTanggal, gbc);

        JButton btnSimpan = new JButton("Simpan");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnSimpan, gbc);

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

        String sql;
        if (idTrainer == null) {
            sql = "INSERT INTO tb_trainer (nama_trainer, kota_asal, tanggal_daftar) VALUES (?,?,?)";
        } else {
            sql = "UPDATE tb_trainer SET nama_trainer=?, kota_asal=?, tanggal_daftar=? WHERE id_trainer=?";
        }

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
            JOptionPane.showMessageDialog(this, "Format tanggal salah. Gunakan yyyy-mm-dd.");
        }
    }
}
