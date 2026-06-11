package interfac;

import model.Ordonnance;
import service.OrdonnanceService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrdonnanceFrame extends JFrame {

    private final OrdonnanceService service = new OrdonnanceService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtIdPatient;
    private JTextField txtDateOrd;
    private JTextField txtCommentaire;

    private JButton btnAjouter, btnModifier, btnSupprimer, btnFermer;

    public OrdonnanceFrame() {
        initUI();
        charger();
    }

    private void initUI() {
        setTitle("Gestion des Ordonnances");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== TABLE =====
        model = new DefaultTableModel(
                new Object[]{"ID", "Patient", "Date", "Commentaire"},
                0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) remplirFormulaire();
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== FORMULAIRE BAS =====
        JPanel form = new JPanel(new GridLayout(2, 3, 5, 5));

        form.add(new JLabel("ID Patient :"));
        txtIdPatient = new JTextField();
        form.add(txtIdPatient);

        form.add(new JLabel("Date (yyyy-MM-dd) :"));
        txtDateOrd = new JTextField();
        form.add(txtDateOrd);

        form.add(new JLabel("Commentaire :"));
        txtCommentaire = new JTextField();
        form.add(txtCommentaire);

        add(form, BorderLayout.SOUTH);

        // ===== BOUTONS =====
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

        // ===== ACTIONS =====
        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnFermer.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void charger() {
        model.setRowCount(0);
        List<Ordonnance> liste = service.lister();
        for (Ordonnance o : liste) {
            model.addRow(new Object[]{
                    o.getIdOrdonnance(),
                    o.getIdPatient(),
                    o.getDateOrdonnance(),
                    o.getCommentaire()
            });
        }
    }

    private void remplirFormulaire() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtIdPatient.setText(String.valueOf(model.getValueAt(row, 1)));
            txtDateOrd.setText(String.valueOf(model.getValueAt(row, 2)));
            txtCommentaire.setText(String.valueOf(model.getValueAt(row, 3)));
        }
    }

    private void ajouter() {
        try {
            int idPatient = Integer.parseInt(txtIdPatient.getText());
            LocalDate date = LocalDate.parse(txtDateOrd.getText(), formatter);
            String commentaire = txtCommentaire.getText();

            Ordonnance o = new Ordonnance(idPatient, date, commentaire);

            if (service.ajouter(o)) {
                JOptionPane.showMessageDialog(this, "Ordonnance ajoutée");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur ajout ordonnance");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Données incorrectes : " + ex.getMessage());
        }
    }

    private void modifier() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        try {
            int id = (int) model.getValueAt(row, 0);
            int idPatient = Integer.parseInt(txtIdPatient.getText());
            LocalDate date = LocalDate.parse(txtDateOrd.getText(), formatter);
            String commentaire = txtCommentaire.getText();

            Ordonnance o = new Ordonnance(idPatient, date, commentaire);
            o.setIdOrdonnance(id);

            if (service.modifier(o)) {
                JOptionPane.showMessageDialog(this, "Ordonnance modifiée");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur modification ordonnance");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Données incorrectes : " + ex.getMessage());
        }
    }

    private void supprimer() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer cette ordonnance ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.supprimer(id)) {
                JOptionPane.showMessageDialog(this, "Ordonnance supprimée");
                charger();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur suppression ordonnance");
            }
        }
    }
}


