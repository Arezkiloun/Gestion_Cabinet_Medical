package interfac;

import model.Patient;
import service.PatientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientFrame extends JFrame {

    private final PatientService service = new PatientService();
    private String role; // "ASSISTANT" ou "MEDECIN"

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtDateNaissance;
    private JTextField txtAdresse;
    private JTextField txtTelephone;
    private JTextField txtEmail;

    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JButton btnFermer;

    // === Constructeur utilisé par le menu, avec rôle ===
    public PatientFrame(String role) {
        this.role = role;
        initUI();
        charger();
        appliquerRestrictionsRole();
    }

    // === Ancien constructeur pour tester (par défaut assistant) ===
    public PatientFrame() {
        this("ASSISTANT");
    }

    private void initUI() {
        setTitle("Gestion des Patients");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== TABLE =====
        model = new DefaultTableModel(
                new Object[]{"ID", "Nom", "Prénom", "Date Naissance", "Adresse", "Téléphone", "Email"},
                0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) remplirFormulaireDepuisTable();
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== FORMULAIRE =====
        JPanel form = new JPanel(new GridLayout(3, 4, 5, 5));

        form.add(new JLabel("Nom :"));
        txtNom = new JTextField();
        form.add(txtNom);

        form.add(new JLabel("Prénom :"));
        txtPrenom = new JTextField();
        form.add(txtPrenom);

        form.add(new JLabel("Date Naissance (yyyy-MM-dd) :"));
        txtDateNaissance = new JTextField();
        form.add(txtDateNaissance);

        form.add(new JLabel("Adresse :"));
        txtAdresse = new JTextField();
        form.add(txtAdresse);

        form.add(new JLabel("Téléphone :"));
        txtTelephone = new JTextField();
        form.add(txtTelephone);

        form.add(new JLabel("Email :"));
        txtEmail = new JTextField();
        form.add(txtEmail);

        add(form, BorderLayout.SOUTH);

        // ===== BOUTONS =====
        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.Y_AXIS));

        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnFermer = new JButton("Fermer");

        btnAjouter.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnModifier.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSupprimer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFermer.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBoutons.add(btnAjouter);
        panelBoutons.add(Box.createVerticalStrut(10));
        panelBoutons.add(btnModifier);
        panelBoutons.add(Box.createVerticalStrut(10));
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(Box.createVerticalStrut(10));
        panelBoutons.add(btnFermer);

        add(panelBoutons, BorderLayout.EAST);

        // ===== ACTIONS =====
        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnFermer.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void charger() {
        model.setRowCount(0);
        List<Patient> liste = service.listerPatients();
        for (Patient p : liste) {
            model.addRow(new Object[]{
                    p.getIdPatient(),
                    p.getNom(),
                    p.getPrenom(),
                    p.getDateNaissance(),
                    p.getAdresse(),
                    p.getTelephone(),
                    p.getEmail()
            });
        }
    }

    private void remplirFormulaireDepuisTable() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtNom.setText(String.valueOf(model.getValueAt(row, 1)));
            txtPrenom.setText(String.valueOf(model.getValueAt(row, 2)));
            txtDateNaissance.setText(String.valueOf(model.getValueAt(row, 3)));
            txtAdresse.setText(String.valueOf(model.getValueAt(row, 4)));
            txtTelephone.setText(String.valueOf(model.getValueAt(row, 5)));
            txtEmail.setText(String.valueOf(model.getValueAt(row, 6)));
        }
    }

    private void ajouter() {
        try {
            Patient p = new Patient(
                    txtNom.getText().trim(),
                    txtPrenom.getText().trim(),
                    txtDateNaissance.getText().trim(),
                    txtAdresse.getText().trim(),
                    txtTelephone.getText().trim(),
                    txtEmail.getText().trim()
            );

            boolean ok = service.ajouterPatient(p);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Patient ajouté");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur ajout patient");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void modifier() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionne un patient");
            return;
        }

        try {
            int id = (int) model.getValueAt(row, 0);

            Patient p = new Patient(
                    txtNom.getText().trim(),
                    txtPrenom.getText().trim(),
                    txtDateNaissance.getText().trim(),
                    txtAdresse.getText().trim(),
                    txtTelephone.getText().trim(),
                    txtEmail.getText().trim()
            );
            p.setIdPatient(id);

            boolean ok = service.modifierPatient(p);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Patient modifié");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur modification patient");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void supprimer() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionne un patient");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Supprimer ce patient ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = service.supprimerPatient(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Patient supprimé");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur suppression patient");
            }
        }
    }

    // === ICI : on bride le médecin en lecture seule ===
    private void appliquerRestrictionsRole() {
        if ("MEDECIN".equalsIgnoreCase(role)) {
            // Désactiver les boutons d'édition
            btnAjouter.setEnabled(false);
            btnModifier.setEnabled(false);
            btnSupprimer.setEnabled(false);

            // Rendre les champs non éditables
            txtNom.setEditable(false);
            txtPrenom.setEditable(false);
            txtDateNaissance.setEditable(false);
            txtAdresse.setEditable(false);
            txtTelephone.setEditable(false);
            txtEmail.setEditable(false);
        }
    }
}
