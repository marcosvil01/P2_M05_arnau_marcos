package dao;

import model.Llibre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// GestorLlibres: Gestiona CRUD per a la taula 'llibres'
public class GestorLlibres {
    private Connection conn;

    // Constructor que rep la connexió
    public GestorLlibres(Connection conn) {
        this.conn = conn;
    }

    // Crear un nou llibre
    public void crearLlibre(Llibre llibre) throws SQLException {
        String query = "INSERT INTO llibres (titol, any_publicacio) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, llibre.getTitol());
            stmt.setInt(2, llibre.getAnyPublicacio());
            stmt.executeUpdate();
        }
    }

    // Llegir tots els llibres
    public List<Llibre> llegirLlibres() throws SQLException {
        List<Llibre> llibres = new ArrayList<>();
        String query = "SELECT * FROM llibres";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                llibres.add(new Llibre(rs.getInt("id"), rs.getString("titol"), rs.getInt("any_publicacio")));
            }
        }
        return llibres;
    }

    // Actualitzar un llibre
    public void actualitzarLlibre(Llibre llibre) throws SQLException {
        String query = "UPDATE llibres SET titol = ?, any_publicacio = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, llibre.getTitol());
            stmt.setInt(2, llibre.getAnyPublicacio());
            stmt.setInt(3, llibre.getId());
            stmt.executeUpdate();
        }
    }

    // Eliminar un llibre
    public void eliminarLlibre(int id) throws SQLException {
        String query = "DELETE FROM llibres WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Obtenir l'ID d'un llibre pel seu nom
    public int obtenirIdPerTitol(String titol) throws SQLException {
        String query = "SELECT id FROM llibres WHERE titol = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, titol);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // Si es troba el llibre
                    return rs.getInt("id");
                }
            }
        }
        return -1; // Retorna -1 si no existeix el llibre
    }

}
