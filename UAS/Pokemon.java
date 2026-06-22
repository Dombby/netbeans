package uas;

import java.awt.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

public class Pokemon extends JFrame {

    private JTextField txtNama, txtLevel;
    private JComboBox<String> comboTipe, comboTrainer;
    private Map<String, Integer> mapTrainer = new LinkedHashMap<>();
    private Integer idPokemon;

    public Pokemon(Integer idPokemon) {
        this.idPokemon = idPokemon;
        setTitle(idPokemon == null ? "Tambah Pokemon" : "Ubah Pokemon");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nama Pokemon:"), gbc);
        txtNama = new JTextField(15);
        gbc.gridx = 1;
        add(txtNama, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Tipe:"), gbc);
        comboTipe = new JComboBox<>(new String[]{
            "Fire", "Water", "Grass", "Electric", "Psychic",
            "Rock", "Ground", "Flying", "Bug", "Normal",
            "Poison", "Ghost", "Dragon", "Dark", "Steel",
            "Ice", "Fighting", "Fairy",
            "Fire/Flying", "Water/Psychic", "Grass/Poison"
        });
        gbc.gridx = 1;
        add(comboTipe, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Level:"), gbc);
        txtLevel = new JTextField(15);
        gbc.gridx = 1;
        add(txtLevel, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Trainer:"), gbc);
        comboTrainer = new JComboBox<>();
        gbc.gridx = 1;
        add(comboTrainer, gbc);

        JButton btnSimpan = new JButton("Simpan");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(btnSimpan, gbc);

        btnSimpan.addActionListener(e -> simpan());

        loadTrainer();
        if (idPokemon != null) loadData();
    }

    private void loadTrainer() {
        String sql = "SELECT id_trainer, nama_trainer FROM tb_trainer ORDER BY nama_trainer";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String nama = rs.getString("nama_trainer");
                mapTrainer.put(nama, rs.getInt("id_trainer"));
                comboTrainer.addItem(nama);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void loadData() {
        String sql = "SELECT * FROM tb_pokemon WHERE id_pokemon=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPokemon);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNama.setText(rs.getString("nama_pokemon"));
                comboTipe.setSelectedItem(rs.getString("tipe"));
                txtLevel.setText(String.valueOf(rs.getInt("level")));
                int idTrainer = rs.getInt("id_trainer");
                for (Map.Entry<String, Integer> entry : mapTrainer.entrySet()) {
                    if (entry.getValue() == idTrainer) {
                        comboTrainer.setSelectedItem(entry.getKey());
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void simpan() {
        String nama = txtNama.getText().trim();
        String tipe = (String) comboTipe.getSelectedItem();
        String levelStr = txtLevel.getText().trim();
        String trainerNama = (String) comboTrainer.getSelectedItem();

        if (nama.isEmpty() || levelStr.isEmpty() || trainerNama == null) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi.");
            return;
        }

        int level;
        try {
            level = Integer.parseInt(levelStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Level harus berupa angka.");
            return;
        }

        int idTrainer = mapTrainer.get(trainerNama);

        String sql = idPokemon == null
            ? "INSERT INTO tb_pokemon (nama_pokemon, tipe, level, id_trainer) VALUES (?,?,?,?)"
            : "UPDATE tb_pokemon SET nama_pokemon=?, tipe=?, level=?, id_trainer=? WHERE id_pokemon=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, tipe);
            ps.setInt(3, level);
            ps.setInt(4, idTrainer);
            if (idPokemon != null) ps.setInt(5, idPokemon);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
            this.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}