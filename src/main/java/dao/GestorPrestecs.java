package dao;

import model.Prestec;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// GestorPrestecs: Gestiona CRUD per a la taula 'prestecs'
public class GestorPrestecs {
    private Connection conn;

    // Constructor que rep la connexió
    public GestorPrestecs(Connection conn) {
        this.conn = conn;
    }

    public void crearPrestec(Prestec prestec) throws SQLException {
        // Comprovar si l'ID del llibre existeix
        String checkQuery = "SELECT COUNT(*) FROM llibres WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, prestec.getLlibreId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) { // Si no hi ha coincidències
                throw new SQLException("Llibre ID no existeix: " + prestec.getLlibreId());
            }
        }

        // Inserir el préstec si l'ID del llibre és vàlid
        String query = "INSERT INTO prestecs (llibre_id, usuari, data_prestec, data_devolucio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, prestec.getLlibreId());
            stmt.setString(2, prestec.getUsuari());
            stmt.setDate(3, Date.valueOf(prestec.getDataPrestec()));
            stmt.setDate(4, Date.valueOf(prestec.getDataDevolucio()));
            stmt.executeUpdate();
        }
    }


    // Llegir tots els préstecs
    public List<Prestec> llegirPrestecs() throws SQLException {
        List<Prestec> prestecs = new ArrayList<>();
        String query = "SELECT * FROM prestecs";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                prestecs.add(new Prestec(
                        rs.getInt("id"),
                        rs.getInt("llibre_id"),
                        rs.getString("usuari"),
                        rs.getDate("data_prestec").toString(),
                        rs.getDate("data_devolucio").toString()
                ));
            }
        }
        return prestecs;
    }

    // Actualitzar un préstec
    public void actualitzarPrestec(Prestec prestec) throws SQLException {
        String query = "UPDATE prestecs SET llibre_id = ?, usuari = ?, data_prestec = ?, data_devolucio = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, prestec.getLlibreId());
            stmt.setString(2, prestec.getUsuari());
            stmt.setDate(3, Date.valueOf(prestec.getDataPrestec()));
            stmt.setDate(4, Date.valueOf(prestec.getDataDevolucio()));
            stmt.setInt(5, prestec.getId());
            stmt.executeUpdate();
        }
    }

    // Eliminar un préstec
    public void eliminarPrestec(int id) throws SQLException {
        String query = "DELETE FROM prestecs WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}