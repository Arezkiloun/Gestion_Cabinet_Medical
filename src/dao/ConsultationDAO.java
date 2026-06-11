
package dao;

import connexion.ConnexionBD;
import model.Consultation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDAO {

    public List<Consultation> findAll() {
        List<Consultation> liste = new ArrayList<>();
        String sql = "SELECT * FROM Consultation ORDER BY dateConsultation DESC";

        try (Connection cnx = ConnexionBD.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Consultation c = new Consultation();

                c.setIdConsultation(rs.getInt("idConsultation"));
                c.setIdRendezVous(rs.getInt("idRendezVous"));
                c.setIdPatient(rs.getInt("idPatient"));
                c.setDateConsultation(rs.getDate("dateConsultation").toLocalDate());
                c.setDiagnostic(rs.getString("diagnostic"));
                c.setNotes(rs.getString("notes"));

                liste.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Erreur findAll Consultation : " + e.getMessage());
        }

        return liste;
    }

    public boolean insert(Consultation c) {
        String sql = "INSERT INTO Consultation(idRendezVous, idPatient, dateConsultation, diagnostic, notes) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, c.getIdRendezVous());
            ps.setInt(2, c.getIdPatient());
            ps.setDate(3, Date.valueOf(c.getDateConsultation()));
            ps.setString(4, c.getDiagnostic());
            ps.setString(5, c.getNotes());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur insert Consultation : " + e.getMessage());
            return false;
        }
    }

    public boolean update(Consultation c) {
        String sql = "UPDATE Consultation SET idRendezVous=?, idPatient=?, "
                   + "dateConsultation=?, diagnostic=?, notes=? WHERE idConsultation=?";

        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, c.getIdRendezVous());
            ps.setInt(2, c.getIdPatient());
            ps.setDate(3, Date.valueOf(c.getDateConsultation()));
            ps.setString(4, c.getDiagnostic());
            ps.setString(5, c.getNotes());
            ps.setInt(6, c.getIdConsultation());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur update Consultation : " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Consultation WHERE idConsultation=?";

        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur delete Consultation : " + e.getMessage());
            return false;
        }
    }
}
