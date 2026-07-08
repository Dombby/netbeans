package uas;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class datatrainer extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    
    private final Color BLUE_SYS = new Color(0, 162, 232); // Biru Layar PC Utama
    private final Color RED_BALL = new Color(220, 53, 69);   // Merah Aksen Poké Ball
    private final Color TEXT_DARK = new Color(45, 45, 45);   // Hitam Arang Elegan

    public datatrainer() {
        setTitle("Pusat Pokémon - Data Trainer");
        setSize(750, 480); 
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

        JLabel lblTitle = new JLabel("TRAINER REGISTRATION SYSTEM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Monospaced", Font.BOLD, 20));
        lblTitle.setForeground(BLUE_SYS);
        contentPanel.add(lblTitle, BorderLayout.NORTH);

        model = new DefaultTableModel(
            new Object[]{"ID", "Nama Trainer", "Kota Asal", "Tgl Daftar"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        table.getTableHeader().setFont(new Font("Monospaced", Font.BOLD, 12));
        table.getTableHeader().setBackground(TEXT_DARK);
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(TEXT_DARK, 2));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panelBtn.setOpaque(false);

        JButton btnTambah = createSysButton("TAMBAH", Color.WHITE, TEXT_DARK);
        JButton btnUbah = createSysButton("UBAH", Color.WHITE, TEXT_DARK);
        JButton btnHapus = createSysButton("HAPUS", RED_BALL, Color.WHITE);
        JButton btnRefresh = createSysButton("REFRESH", Color.WHITE, TEXT_DARK);
        JButton btnTutup = createSysButton("TUTUP", TEXT_DARK, Color.WHITE);

        panelBtn.add(btnTambah);
        panelBtn.add(btnUbah);
        panelBtn.add(btnHapus);
        panelBtn.add(btnRefresh);
        panelBtn.add(btnTutup);
        contentPanel.add(panelBtn, BorderLayout.SOUTH);

        add(mainPanel);

        btnTambah.addActionListener(e -> {
            new Trainer(null).setVisible(true);
            loadData();
        });

        btnUbah.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data trainer terlebih dahulu.");
                return;
            }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        new Trainer(id).setVisible(true);
            loadData();
        });

        btnHapus.addActionListener(e -> hapusData());
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
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        button.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public void loadData() {
        model.setRowCount(0);
        String sql = "SELECT * FROM tb_trainer ORDER BY id_trainer ASC";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_trainer"),
                    rs.getString("nama_trainer"),
                    rs.getString("kota_asal"),
                    rs.getDate("tanggal_daftar")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void hapusData() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data trainer terlebih dahulu.");
            return;
        }
        String namaTrainer = model.getValueAt(row, 1).toString();
        String id = model.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
            "Hapus trainer \"" + namaTrainer + "\" beserta seluruh data Pokémon miliknya?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM tb_trainer WHERE id_trainer=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data trainer berhasil dihapus.");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}