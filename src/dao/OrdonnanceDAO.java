package dao;

import connexion.ConnexionBD;
import model.Ordonnance;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceDAO {

    public List<Ordonnance> findAll() {
        List<Ordonnance> liste = new ArrayList<>();
        String sql = "SELECT * FROM Ordonnance ORDER BY dateOrdonnance DESC";

        try (Connection cnx = ConnexionBD.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Ordonnance o = new Ordonnance();
                o.setIdOrdonnance(rs.getInt("idOrdonnance"));
                o.setIdPatient(rs.getInt("idPatient"));
                Date d = rs.getDate("dateOrdonnance");
                if (d != null) {
                    o.setDateOrdonnance(d.toLocalDate());
                }
                o.setCommentaire(rs.getString("commentaire"));

                liste.add(o);
            }

        } catch (SQLException e) {
            System.out.println("Erreur findAll ordonnance : " + e.getMessage());
        }
        return liste;
    }

    public boolean insert(Ordonnance o) {
        String sql = "INSERT INTO Ordonnance(idPatient, dateOrdonnance, commentaire) VALUES (?, ?, ?)";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, o.getIdPatient());
            ps.setDate(2, Date.valueOf(o.getDateOrdonnance()));
            ps.setString(3, o.getCommentaire());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur insert ordonnance : " + e.getMessage());
            return false;
        }
    }

    public boolean update(Ordonnance o) {
        String sql = "UPDATE Ordonnance SET idPatient=?, dateOrdonnance=?, commentaire=? WHERE idOrdonnance=?";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, o.getIdPatient());
            ps.setDate(2, Date.valueOf(o.getDateOrdonnance()));
            ps.setString(3, o.getCommentaire());
            ps.setInt(4, o.getIdOrdonnance());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur update ordonnance : " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Ordonnance WHERE idOrdonnance=?";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur delete ordonnance : " + e.getMessage());
            return false;
        }
    }
}

