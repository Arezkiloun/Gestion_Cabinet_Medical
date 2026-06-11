package model;

public class DossierMedical {

    private int idDossier;
    private int idPatient;
    private String antecedents;
    private String allergies;
    private String traitements;
    private String observations;

    public DossierMedical() {}

    public DossierMedical(int idPatient, String antecedents, String allergies, String traitements, String observations) {
        this.idPatient = idPatient;
        this.antecedents = antecedents;
        this.allergies = allergies;
        this.traitements = traitements;
        this.observations = observations;
    }

    public int getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(int idDossier) {
        this.idDossier = idDossier;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getAntecedents() {
        return antecedents;
    }

    public void setAntecedents(String antecedents) {
        this.antecedents = antecedents;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getTraitements() {
        return traitements;
    }

    public void setTraitements(String traitements) {
        this.traitements = traitements;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
