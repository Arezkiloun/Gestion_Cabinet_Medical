package service;

import dao.DossierMedicalDAO;
import model.DossierMedical;

import java.util.List;

public class DossierMedicalService {

    private final DossierMedicalDAO dao;

    public DossierMedicalService() {
        this.dao = new DossierMedicalDAO();
    }

    public List<DossierMedical> lister() {
        return dao.findAll();
    }

    public boolean ajouter(DossierMedical d) {
        if (d.getIdPatient() <= 0) return false;
        return dao.insert(d);
    }

    public boolean modifier(DossierMedical d) {
        if (d.getIdDossier() <= 0) return false;
        return dao.update(d);
    }

    public boolean supprimer(int id) {
        return dao.delete(id);
    }
}
