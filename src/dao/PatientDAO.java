package dao;

import connexion.ConnexionBD;
import model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Ajouter un patient
    public boolean ajouterPatient(Patient p) {
        String sql = "INSERT INTO Patient(nom, prenom, dateNaissance, adresse, telephone, email) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setString(3, p.getDateNaissance());
            ps.setString(4, p.getAdresse());
            ps.setString(5, p.getTelephone());
            ps.setString(6, p.getEmail());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur ajout patient : " + e.getMessage());
            return false;
        }
    }

    // Modifier un patient
    public boolean modifierPatient(Patient p) {
        String sql = "UPDATE Patient SET nom = ?, prenom = ?, dateNaissance = ?, "
                   + "adresse = ?, telephone = ?, email = ? "
                   + "WHERE idPatient = ?";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setString(3, p.getDateNaissance());
            ps.setString(4, p.getAdresse());
            ps.setString(5, p.getTelephone());
            ps.setString(6, p.getEmail());
            ps.setInt(7, p.getIdPatient());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur modification patient : " + e.getMessage());
            return false;
        }
    }

    // Supprimer un patient
    public boolean supprimerPatient(int idPatient) {
        String sql = "DELETE FROM Patient WHERE idPatient = ?";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, idPatient);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur suppression patient : " + e.getMessage());
            return false;
        }
    }

    // Récupérer la liste de tous les patients
    public List<Patient> listerPatients() {
        List<Patient> liste = new ArrayList<>();
        String sql = "SELECT * FROM Patient";

        try (Connection cnx = ConnexionBD.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Patient p = new Patient();
                p.setIdPatient(rs.getInt("idPatient"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setDateNaissance(rs.getString("dateNaissance"));
                p.setAdresse(rs.getString("adresse"));
                p.setTelephone(rs.getString("telephone"));
                p.setEmail(rs.getString("email"));
                liste.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erreur liste patients : " + e.getMessage());
        }
        return liste;
    }
}
