package service;

import dao.OrdonnanceDAO;
import model.Ordonnance;

import java.util.List;

public class OrdonnanceService {

    private final OrdonnanceDAO dao;

    public OrdonnanceService() {
        this.dao = new OrdonnanceDAO();
    }

    public List<Ordonnance> lister() {
        return dao.findAll();
    }

    public boolean ajouter(Ordonnance o) {
        if (o.getIdPatient() <= 0 || o.getDateOrdonnance() == null) {
            return false;
        }
        return dao.insert(o);
    }

    public boolean modifier(Ordonnance o) {
        if (o.getIdOrdonnance() <= 0) return false;
        return dao.update(o);
    }

    public boolean supprimer(int idOrdonnance) {
        return dao.delete(idOrdonnance);
    }
}
