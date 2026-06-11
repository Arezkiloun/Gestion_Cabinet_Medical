package interfac;

import model.Patient;
import model.RendezVous;
import service.PatientService;
import service.RendezVousService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RendezVousFrame extends JFrame {

    private final RendezVousService service = new RendezVousService();
    private final PatientService patientService = new PatientService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private String role; // "ASSISTANT" ou "MEDECIN"

    private JTable table;
    private DefaultTableModel model;

    private JComboBox<Patient> comboPatient;
    private JTextField txtDateHeure;
    private JTextField txtMotif;
    private JComboBox<String> comboStatut;

    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JButton btnFermer;

    // === Constructeur avec rôle ===
    public RendezVousFrame(String role) {
        this.role = role;
        initUI();
        chargerPatients();
        charger();
        appliquerRestrictionsRole();
    }

    // Pour tester : par défaut assistant
    public RendezVousFrame() {
        this("ASSISTANT");
    }

    private void initUI() {
        setTitle("Gestion des Rendez-vous");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== TABLE =====
        model = new DefaultTableModel(
                new Object[]{"ID", "IdPatient", "DateHeure", "Motif", "Statut"},
                0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) remplirFormulaireDepuisTable();
        });

        // ===== FORMULAIRE =====
        JPanel form = new JPanel(new GridLayout(2, 4, 5, 5));

        form.add(new JLabel("Patient :"));
        comboPatient = new JComboBox<>();
        form.add(comboPatient);

        form.add(new JLabel("DateHeure (yyyy-MM-dd HH:mm) :"));
        txtDateHeure = new JTextField();
        form.add(txtDateHeure);

        form.add(new JLabel("Motif :"));
        txtMotif = new JTextField();
        form.add(txtMotif);

        form.add(new JLabel("Statut :"));
        comboStatut = new JComboBox<>(new String[]{"PREVU", "ANNULE", "TERMINE"});
        form.add(comboStatut);

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

    // Charger les patients dans le ComboBox
    private void chargerPatients() {
        comboPatient.removeAllItems();
        List<Patient> patients = patientService.listerPatients();
        for (Patient p : patients) {
            comboPatient.addItem(p);
        }
    }

    // Charger les rendez-vous dans la table
    private void charger() {
        model.setRowCount(0);
        List<RendezVous> liste = service.lister();
        for (RendezVous rdv : liste) {
            model.addRow(new Object[]{
                    rdv.getIdRendezVous(),
                    rdv.getIdPatient(),
                    rdv.getDateHeure() != null ? rdv.getDateHeure().format(formatter) : "",
                    rdv.getMotif(),
                    rdv.getStatut()
            });
        }
    }

    private void remplirFormulaireDepuisTable() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int idPatient = (int) model.getValueAt(row, 1);
            String dateHeure = String.valueOf(model.getValueAt(row, 2));
            String motif = String.valueOf(model.getValueAt(row, 3));
            String statut = String.valueOf(model.getValueAt(row, 4));

            selectionnerPatientDansCombo(idPatient);
            txtDateHeure.setText(dateHeure);
            txtMotif.setText(motif);
            comboStatut.setSelectedItem(statut);
        }
    }

    private void selectionnerPatientDansCombo(int idPatient) {
        for (int i = 0; i < comboPatient.getItemCount(); i++) {
            Patient p = comboPatient.getItemAt(i);
            if (p.getIdPatient() == idPatient) {
                comboPatient.setSelectedIndex(i);
                break;
            }
        }
    }

    private void ajouter() {
        try {
            Patient p = (Patient) comboPatient.getSelectedItem();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Sélectionne un patient");
                return;
            }

            int idPatient = p.getIdPatient();
            LocalDateTime dt = LocalDateTime.parse(txtDateHeure.getText().trim(), formatter);
            String motif = txtMotif.getText().trim();
            String statut = comboStatut.getSelectedItem().toString();

            RendezVous rdv = new RendezVous(idPatient, dt, motif, statut);
            boolean ok = service.ajouter(rdv);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Rendez-vous ajouté");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur ajout RDV");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Données invalides : " + ex.getMessage());
        }
    }

    private void modifier() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionne un rendez-vous");
            return;
        }
        try {
            int idRdv = (int) model.getValueAt(row, 0);

            Patient p = (Patient) comboPatient.getSelectedItem();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Sélectionne un patient");
                return;
            }

            int idPatient = p.getIdPatient();
            LocalDateTime dt = LocalDateTime.parse(txtDateHeure.getText().trim(), formatter);
            String motif = txtMotif.getText().trim();
            String statut = comboStatut.getSelectedItem().toString();

            RendezVous rdv = new RendezVous(idPatient, dt, motif, statut);
            rdv.setIdRendezVous(idRdv);

            boolean ok = service.modifier(rdv);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Rendez-vous modifié");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur modification RDV");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Données invalides : " + ex.getMessage());
        }
    }

    private void supprimer() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionne un rendez-vous");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer ce rendez-vous ?", "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = service.supprimer(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Rendez-vous supprimé");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur suppression RDV");
            }
        }
    }

    // === Lecture seule pour le médecin ===
    private void appliquerRestrictionsRole() {
        if ("MEDECIN".equalsIgnoreCase(role)) {
            // boutons désactivés
            btnAjouter.setEnabled(false);
            btnModifier.setEnabled(false);
            btnSupprimer.setEnabled(false);

            // champs non éditables
            comboPatient.setEnabled(false);
            txtDateHeure.setEditable(false);
            txtMotif.setEditable(false);
            comboStatut.setEnabled(false);
        }
    }
}
