package dao;

import connexion.ConnexionBD;
import model.DossierMedical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DossierMedicalDAO {

    public List<DossierMedical> findAll() {
        List<DossierMedical> liste = new ArrayList<>();
        String sql = "SELECT * FROM DossierMedical";

        try (Connection cnx = ConnexionBD.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                DossierMedical d = new DossierMedical();

                d.setIdDossier(rs.getInt("idDossier"));
                d.setIdPatient(rs.getInt("idPatient"));
                d.setAntecedents(rs.getString("antecedents"));
                d.setAllergies(rs.getString("allergies"));
                d.setTraitements(rs.getString("traitements"));
                d.setObservations(rs.getString("observations"));

                liste.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Erreur findAll dossier : " + e.getMessage());
        }
        return liste;
    }

    public boolean insert(DossierMedical d) {
        String sql = "INSERT INTO DossierMedical(idPatient, antecedents, allergies, traitements, observations) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, d.getIdPatient());
            ps.setString(2, d.getAntecedents());
            ps.setString(3, d.getAllergies());
            ps.setString(4, d.getTraitements());
            ps.setString(5, d.getObservations());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur insert dossier : " + e.getMessage());
            return false;
        }
    }

    public boolean update(DossierMedical d) {
        String sql = "UPDATE DossierMedical SET antecedents=?, allergies=?, traitements=?, observations=? "
                   + "WHERE idDossier=?";

        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, d.getAntecedents());
            ps.setString(2, d.getAllergies());
            ps.setString(3, d.getTraitements());
            ps.setString(4, d.getObservations());
            ps.setInt(5, d.getIdDossier());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur update dossier : " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idDossier) {
        String sql = "DELETE FROM DossierMedical WHERE idDossier=?";

        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, idDossier);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur delete dossier : " + e.getMessage());
            return false;
        }
    }
}


