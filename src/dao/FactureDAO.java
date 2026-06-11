package dao;

import connexion.ConnexionBD;
import model.Facture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureDAO {

    public List<Facture> findAll() {
        List<Facture> liste = new ArrayList<>();
        String sql = "SELECT * FROM Facture ORDER BY dateFacture DESC";

        try (Connection cnx = ConnexionBD.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Facture f = new Facture();
                f.setIdFacture(rs.getInt("idFacture"));
                f.setIdRendezVous(rs.getInt("idRendezVous"));
                Date d = rs.getDate("dateFacture");
                if (d != null) {
                    f.setDateFacture(d.toLocalDate());
                }
                f.setMontant(rs.getDouble("montant"));
                f.setStatut(rs.getString("statut"));
                f.setModePaiement(rs.getString("modePaiement"));
                liste.add(f);
            }

        } catch (SQLException e) {
            System.out.println("Erreur findAll Facture : " + e.getMessage());
        }
        return liste;
    }

    public boolean insert(Facture f) {
        String sql = "INSERT INTO Facture(idRendezVous, dateFacture, montant, statut, modePaiement) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, f.getIdRendezVous());
            ps.setDate(2, Date.valueOf(f.getDateFacture()));
            ps.setDouble(3, f.getMontant());
            ps.setString(4, f.getStatut());
            ps.setString(5, f.getModePaiement());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur insert Facture : " + e.getMessage());
            return false;
        }
    }

    public boolean update(Facture f) {
        String sql = "UPDATE Facture SET idRendezVous = ?, dateFacture = ?, "
                   + "montant = ?, statut = ?, modePaiement = ? "
                   + "WHERE idFacture = ?";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, f.getIdRendezVous());
            ps.setDate(2, Date.valueOf(f.getDateFacture()));
            ps.setDouble(3, f.getMontant());
            ps.setString(4, f.getStatut());
            ps.setString(5, f.getModePaiement());
            ps.setInt(6, f.getIdFacture());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur update Facture : " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idFacture) {
        String sql = "DELETE FROM Facture WHERE idFacture = ?";
        try (Connection cnx = ConnexionBD.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, idFacture);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur delete Facture : " + e.getMessage());
            return false;
        }
    }
}
