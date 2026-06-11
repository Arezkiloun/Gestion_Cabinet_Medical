package model;

import java.time.LocalDate;

public class Ordonnance {

    private int idOrdonnance;
    private int idPatient;
    private LocalDate dateOrdonnance;
    private String commentaire;

    public Ordonnance() {}

    public Ordonnance(int idPatient, LocalDate dateOrdonnance, String commentaire) {
        this.idPatient = idPatient;
        this.dateOrdonnance = dateOrdonnance;
        this.commentaire = commentaire;
    }

    public int getIdOrdonnance() {
        return idOrdonnance;
    }

    public void setIdOrdonnance(int idOrdonnance) {
        this.idOrdonnance = idOrdonnance;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public LocalDate getDateOrdonnance() {
        return dateOrdonnance;
    }

    public void setDateOrdonnance(LocalDate dateOrdonnance) {
        this.dateOrdonnance = dateOrdonnance;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
