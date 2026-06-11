
package interfac;

import model.Consultation;
import service.ConsultationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultationFrame extends JFrame {

    private final ConsultationService service = new ConsultationService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtIdRendezVous;
    private JTextField txtIdPatient;
    private JTextField txtDate;
    private JTextField txtDiagnostic;
    private JTextField txtNotes;

    private JButton btnAjouter, btnModifier, btnSupprimer, btnFermer;

    public ConsultationFrame() {
        initUI();
        charger();
    }

    private void initUI() {
        setTitle("Gestion des Consultations");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // TABLE
        model = new DefaultTableModel(
                new Object[]{"ID", "RDV", "Patient", "Date", "Diagnostic", "Notes"},
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

        // FORMULAIRE
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        form.add(new JLabel("ID Rendez-vous :"));
        txtIdRendezVous = new JTextField();
        form.add(txtIdRendezVous);

        form.add(new JLabel("ID Patient :"));
        txtIdPatient = new JTextField();
        form.add(txtIdPatient);

        form.add(new JLabel("Date (yyyy-MM-dd) :"));
        txtDate = new JTextField();
        form.add(txtDate);

        form.add(new JLabel("Diagnostic :"));
        txtDiagnostic = new JTextField();
        form.add(txtDiagnostic);

        form.add(new JLabel("Notes :"));
        txtNotes = new JTextField();
        form.add(txtNotes);

        add(form, BorderLayout.SOUTH);

        // BOUTONS
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

        // ACTIONS
        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnFermer.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void charger() {
        model.setRowCount(0);
        List<Consultation> liste = service.lister();
        for (Consultation c : liste) {
            model.addRow(new Object[]{
                    c.getIdConsultation(),
                    c.getIdRendezVous(),
                    c.getIdPatient(),
                    c.getDateConsultation(),
                    c.getDiagnostic(),
                    c.getNotes()
            });
        }
    }

    private void remplirFormulaire() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        txtIdRendezVous.setText(String.valueOf(model.getValueAt(row, 1)));
        txtIdPatient.setText(String.valueOf(model.getValueAt(row, 2)));
        txtDate.setText(String.valueOf(model.getValueAt(row, 3)));
        txtDiagnostic.setText(String.valueOf(model.getValueAt(row, 4)));
        txtNotes.setText(String.valueOf(model.getValueAt(row, 5)));
    }

    private void ajouter() {
        try {
            int idRdv = Integer.parseInt(txtIdRendezVous.getText());
            int idPatient = Integer.parseInt(txtIdPatient.getText());
            LocalDate date = LocalDate.parse(txtDate.getText(), formatter);
            String diag = txtDiagnostic.getText();
            String notes = txtNotes.getText();

            Consultation c = new Consultation(idRdv, idPatient, date, diag, notes);

            if (service.ajouter(c)) {
                JOptionPane.showMessageDialog(this, "Consultation ajoutée");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur ajout consultation");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void modifier() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        try {
            int id = (int) model.getValueAt(row, 0);
            int idRdv = Integer.parseInt(txtIdRendezVous.getText());
            int idPatient = Integer.parseInt(txtIdPatient.getText());
            LocalDate date = LocalDate.parse(txtDate.getText(), formatter);
            String diag = txtDiagnostic.getText();
            String notes = txtNotes.getText();

            Consultation c = new Consultation(idRdv, idPatient, date, diag, notes);
            c.setIdConsultation(id);

            if (service.modifier(c)) {
                JOptionPane.showMessageDialog(this, "Consultation modifiée");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur modification consultation");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void supprimer() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.supprimer(id)) {
                JOptionPane.showMessageDialog(this, "Consultation supprimée");
                charger();
            }
        }
    }
}
