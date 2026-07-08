package uas;

import java.awt.*;
import javax.swing.*;

public class menu extends JFrame {

    public menu() {
        setTitle("Menu Utama Pokemon Trainer Management");
        setSize(420, 380); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(220, 53, 69)); 
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(15, 15, 15, 15);
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
        contentPanel.setLayout(new GridLayout(5, 1, 12, 12)); // Tetap menggunakan GridLayout bawaanmu

        JLabel lblTitle = new JLabel("POKÉDEX MENU", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setForeground(new Color(45, 45, 45));

        JButton btnTrainer = createPokemonButton("Data Trainer", new Color(255, 204, 0), new Color(220, 180, 0));
        JButton btnPokemon = createPokemonButton("Data Pokemon", new Color(255, 204, 0), new Color(220, 180, 0));
        JButton btnReport = createPokemonButton("Report Pokemon per Trainer", new Color(255, 204, 0), new Color(220, 180, 0));
        
        JButton btnLogout = createPokemonButton("Logout", new Color(108, 117, 125), new Color(73, 80, 87));
        btnLogout.setForeground(Color.WHITE); 

        contentPanel.add(lblTitle);
        contentPanel.add(btnTrainer);
        contentPanel.add(btnPokemon);
        contentPanel.add(btnReport);
        contentPanel.add(btnLogout);

        btnTrainer.addActionListener(e -> new datatrainer().setVisible(true));
        btnPokemon.addActionListener(e -> new datapokemon().setVisible(true));
        btnReport.addActionListener(e -> new Report().setVisible(true));
        btnLogout.addActionListener(e -> {
            this.dispose();
            new Login().setVisible(true);
        });

        mainPanel.add(contentPanel, gbcMain);
        add(mainPanel);
    }

    private JButton createPokemonButton(String text, Color baseColor, Color pressedColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(pressedColor);
                } else {
                    g2d.setColor(baseColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2d.setColor(new Color(45, 45, 45));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        if (baseColor.equals(new Color(255, 204, 0))) {
            button.setForeground(new Color(45, 45, 45)); // Teks gelap untuk tombol kuning
        }
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new menu().setVisible(true));
    }
}