package uas;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Pokemon extends JFrame {

    private JTextField txtNama, txtLevel;
    private JComboBox<String> cbTipe, cbTrainer;
    private Integer idPokemon;
    private DefaultComboBoxModel<String> trainerModel;

    private java.util.ArrayList<Integer> listIdTrainer = new java.util.ArrayList<>();

    private final Color BLUE_SYS = new Color(0, 162, 232); 
    private final Color RED_BALL = new Color(220, 53, 69);  
    private final Color TEXT_DARK = new Color(45, 45, 45);

    public Pokemon(Integer idPokemon) {
        this.idPokemon = idPokemon;
        setTitle(idPokemon == null ? "Pusat Pokémon - Tambah Pokémon" : "Pusat Pokémon - Ubah Pokémon");
        setSize(450, 360);
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

        JLabel lblNama = createFormLabel("Nama Pokémon:");
        gbcForm.gridx = 0; gbcForm.gridy = 0; gbcForm.weightx = 0.35;
        formPanel.add(lblNama, gbcForm);

        txtNama = createCustomTextField();
        gbcForm.gridx = 1; gbcForm.weightx = 0.65;
        formPanel.add(txtNama, gbcForm);

        JLabel lblTipe = createFormLabel("Tipe:");
        gbcForm.gridx = 0; gbcForm.gridy = 1; gbcForm.weightx = 0.35;
        formPanel.add(lblTipe, gbcForm);

        String[] tipePokemon = {"Normal", "Fire", "Water", "Grass", "Electric", "Ice", "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Steel", "Fairy"};
        cbTipe = new JComboBox<>(tipePokemon);
        cbTipe.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbcForm.gridx = 1; gbcForm.weightx = 0.65;
        formPanel.add(cbTipe, gbcForm);

        JLabel lblLevel = createFormLabel("Level:");
        gbcForm.gridx = 0; gbcForm.gridy = 2; gbcForm.weightx = 0.35;
        formPanel.add(lblLevel, gbcForm);

        txtLevel = createCustomTextField();
        gbcForm.gridx = 1; gbcForm.weightx = 0.65;
        formPanel.add(txtLevel, gbcForm);

        JLabel lblTrainer = createFormLabel("Trainer:");
        gbcForm.gridx = 0; gbcForm.gridy = 3; gbcForm.weightx = 0.35;
        formPanel.add(lblTrainer, gbcForm);

        trainerModel = new DefaultComboBoxModel<>();
        cbTrainer = new JComboBox<>(trainerModel);
        cbTrainer.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gbcForm.gridx = 1; gbcForm.weightx = 0.65;
        formPanel.add(cbTrainer, gbcForm);

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

        loadTrainers();
        if (idPokemon != null) {
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

    private void loadTrainers() {
        String sql = "SELECT id_trainer, nama_trainer FROM tb_trainer ORDER BY nama_trainer ASC";
        listIdTrainer.clear();
        trainerModel.removeAllElements();
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                listIdTrainer.add(rs.getInt("id_trainer"));
                trainerModel.addElement(rs.getString("nama_trainer"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data trainer: " + e.getMessage());
        }
    }

    private void loadData() {
        String sql = "SELECT * FROM tb_pokemon WHERE id_pokemon=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPokemon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    txtNama.setText(rs.getString("nama_pokemon"));
                    cbTipe.setSelectedItem(rs.getString("tipe"));
                    txtLevel.setText(String.valueOf(rs.getInt("level")));
                    
                    int currentTrainerId = rs.getInt("id_trainer");
                    int indexToSelect = listIdTrainer.indexOf(currentTrainerId);
                    if (indexToSelect != -1) {
                        cbTrainer.setSelectedIndex(indexToSelect);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

   private void simpan() {
    String nama = txtNama.getText().trim();
    String tipe = cbTipe.getSelectedItem().toString();
    String strLevel = txtLevel.getText().trim();

    if (nama.isEmpty() || strLevel.isEmpty() || cbTrainer.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Semua kolom form wajib diisi!");
        return;
    }

    int level;
    try {
        level = Integer.parseInt(strLevel);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Level harus berupa angka bulat valid!");
        return;
    }

    int idTrainerDipilih = listIdTrainer.get(cbTrainer.getSelectedIndex());

    String sql;
    if (idPokemon == null) {
        sql = "INSERT INTO tb_pokemon (nama_pokemon, tipe, level, id_trainer) VALUES (?,?,?,?)";
    } else {
        sql = "UPDATE tb_pokemon SET nama_pokemon=?, tipe=?, level=?, id_trainer=? WHERE id_pokemon=?";
    }

    try (Connection conn = Koneksi.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, nama);
        ps.setString(2, tipe);
        ps.setInt(3, level);
        ps.setInt(4, idTrainerDipilih);
        
        if (idPokemon != null) {
            ps.setInt(5, idPokemon);
        }
        
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data pendaftaran Pokémon sukses diproses!");
        this.dispose();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error Simpan: " + e.getMessage());
    }
}
}  