package service;

import dao.RendezVousDAO;
import model.RendezVous;

import java.util.List;

public class RendezVousService {

    private final RendezVousDAO dao;

    public RendezVousService() {
        this.dao = new RendezVousDAO();
    }

    public List<RendezVous> lister() {
        return dao.findAll();
    }

    public boolean ajouter(RendezVous rdv) {
        if (rdv.getIdPatient() <= 0 || rdv.getDateHeure() == null) {
            return false;
        }
        return dao.insert(rdv);
    }

    public boolean modifier(RendezVous rdv) {
        if (rdv.getIdRendezVous() <= 0) return false;
        return dao.update(rdv);
    }

    public boolean supprimer(int idRdv) {
        if (idRdv <= 0) return false;
        return dao.delete(idRdv);
    }
}
