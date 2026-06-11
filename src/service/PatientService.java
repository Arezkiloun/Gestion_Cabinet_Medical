package service;

import dao.PatientDAO;
import model.Patient;
import java.util.List;

public class PatientService {

    private final PatientDAO dao;

    public PatientService() {
        this.dao = new PatientDAO();
    }

    public List<Patient> listerPatients() {
        return dao.listerPatients();
    }

    public boolean ajouterPatient(Patient p) {
        if (p.getNom() == null || p.getNom().trim().isEmpty()
                || p.getPrenom() == null || p.getPrenom().trim().isEmpty()) {
            return false;
        }
        return dao.ajouterPatient(p);
    }

    public boolean modifierPatient(Patient p) {
        if (p.getIdPatient() <= 0) return false;
        if (p.getNom() == null || p.getNom().trim().isEmpty()
                || p.getPrenom() == null || p.getPrenom().trim().isEmpty()) {
            return false;
        }
        return dao.modifierPatient(p);
    }

    public boolean supprimerPatient(int idPatient) {
        if (idPatient <= 0) return false;
        return dao.supprimerPatient(idPatient);
    }
}
