package dao;

import model.Producte;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Component JDBC per a Productes
public class GestorProductes implements Serializable {
    private transient Connection conn; // Connexió
    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    // Constructor per inicialitzar la connexió
    public GestorProductes(Connection conn) throws SQLException {
        this.conn = conn;
        crearTaulaProductes(); // Crear la taula si no existeix
    }

    // Crear la taula productes
    public void crearTaulaProductes() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS productes (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nom VARCHAR(100) NOT NULL, " +
                "preu DOUBLE NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }
    }

    // Crear un nou producte
    public void crearProducte(Producte producte) throws SQLException {
        String query = "INSERT INTO productes (nom, preu) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producte.getNom());
            stmt.setDouble(2, producte.getPreu());
            stmt.executeUpdate();

            // Obtenir l'ID generat
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int oldId = producte.getId();
                    producte.setId(rs.getInt(1));
                    support.firePropertyChange("id", oldId, producte.getId());
                }
            }
        }
    }

    // Llegir tots els productes
    public List<Producte> llegirProductes() throws SQLException {
        List<Producte> productes = new ArrayList<>();
        String query = "SELECT * FROM productes";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                productes.add(new Producte(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("preu")
                ));
            }
        }
        return productes;
    }

    // Actualitzar un producte
    public void actualitzarProducte(Producte producte) throws SQLException {
        String query = "UPDATE productes SET nom = ?, preu = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, producte.getNom());
            stmt.setDouble(2, producte.getPreu());
            stmt.setInt(3, producte.getId());
            stmt.executeUpdate();
            support.firePropertyChange("producteActualitzat", null, producte);
        }
    }

    // Eliminar un producte
    public void eliminarProducte(int id) throws SQLException {
        String query = "DELETE FROM productes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            support.firePropertyChange("producteEliminat", id, null);
        }
    }

    // Afegir i eliminar listeners per notificacions
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // Tancar connexió
    public void tancarConnexio() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}