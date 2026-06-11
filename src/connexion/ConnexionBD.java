package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/cabinet_medical";
    private static final String USER = "root";
    private static final String PASSWORD = "";  // Si tu as un mot de passe MySQL, mets-le ici

    public static Connection getConnection() {
        Connection cnx = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println("Connexion a MySQL reussie !");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
        return cnx;
    }
}

