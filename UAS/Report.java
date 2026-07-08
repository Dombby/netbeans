package uas;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class Report extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTotal;
    
    private final Color BLUE_SYS = new Color(0, 162, 232); 
    private final Color RED_BALL = new Color(220, 53, 69);   
    private final Color TEXT_DARK = new Color(45, 45, 45);

    public Report() {
        setTitle("Pusat Pokémon - Laporan Data");
        setSize(700, 480); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(RED_BALL);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 25));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JLabel lblTitle = new JLabel("TRAINER & POKÉMON REPORT SYSTEM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Monospaced", Font.BOLD, 20));
        lblTitle.setForeground(BLUE_SYS);
        contentPanel.add(lblTitle, BorderLayout.NORTH);

        model = new DefaultTableModel(
            new Object[]{"No", "Nama Trainer", "Kota Asal", "Nama Pokémon", "Tipe", "Level"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setRowHeight(25);
        
        table.getTableHeader().setFont(new Font("Monospaced", Font.BOLD, 12));
        table.getTableHeader().setBackground(TEXT_DARK);
        table.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Pengaturan Lebar Kolom Proposional
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(140);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(TEXT_DARK, 2));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setOpaque(false);
        
        lblTotal = new JLabel("TOTAL POKÉMON: 0", SwingConstants.LEFT);
        lblTotal.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblTotal.setForeground(TEXT_DARK);
        lblTotal.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBtn.setOpaque(false);
        
        JButton btnRefresh = createSysButton("REFRESH", Color.WHITE, TEXT_DARK);
        JButton btnTutup = createSysButton("TUTUP", TEXT_DARK, Color.WHITE);
        
        panelBtn.add(btnRefresh);
        panelBtn.add(btnTutup);
        
        panelBawah.add(lblTotal, BorderLayout.WEST);
        panelBawah.add(panelBtn, BorderLayout.EAST);
        contentPanel.add(panelBawah, BorderLayout.SOUTH);

        add(mainPanel);

        btnRefresh.addActionListener(e -> loadData());
        btnTutup.addActionListener(e -> this.dispose());

        loadData();
    }

    private JButton createSysButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(getBackground().darker());
                } else {
                    g2d.setColor(getBackground());
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.setColor(TEXT_DARK);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Monospaced", Font.BOLD, 12));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
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

        lblTotal.setText("TOTAL POKÉMON: " + totalPokemon);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Report().setVisible(true));
    }
}