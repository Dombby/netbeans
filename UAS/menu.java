package uas;

import java.awt.*;
import javax.swing.*;

public class menu extends JFrame {

    public menu() {
        setTitle("Menu Utama Pokemon Trainer Management");
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel lblTitle = new JLabel("MENU UTAMA", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnTrainer = new JButton("Data Trainer");
        JButton btnPokemon = new JButton("Data Pokemon");
        JButton btnReport = new JButton("Report Pokemon per Trainer");
        JButton btnLogout = new JButton("Logout");

        add(lblTitle);
        add(btnTrainer);
        add(btnPokemon);
        add(btnReport);
        add(btnLogout);

        btnTrainer.addActionListener(e -> new datatrainer().setVisible(true));
        btnPokemon.addActionListener(e -> new datapokemon().setVisible(true));
        btnReport.addActionListener(e -> new Report().setVisible(true));
        btnLogout.addActionListener(e -> {
            this.dispose();
            new Login().setVisible(true);
        });
    }
}
    