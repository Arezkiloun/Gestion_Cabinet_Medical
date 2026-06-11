import dao.PatientDAO;
import model.Patient;
import java.util.List;

public class TestPatient {
    public static void main(String[] args) {

        PatientDAO dao = new PatientDAO();

        // 1) Test ajout patient
        Patient p = new Patient("Ali", "Karim", "2000-05-10",
                                "Alger", "0555 00 00 01", "ali.karim@example.com");

        if (dao.ajouterPatient(p)) {
            System.out.println("Patient ajout avec succes !");
        } else {
            System.out.println("Échec ajout patient.");
        }

        // 2) Test affichage de tous les patients
        List<Patient> patients = dao.listerPatients();
        System.out.println("Liste des patients :");
        for (Patient pat : patients) {
            System.out.println(pat);
        }
    }
}
