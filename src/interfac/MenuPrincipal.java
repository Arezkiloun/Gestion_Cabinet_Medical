package interfac;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private String role;            // "MEDECIN" ou "ASSISTANT"
    private String nomUtilisateur;

    private JButton btnPatients;
    private JButton btnRendezVous;
    private JButton btnFactures;
    private JButton btnOrdonnances;
    private JButton btnDossiers;
    private JButton btnConsultations;
    private JButton btnRdvMedecin;
    private JButton btnLogout;  // <-- bouton déconnexion

    public MenuPrincipal(String role, String nomUtilisateur) {
        this.role = role;
        this.nomUtilisateur = nomUtilisateur;
        initUI();
    }

    public MenuPrincipal() {
        this("MEDECIN", "Utilisateur Test");
    }

    private void initUI() {
        setTitle("Cabinet Médical - Menu Principal");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Système de gestion d’un cabinet médical", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        add(titre, BorderLayout.NORTH);

        // ===== PANEL BOUTONS =====
        JPanel panelBoutons = new JPanel(new GridLayout(4, 2, 10, 10));

        btnPatients       = new JButton("Gestion des Patients");
        btnRendezVous     = new JButton("Gestion des Rendez-vous");
        btnFactures       = new JButton("Gestion des Factures");
        btnOrdonnances    = new JButton("Gestion des Ordonnances");
        btnDossiers       = new JButton("Gestion des Dossiers Médicaux");
        btnConsultations  = new JButton("Gestion des Consultations");
       

        panelBoutons.add(btnPatients);
        panelBoutons.add(btnRendezVous);
        panelBoutons.add(btnFactures);
        panelBoutons.add(btnOrdonnances);
        panelBoutons.add(btnDossiers);
        panelBoutons.add(btnConsultations);
      

        JPanel centre = new JPanel(new GridBagLayout());
        centre.add(panelBoutons);
        add(centre, BorderLayout.CENTER);

        // ===== BAS DE PAGE : Info + Déconnexion =====
        JLabel lblInfo = new JLabel(
                "Connecté : " + nomUtilisateur + " (" + role + ")",
                SwingConstants.CENTER
        );

        btnLogout = new JButton("Déconnexion");
        btnLogout.setBackground(new Color(200, 60, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);

        btnLogout.addActionListener(e -> {
            this.dispose();                    // fermer menu
            new LoginFrame().setVisible(true); // réafficher Login
        });

        JPanel bas = new JPanel(new BorderLayout());
        bas.add(lblInfo, BorderLayout.CENTER);
        bas.add(btnLogout, BorderLayout.EAST);

        add(bas, BorderLayout.SOUTH);

        // ===== ACTIONS DES BOUTONS =====
        btnPatients.addActionListener(e -> new PatientFrame(role));
        btnRendezVous.addActionListener(e -> new RendezVousFrame(role));
        btnFactures.addActionListener(e -> new FactureFrame(role));
        btnOrdonnances.addActionListener(e -> new OrdonnanceFrame());
        btnDossiers.addActionListener(e -> new DossierMedicalFrame());
        btnConsultations.addActionListener(e -> new ConsultationFrame());
       

        appliquerRestrictionsRole();
    }

    private void appliquerRestrictionsRole() {

        if ("ASSISTANT".equalsIgnoreCase(role)) {

            // Assistant autorisé
            btnPatients.setEnabled(true);
            btnRendezVous.setEnabled(true);
            btnFactures.setEnabled(true);

            // Pas autorisé
            btnOrdonnances.setEnabled(false);
            btnDossiers.setEnabled(false);
            btnConsultations.setEnabled(false);
         

        } else if ("MEDECIN".equalsIgnoreCase(role)) {

            // Médecin autorisé
            btnOrdonnances.setEnabled(true);
            btnDossiers.setEnabled(true);
            btnConsultations.setEnabled(true);
            

            // Pas autorisé pour médecin
            btnRendezVous.setEnabled(true);
            btnFactures.setEnabled(true );
            btnPatients.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal a = new MenuPrincipal("MEDECIN", "Admin");
            a.setVisible(true);
        });
    }
}
