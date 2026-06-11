package dao;

import connexion.ConnexionBD;
import model.RendezVous;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAO {

    // Liste de tous les rendez-vous
    public List<RendezVous> findAll() {
        List<RendezVous> liste = new ArrayList<>();
        String sql = "SELECT * FROM RendezVous ORDER BY dateHeure";

        try (Connection cnx = ConnexionBD.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                RendezVous rdv = new RendezVous();
                rdv.setIdRendezVous(rs.getInt("idRendezVous"));
                rdv.setIdPatient(rs.getInt("idPatient"));

                Timestamp ts = rs.getTimestamp("dateHeure");
                if (ts != null) {
                    rdv.setDateHeure(ts.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime());
                }

                rdv.setMotif(rs.getString("motif"));
                rdv.setStatut(rs.getString("statut"));
                liste.add(rdv);
            }

        } catch (SQLException e) {
            System.out.println("Erreur findAll RDV : " + e.getMessage());
        }
        return liste;
    }

    // Ajouter
    public boolean insert(RendezVous rdv) {
        String sql = "INSERT INTO RendezVous(idPatient, dateHeure, motif, statut) "
                   + "VALUES (?, ?, ?, ?)";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, rdv.getIdPatient());
            ps.setTimestamp(2, Timestamp.valueOf(rdv.getDateHeure()));
            ps.setString(3, rdv.getMotif());
            ps.setString(4, rdv.getStatut());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur insert RDV : " + e.getMessage());
            return false;
        }
    }

    // Modifier
    public boolean update(RendezVous rdv) {
        String sql = "UPDATE RendezVous SET idPatient = ?, dateHeure = ?, motif = ?, statut = ? "
                   + "WHERE idRendezVous = ?";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, rdv.getIdPatient());
            ps.setTimestamp(2, Timestamp.valueOf(rdv.getDateHeure()));
            ps.setString(3, rdv.getMotif());
            ps.setString(4, rdv.getStatut());
            ps.setInt(5, rdv.getIdRendezVous());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur update RDV : " + e.getMessage());
            return false;
        }
    }

    // Supprimer
    public boolean delete(int idRendezVous) {
        String sql = "DELETE FROM RendezVous WHERE idRendezVous = ?";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, idRendezVous);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur delete RDV : " + e.getMessage());
            return false;
        }
    }
}
