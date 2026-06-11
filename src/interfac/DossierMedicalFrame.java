package interfac;

import model.DossierMedical;
import service.DossierMedicalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DossierMedicalFrame extends JFrame {

    private final DossierMedicalService service = new DossierMedicalService();

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtIdPatient;
    private JTextField txtAntecedents;
    private JTextField txtAllergies;
    private JTextField txtTraitements;
    private JTextField txtObservations;

    private JButton btnAjouter, btnModifier, btnSupprimer, btnFermer;

    public DossierMedicalFrame() {
        initUI();
        charger();
    }

    private void initUI() {
        setTitle("Gestion des Dossiers Médicaux");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ==== TABLE ====
        model = new DefaultTableModel(
                new Object[]{"ID", "Patient", "Antécédents", "Allergies", "Traitements", "Observations"},
                0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) remplirFormulaire();
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ==== FORMULAIRE BAS ====
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        form.add(new JLabel("ID Patient :"));
        txtIdPatient = new JTextField();
        form.add(txtIdPatient);

        form.add(new JLabel("Antécédents :"));
        txtAntecedents = new JTextField();
        form.add(txtAntecedents);

        form.add(new JLabel("Allergies :"));
        txtAllergies = new JTextField();
        form.add(txtAllergies);

        form.add(new JLabel("Traitements :"));
        txtTraitements = new JTextField();
        form.add(txtTraitements);

        form.add(new JLabel("Observations :"));
        txtObservations = new JTextField();
        form.add(txtObservations);

        add(form, BorderLayout.SOUTH);

        // ==== BOUTONS ====
        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.Y_AXIS));

        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnFermer = new JButton("Fermer");

        panelBtn.add(btnAjouter);
        panelBtn.add(Box.createVerticalStrut(10));
        panelBtn.add(btnModifier);
        panelBtn.add(Box.createVerticalStrut(10));
        panelBtn.add(btnSupprimer);
        panelBtn.add(Box.createVerticalStrut(10));
        panelBtn.add(btnFermer);

        add(panelBtn, BorderLayout.EAST);

        // Actions
        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnFermer.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void charger() {
        model.setRowCount(0);
        List<DossierMedical> liste = service.lister();
        for (DossierMedical d : liste) {
            model.addRow(new Object[]{
                    d.getIdDossier(),
                    d.getIdPatient(),
                    d.getAntecedents(),
                    d.getAllergies(),
                    d.getTraitements(),
                    d.getObservations()
            });
        }
    }

    private void remplirFormulaire() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtIdPatient.setText(String.valueOf(model.getValueAt(row, 1)));
            txtAntecedents.setText(String.valueOf(model.getValueAt(row, 2)));
            txtAllergies.setText(String.valueOf(model.getValueAt(row, 3)));
            txtTraitements.setText(String.valueOf(model.getValueAt(row, 4)));
            txtObservations.setText(String.valueOf(model.getValueAt(row, 5)));
        }
    }

    private void ajouter() {
        try {
            int idPatient = Integer.parseInt(txtIdPatient.getText());

            DossierMedical d = new DossierMedical(
                    idPatient,
                    txtAntecedents.getText(),
                    txtAllergies.getText(),
                    txtTraitements.getText(),
                    txtObservations.getText()
            );

            if (service.ajouter(d)) {
                JOptionPane.showMessageDialog(this, "Dossier médical ajouté");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur ajout dossier");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void modifier() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int idDossier = (int) model.getValueAt(row, 0);

        DossierMedical d = new DossierMedical(
                Integer.parseInt(txtIdPatient.getText()),
                txtAntecedents.getText(),
                txtAllergies.getText(),
                txtTraitements.getText(),
                txtObservations.getText()
        );
        d.setIdDossier(idDossier);

        if (service.modifier(d)) {
            JOptionPane.showMessageDialog(this, "Dossier médical modifié");
            charger();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur modification");
        }
    }

    private void supprimer() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int id = (int) model.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer ce dossier ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.supprimer(id)) {
                JOptionPane.showMessageDialog(this, "Dossier supprimé");
                charger();
            }
        }
    }
}
