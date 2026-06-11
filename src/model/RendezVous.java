
package model;

import java.time.LocalDateTime;

public class RendezVous {
    private int idRendezVous;
    private int idPatient;
    private LocalDateTime dateHeure;
    private String motif;
    private String statut; // PREVU / ANNULE / TERMINE

    public RendezVous() {
    }

    public RendezVous(int idPatient, LocalDateTime dateHeure, String motif, String statut) {
        this.idPatient = idPatient;
        this.dateHeure = dateHeure;
        this.motif = motif;
        this.statut = statut;
    }

    public int getIdRendezVous() {
        return idRendezVous;
    }

    public void setIdRendezVous(int idRendezVous) {
        this.idRendezVous = idRendezVous;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
