package uas;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public Login() {
        setTitle("Login - Pokemon Trainer Management");
        setSize(400, 320); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(220, 53, 69)); 
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(10, 10, 10, 10);
        gbcMain.fill = GridBagConstraints.BOTH;

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(245, 245, 240)); 
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2d.setColor(new Color(45, 45, 45)); 
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);
                g2d.dispose();
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblTitle = new JLabel("POKÉMON TRAINER LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTitle.setForeground(new Color(45, 45, 45));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        contentPanel.add(lblTitle, gbc);

        gbc.gridwidth = 1;

        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblUser.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(lblUser, gbc);

        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(45, 45, 45), 2),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        gbc.gridx = 1;
        contentPanel.add(txtUsername, gbc);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblPass.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(lblPass, gbc);

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(45, 45, 45), 2),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        gbc.gridx = 1;
        contentPanel.add(txtPassword, gbc);

        JButton btnLogin = new JButton("LOGIN") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(220, 180, 0)); // Kuning gelap
                } else {
                    g2d.setColor(new Color(255, 204, 0)); // Kuning Pikachu
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2d.setColor(new Color(45, 45, 45));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnLogin.setForeground(new Color(45, 45, 45));
        btnLogin.setContentAreaFilled(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        contentPanel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> Login());
        getRootPane().setDefaultButton(btnLogin);

        mainPanel.add(contentPanel, gbcMain);
        add(mainPanel);
    }

    private void Login() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi.");
            return;
        }

        String sql = "SELECT * FROM tb_user WHERE username=? AND password=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang, " + user);
                new menu().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}