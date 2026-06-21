package uas;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Report extends JFrame {

    public Report() {
        setTitle("Report: Jumlah Pokemon per Trainer");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Nama Trainer", "Kota Asal", "Jumlah Pokemon"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        String sql = "SELECT t.nama_trainer, t.kota_asal, COUNT(p.id_pokemon) AS jumlah " +
                     "FROM tb_trainer t LEFT JOIN tb_pokemon p ON t.id_trainer = p.id_trainer " +
                     "GROUP BY t.id_trainer, t.nama_trainer, t.kota_asal " +
                     "ORDER BY jumlah DESC";

        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nama_trainer"),
                    rs.getString("kota_asal"),
                    rs.getInt("jumlah")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

        JButton btnTutup = new JButton("Tutup");
        btnTutup.addActionListener(e -> this.dispose());
        JPanel panelBtn = new JPanel();
        panelBtn.add(btnTutup);
        add(panelBtn, BorderLayout.SOUTH);
    }
}
