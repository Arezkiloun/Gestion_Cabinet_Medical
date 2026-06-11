package interfac;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUtilisateur;
    private JPasswordField txtMotDePasse;
    private JButton btnConnexion;
    private JComboBox<String> comboRole;

    public LoginFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Connexion - Cabinet médical");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Titre =====
        JLabel titre = new JLabel("Authentification", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 26));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titre, BorderLayout.NORTH);

        // ===== Panel principal =====
        JPanel panelCentre = new JPanel();
        panelCentre.setLayout(new GridLayout(4, 2, 10, 10));
        panelCentre.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelCentre.setBackground(new Color(245, 245, 245));

        panelCentre.add(new JLabel("Nom d'utilisateur :", SwingConstants.RIGHT));
        txtUtilisateur = new JTextField();
        panelCentre.add(txtUtilisateur);

        panelCentre.add(new JLabel("Mot de passe :", SwingConstants.RIGHT));
        txtMotDePasse = new JPasswordField();
        panelCentre.add(txtMotDePasse);

        panelCentre.add(new JLabel("Rôle :", SwingConstants.RIGHT));
        comboRole = new JComboBox<>(new String[]{"ASSISTANT", "MEDECIN"});
        panelCentre.add(comboRole);

        add(panelCentre, BorderLayout.CENTER);

        // ===== Bouton Connexion =====
        btnConnexion = new JButton("Se connecter");
        btnConnexion.setFont(new Font("Arial", Font.BOLD, 16));
        btnConnexion.setFocusPainted(false);
        btnConnexion.setBackground(new Color(61, 122, 204));
        btnConnexion.setForeground(Color.WHITE);
        btnConnexion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(new Color(245, 245, 245));
        panelBtn.add(btnConnexion);

        add(panelBtn, BorderLayout.SOUTH);

        // Action
        btnConnexion.addActionListener(e -> seConnecter());
    }

    private void seConnecter() {
        String user = txtUtilisateur.getText().trim();
        String pwd = new String(txtMotDePasse.getPassword());
        String role = comboRole.getSelectedItem().toString();

        boolean ok = false;

        if (role.equals("ASSISTANT")) {
            if (user.equals("ass") && pwd.equals("123")) {
                ok = true;
            }
        } else if (role.equals("MEDECIN")) {
            if (user.equals("med") && pwd.equals("123")) {
                ok = true;
            }
        }

        if (ok) {
            MenuPrincipal menu = new MenuPrincipal(role, user);
            menu.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Identifiants incorrects",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
