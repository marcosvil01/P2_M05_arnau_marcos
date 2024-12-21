package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


// Gestió de la connexió per la BBDD de productes
public class ConnexioDB_Productes {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "productes_db";
    private static final String USUARI = "root";
    private static final String CONTRASENYA = "";

    // Obtenir connexió
    public static Connection obtenirConnexio() throws SQLException {
        // Comprovar i crear la base de dades si no existeix
        crearBaseDeDades();

        // Connexió directa a la base de dades
        String fullURL = URL + DB_NAME;
        return DriverManager.getConnection(fullURL, USUARI, CONTRASENYA);
    }

    // Crear la base de dades si no existeix
    private static void crearBaseDeDades() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USUARI, CONTRASENYA);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("Base de dades '" + DB_NAME + "' verificada o creada amb èxit.");
        }
    }
}
