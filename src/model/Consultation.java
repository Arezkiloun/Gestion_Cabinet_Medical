
package model;

import java.time.LocalDate;

public class Consultation {

    private int idConsultation;
    private int idRendezVous;
    private int idPatient;
    private LocalDate dateConsultation;
    private String diagnostic;
    private String notes;

    public Consultation() {}

    public Consultation(int idRendezVous, int idPatient, LocalDate dateConsultation,
                        String diagnostic, String notes) {
        this.idRendezVous = idRendezVous;
        this.idPatient = idPatient;
        this.dateConsultation = dateConsultation;
        this.diagnostic = diagnostic;
        this.notes = notes;
    }

    public int getIdConsultation() {
        return idConsultation;
    }

    public void setIdConsultation(int idConsultation) {
        this.idConsultation = idConsultation;
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

    public LocalDate getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(LocalDate dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
