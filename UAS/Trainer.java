package uas;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Trainer extends JFrame {

    private JTextField txtNama, txtKota, txtTanggal;
    private Integer idTrainer; 

    private final Color BLUE_SYS = new Color(0, 162, 232); 
    private final Color RED_BALL = new Color(220, 53, 69);   
    private final Color TEXT_DARK = new Color(45, 45, 45);

    public Trainer(Integer idTrainer) {
        this.idTrainer = idTrainer;
        setTitle(idTrainer == null ? "Pusat Pokémon - Tambah Trainer" : "Pusat Pokémon - Ubah Trainer");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(RED_BALL);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 20));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;

        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(BLUE_SYS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        formPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(6, 6, 6, 6);
        gbcForm.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNama = createFormLabel("Nama Trainer:");
        gbcForm.gridx = 0; gbcForm.gridy = 0; gbcForm.weightx = 0.35;
        formPanel.add(lblNama, gbcForm);

        txtNama = createCustomTextField();
        gbcForm.gridx = 1; gbcForm.weightx = 0.65;
        formPanel.add(txtNama, gbcForm);

        JLabel lblKota = createFormLabel("Kota Asal:");
        gbcForm.gridx = 0; gbcForm.gridy = 1; gbcForm.weightx = 0.35;
        formPanel.add(lblKota, gbcForm);

        txtKota = createCustomTextField();
        gbcForm.gridx = 1; gbcForm.weightx = 0.65;
        formPanel.add(txtKota, gbcForm);

        JLabel lblTanggal = createFormLabel("Tgl Daftar:");
        gbcForm.gridx = 0; gbcForm.gridy = 2; gbcForm.weightx = 0.35;
        formPanel.add(lblTanggal, gbcForm);

        txtTanggal = createCustomTextField();
        txtTanggal.setText(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
        gbcForm.gridx = 1; gbcForm.weightx = 0.65;
        formPanel.add(txtTanggal, gbcForm);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0; gbc.weighty = 0.8;
        contentPanel.add(formPanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        JButton btnSimpan = createSysButton("SIMPAN DATA", Color.WHITE, TEXT_DARK);
        JButton btnBatal = createSysButton("BATAL", TEXT_DARK, Color.WHITE);
        
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(buttonPanel, gbc);

        add(mainPanel);

        btnBatal.addActionListener(e -> this.dispose());
        btnSimpan.addActionListener(e -> simpan());

        if (idTrainer != null) {
            loadData();
        }
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Monospaced", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createCustomTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_DARK, 1),
                BorderFactory.createEmptyBorder(3, 5, 3, 5)
        ));
        return tf;
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
        button.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadData() {
        String sql = "SELECT * FROM tb_trainer WHERE id_trainer=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTrainer);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    txtNama.setText(rs.getString("nama_trainer"));
                    txtKota.setText(rs.getString("kota_asal")); // Sesuai dengan penamaan database kamu
                    txtTanggal.setText(rs.getString("tanggal_daftar"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Load Data: " + e.getMessage());
        }
    }

    private void simpan() {
        String nama = txtNama.getText().trim();
        String kota = txtKota.getText().trim();
        String tanggal = txtTanggal.getText().trim();

        if (nama.isEmpty() || kota.isEmpty() || tanggal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom form wajib diisi!");
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
            
            try {
                ps.setDate(3, java.sql.Date.valueOf(tanggal));
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan format: YYYY-MM-DD\nContoh: 2026-07-07");
                return;
            }
            
            if (idTrainer != null) {
                ps.setInt(4, idTrainer);
            }
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data pendaftaran Trainer sukses diproses!");
            this.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Simpan: " + e.getMessage());
        }
    }
}