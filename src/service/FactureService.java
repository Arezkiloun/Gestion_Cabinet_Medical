package service;

import dao.FactureDAO;
import model.Facture;

import java.util.List;

public class FactureService {

    private final FactureDAO dao;

    public FactureService() {
        this.dao = new FactureDAO();
    }

    public List<Facture> lister() {
        return dao.findAll();
    }

    public boolean ajouter(Facture f) {
        if (f.getIdRendezVous() <= 0 || f.getDateFacture() == null || f.getMontant() <= 0) {
            return false;
        }
        return dao.insert(f);
    }

    public boolean modifier(Facture f) {
        if (f.getIdFacture() <= 0) return false;
        if (f.getIdRendezVous() <= 0 || f.getMontant() <= 0) return false;
        return dao.update(f);
    }

    public boolean supprimer(int idFacture) {
        if (idFacture <= 0) return false;
        return dao.delete(idFacture);
    }
}

