package model;

import java.time.LocalDate;

public class Facture {

    private int idFacture;
    private int idRendezVous;
    private LocalDate dateFacture;
    private double montant;
    private String statut;      // PAYE / NON_PAYE
    private String modePaiement; // ESPECES / CARTE / CHEQUE / etc.

    public Facture() {
    }

    public Facture(int idRendezVous, LocalDate dateFacture, double montant,
                   String statut, String modePaiement) {
        this.idRendezVous = idRendezVous;
        this.dateFacture = dateFacture;
        this.montant = montant;
        this.statut = statut;
        this.modePaiement = modePaiement;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public int getIdRendezVous() {
        return idRendezVous;
    }

    public void setIdRendezVous(int idRendezVous) {
        this.idRendezVous = idRendezVous;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }
}
