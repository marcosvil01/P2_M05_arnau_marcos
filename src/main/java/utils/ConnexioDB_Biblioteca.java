package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.nio.file.Files;
import java.nio.file.Path;

// Classe ConnexioDB per gestionar la connexió amb la base de dades
public class ConnexioDB_Biblioteca {

    // Paràmetres de connexió
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "biblioteca_db";
    private static final String USUARI = "root";
    private static final String CONTRASENYA = "";
    private static final String URI_SQL_SCRIPT = "src/main/resources/SQL/estructura.sql";

    // Mètode per establir la connexió
    public static Connection obtenirConnexio() throws SQLException, IOException {
        // Crear la base de dades si no existeix
        executarCreacioBaseDeDades();

        // Connectar-se a la base de dades
        String fullURL = URL + DB_NAME;
        Connection conn = DriverManager.getConnection(fullURL, USUARI, CONTRASENYA);
        return conn; // Retorna la connexió
    }

    // Crear la base de dades si no existeix
    public static void executarCreacioBaseDeDades() throws SQLException, IOException {
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
