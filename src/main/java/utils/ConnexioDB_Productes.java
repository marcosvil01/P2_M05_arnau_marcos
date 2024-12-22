package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;


// Gestió de la connexió per la BBDD de productes
public class ConnexioDB_Productes {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "productes_db";
    private static final String USUARI = "root";
    private static final String CONTRASENYA = "";
    private static final String URI_SQL_SCRIPT = "src/main/resources/SQL/Productes.sql";

    // Mètode per establir la connexió
    public static Connection obtenirConnexio() throws SQLException, IOException {
        // Check if the database exists
        if (!baseDeDadesExisteix(DB_NAME)) {
            // Create the database if it doesn't exist
            crearBaseDeDades();
        }

        // Connect to the database
        String fullURL = URL + DB_NAME;
        Connection conn = DriverManager.getConnection(fullURL, USUARI, CONTRASENYA);
        return conn; // Return the connection
    }

    // Method to check if the database exists
    public static boolean baseDeDadesExisteix(String nomBaseDades) throws SQLException {
        String url = URL; // Connect without specifying a database
        try (Connection conn = DriverManager.getConnection(url, USUARI, CONTRASENYA);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?")) {
            stmt.setString(1, nomBaseDades);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if the database exists
            }
        }
    }

    // Crear la base de dades si no existeix
    public static void crearBaseDeDades() throws SQLException, IOException {
        System.out.println("\nExecutant creació de la base de dades...");

        // Connexió temporal per crear la base de dades
        try (Connection conn = DriverManager.getConnection(URL, USUARI, CONTRASENYA);
             Statement stmt = conn.createStatement()) {
            // Crear la base de dades si no existeix
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("Base de dades '" + DB_NAME + "' creada/verificada amb èxit.");
        }

        // Connexió a la base de dades específica
        try (Connection conn = DriverManager.getConnection(URL + DB_NAME, USUARI, CONTRASENYA)) {
            // Llegir el fitxer SQL
            String sql = new String(Files.readAllBytes(Path.of(URI_SQL_SCRIPT)));

            // Dividir les sentències SQL pel punt i coma
            String[] sentencies = sql.split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String sentencia : sentencies) {
                    if (!sentencia.trim().isEmpty()) { // Ignora línies en blanc
                        stmt.execute(sentencia.trim()); // Executa cada sentència
                    }
                }
            }
            System.out.println("Taules creades o actualitzades amb èxit.");
        }
    }
}
