package dao;

import model.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// GestorAutors: Gestiona CRUD per a la taula 'autors'
public class GestorAutors {
    private Connection conn;

    // Constructor que rep la connexió
    public GestorAutors(Connection conn) {
        this.conn = conn;
    }

    // Crear un nou autor
    public void crearAutor(Autor autor) throws SQLException {
        String query = "INSERT INTO autors (nom, nacionalitat) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, autor.getNom());
            stmt.setString(2, autor.getNacionalitat());
            stmt.executeUpdate();
        }
    }

    // Llegir tots els autors
    public List<Autor> llegirAutors() throws SQLException {
        List<Autor> autors = new ArrayList<>();
        String query = "SELECT * FROM autors";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                autors.add(new Autor(rs.getInt("id"), rs.getString("nom"), rs.getString("nacionalitat")));
            }
        }
        return autors;
    }

    // Actualitzar un autor
    public void actualitzarAutor(Autor autor) throws SQLException {
        String query = "UPDATE autors SET nom = ?, nacionalitat = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, autor.getNom());
            stmt.setString(2, autor.getNacionalitat());
            stmt.setInt(3, autor.getId());
            stmt.executeUpdate();
        }
    }

    // Eliminar un autor
    public void eliminarAutor(int id) throws SQLException {
        String query = "DELETE FROM autors WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
