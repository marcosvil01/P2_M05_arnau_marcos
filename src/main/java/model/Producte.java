package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe Producte que representa un producte amb propietats id, nom i preu
public class Producte implements Serializable {
    private int id;
    private String nom;
    private double preu;

    // Suport per a notificacions de canvis en propietats
    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    // Constructors
    public Producte() {}

    public Producte(int id, String nom, double preu) {
        this.id = id;
        this.nom = nom;
        this.preu = preu;
    }

    // Getters i setters amb notificació de canvis
    public int getId() {
        return id;
    }

    public void setId(int id) {
        int oldId = this.id;
        this.id = id;
        support.firePropertyChange("id", oldId, id);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        String oldNom = this.nom;
        this.nom = nom;
        support.firePropertyChange("nom", oldNom, nom);
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        double oldPreu = this.preu;
        this.preu = preu;
        support.firePropertyChange("preu", oldPreu, preu);
    }

    // Afegir i eliminar listeners per notificacions de propietats
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Producte{id=" + id + ", nom='" + nom + "', preu=" + preu + '}';
    }

    // Gestor de Productes per operar amb la base de dades
    public static class GestorProductes {
        private String url;
        private String usuari;
        private String contrasenya;

        public GestorProductes(String url, String usuari, String contrasenya) {
            this.url = url;
            this.usuari = usuari;
            this.contrasenya = contrasenya;
        }

        // Crear un producte
        public void crearProducte(Producte producte) throws SQLException {
            try (Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO productes (nom, preu) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, producte.getNom());
                stmt.setDouble(2, producte.getPreu());
                stmt.executeUpdate();
            }
        }

        // Llegir tots els productes
        public List<Producte> llegirProductes() throws SQLException {
            List<Producte> productes = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM productes")) {
                while (rs.next()) {
                    productes.add(new Producte(rs.getInt("id"), rs.getString("nom"), rs.getDouble("preu")));
                }
            }
            return productes;
        }

        // Actualitzar un producte
        public void actualitzarProducte(Producte producte) throws SQLException {
            try (Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
                 PreparedStatement stmt = conn.prepareStatement("UPDATE productes SET nom = ?, preu = ? WHERE id = ?")) {
                stmt.setString(1, producte.getNom());
                stmt.setDouble(2, producte.getPreu());
                stmt.setInt(3, producte.getId());
                stmt.executeUpdate();
            }
        }

        // Eliminar un producte
        public void eliminarProducte(int id) throws SQLException {
            try (Connection conn = DriverManager.getConnection(url, usuari, contrasenya);
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM productes WHERE id = ?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        }
    }
}