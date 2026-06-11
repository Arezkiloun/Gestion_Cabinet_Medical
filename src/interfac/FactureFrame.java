package interfac;

import model.Facture;
import service.FactureService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FactureFrame extends JFrame {

    private final FactureService service = new FactureService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String role; // "ASSISTANT" ou "MEDECIN"

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtIdRendezVous;
    private JTextField txtDateFacture;
    private JTextField txtMontant;
    private JComboBox<String> comboStatut;        // NON_PAYE / PAYE
    private JComboBox<String> comboModePaiement;  // ESPECES / CARTE / CHEQUE / VIREMENT

    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JButton btnFermer;

    // === Constructeur avec rôle ===
    public FactureFrame(String role) {
        this.role = role;
        initUI();
        charger();
        appliquerRestrictionsRole();
    }

    // === Pour tester : par défaut assistant ===
    public FactureFrame() {
        this("ASSISTANT");
    }

    private void initUI() {
        setTitle("Gestion des Factures");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== TABLE =====
        model = new DefaultTableModel(
                new Object[]{"ID", "IdRendezVous", "Date", "Montant", "Statut", "Mode paiement"},
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
        JPanel form = new JPanel(new GridLayout(3, 4, 5, 5));

        form.add(new JLabel("Id Rendez-vous :"));
        txtIdRendezVous = new JTextField();
        form.add(txtIdRendezVous);

        form.add(new JLabel("Date (yyyy-MM-dd) :"));
        txtDateFacture = new JTextField();
        form.add(txtDateFacture);

        form.add(new JLabel("Montant :"));
        txtMontant = new JTextField();
        form.add(txtMontant);

        form.add(new JLabel("Statut :"));
        comboStatut = new JComboBox<>(new String[]{"NON_PAYE", "PAYE"});
        form.add(comboStatut);

        form.add(new JLabel("Mode paiement :"));
        comboModePaiement = new JComboBox<>(new String[]{"ESPECES", "CARTE", "CHEQUE", "VIREMENT"});
        form.add(comboModePaiement);

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
        List<Facture> liste = service.lister();
        for (Facture f : liste) {
            model.addRow(new Object[]{
                    f.getIdFacture(),
                    f.getIdRendezVous(),
                    f.getDateFacture() != null ? f.getDateFacture().format(formatter) : "",
                    f.getMontant(),
                    f.getStatut(),
                    f.getModePaiement()
            });
        }
    }

    private void remplirFormulaireDepuisTable() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtIdRendezVous.setText(String.valueOf(model.getValueAt(row, 1)));
            txtDateFacture.setText(String.valueOf(model.getValueAt(row, 2)));
            txtMontant.setText(String.valueOf(model.getValueAt(row, 3)));
            comboStatut.setSelectedItem(String.valueOf(model.getValueAt(row, 4)));
            comboModePaiement.setSelectedItem(String.valueOf(model.getValueAt(row, 5)));
        }
    }

    private void ajouter() {
        try {
            int idRdv = Integer.parseInt(txtIdRendezVous.getText().trim());
            LocalDate date = LocalDate.parse(txtDateFacture.getText().trim(), formatter);
            double montant = Double.parseDouble(txtMontant.getText().trim());
            String statut = comboStatut.getSelectedItem().toString();
            String mode = comboModePaiement.getSelectedItem().toString();

            Facture f = new Facture(idRdv, date, montant, statut, mode);
            boolean ok = service.ajouter(f);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Facture ajoutée");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur ajout facture");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Données invalides : " + ex.getMessage());
        }
    }

    private void modifier() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionne une facture");
            return;
        }

        try {
            int idFacture = (int) model.getValueAt(row, 0);
            int idRdv = Integer.parseInt(txtIdRendezVous.getText().trim());
            LocalDate date = LocalDate.parse(txtDateFacture.getText().trim(), formatter);
            double montant = Double.parseDouble(txtMontant.getText().trim());
            String statut = comboStatut.getSelectedItem().toString();
            String mode = comboModePaiement.getSelectedItem().toString();

            Facture f = new Facture(idRdv, date, montant, statut, mode);
            f.setIdFacture(idFacture);

            boolean ok = service.modifier(f);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Facture modifiée");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur modification facture");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Données invalides : " + ex.getMessage());
        }
    }

    private void supprimer() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionne une facture");
            return;
        }

        int idFacture = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer cette facture ?", "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = service.supprimer(idFacture);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Facture supprimée");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur suppression facture");
            }
        }
    }

    // === Lecture seule pour le médecin ===
    private void appliquerRestrictionsRole() {
        if ("MEDECIN".equalsIgnoreCase(role)) {
            // désactiver les boutons
            btnAjouter.setEnabled(false);
            btnModifier.setEnabled(false);
            btnSupprimer.setEnabled(false);

            // empêcher modification des champs
            txtIdRendezVous.setEditable(false);
            txtDateFacture.setEditable(false);
            txtMontant.setEditable(false);
            comboStatut.setEnabled(false);
            comboModePaiement.setEnabled(false);
        }
    }
}
