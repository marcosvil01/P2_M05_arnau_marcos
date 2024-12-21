package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Classe Contacte que representa un contacte amb propietats nom, email i telefon
public class Contacte implements Serializable {
    private String nom;
    private String email;
    private String telefon;

    // Suport per a notificacions de canvis en propietats
    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    // Constructor per crear un contacte
    public Contacte(String nom, String email, String telefon) {
        this.nom = nom;
        this.email = email;
        this.telefon = telefon;
    }

    // Getters i setters amb notificació de canvis
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        String oldNom = this.nom;
        this.nom = nom;
        support.firePropertyChange("nom", oldNom, nom);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        support.firePropertyChange("email", oldEmail, email);
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        String oldTelefon = this.telefon;
        this.telefon = telefon;
        support.firePropertyChange("telefon", oldTelefon, telefon);
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
        return nom + ", " + email + ", " + telefon;
    }

    // Gestor de Contactes
    public static class GestorContactes {
        private List<Contacte> contactes = new ArrayList<>();
        private PropertyChangeSupport support = new PropertyChangeSupport(this);

        // Afegir un contacte
        public void afegirContacte(Contacte contacte) {
            contactes.add(contacte);
            support.firePropertyChange("contactes", null, contactes);
        }

        // Eliminar un contacte
        public void eliminarContacte(Contacte contacte) {
            contactes.remove(contacte);
            support.firePropertyChange("contactes", null, contactes);
        }

        // Obtenir la llista de contactes
        public List<Contacte> obtenirContactes() {
            return contactes;
        }

        // Llegir contactes des d'un fitxer
        public void llegirDesDeFitxer(String rutaFitxer) throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader(rutaFitxer))) {
                String linia;
                while ((linia = reader.readLine()) != null) {
                    String[] dades = linia.split(",");
                    if (dades.length == 3) {
                        afegirContacte(new Contacte(dades[0].trim(), dades[1].trim(), dades[2].trim()));
                    }
                }
            }
        }

        // Escriure contactes a un fitxer
        public void escriureAFitxer(String rutaFitxer) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaFitxer))) {
                for (Contacte contacte : contactes) {
                    writer.write(contacte.toString());
                    writer.newLine();
                }
            }
        }
    }
}