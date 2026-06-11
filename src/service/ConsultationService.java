package service;

import dao.ConsultationDAO;
import model.Consultation;

import java.util.List;

public class ConsultationService {

    private final ConsultationDAO dao;

    public ConsultationService() {
        this.dao = new ConsultationDAO();
    }

    public List<Consultation> lister() {
        return dao.findAll();
    }

    public boolean ajouter(Consultation c) {
        if (c.getIdPatient() <= 0 || c.getIdRendezVous() <= 0 || c.getDateConsultation() == null) {
            return false;
        }
        return dao.insert(c);
    }

    public boolean modifier(Consultation c) {
        if (c.getIdConsultation() <= 0) return false;
        return dao.update(c);
    }

    public boolean supprimer(int id) {
        return dao.delete(id);
    }
}
