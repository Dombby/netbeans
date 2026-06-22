package uas;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class Report extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTotal;

    public Report() {
        setTitle("Report Pokemon per Trainer");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        
        JLabel lblTitle = new JLabel("LAPORAN POKEMON PER TRAINER", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(lblTitle, BorderLayout.NORTH);

        model = new DefaultTableModel(
            new Object[]{"No", "Nama Trainer", "Kota Asal", "Nama Pokemon", "Tipe", "Level"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new BorderLayout());
        lblTotal = new JLabel("Total Pokemon: 0", SwingConstants.LEFT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));

        JButton btnRefresh = new JButton("Refresh");
        JButton btnTutup = new JButton("Tutup");
        JPanel panelBtn = new JPanel();
        panelBtn.add(btnRefresh);
        panelBtn.add(btnTutup);
        panelBawah.add(lblTotal, BorderLayout.WEST);
        panelBawah.add(panelBtn, BorderLayout.EAST);
        add(panelBawah, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
        btnTutup.addActionListener(e -> this.dispose());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);

        String sql = "SELECT t.nama_trainer, t.kota_asal, " +
                     "p.nama_pokemon, p.tipe, p.level " +
                     "FROM tb_trainer t " +
                     "LEFT JOIN tb_pokemon p ON t.id_trainer = p.id_trainer " +
                     "ORDER BY t.nama_trainer, p.level DESC";

        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            int no = 1;
            int totalPokemon = 0;

            while (rs.next()) {
                String namaPokemon = rs.getString("nama_pokemon");
                model.addRow(new Object[]{
                    no++,
                    rs.getString("nama_trainer"),
                    rs.getString("kota_asal"),
                    namaPokemon != null ? namaPokemon : "-",
                    rs.getString("tipe") != null ? rs.getString("tipe") : "-",
                    namaPokemon != null ? rs.getInt("level") : "-"
                });
                if (namaPokemon != null) totalPokemon++;
            }

            lblTotal.setText("Total Pokemon: " + totalPokemon);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}