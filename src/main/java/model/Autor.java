package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Autor implements Serializable {
    // Identificador unic de l'autor
    private int id;
    // Nom de l'autor
    private String nom;
    // Nacionalitat de l'autor
    private String nacionalitat;

    // Aquest suport permet gestionar notificacions de canvis en les propietats.
    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    // Constructor per inicialitzar un autor amb els seus atributs
    public Autor(int id, String nom, String nacionalitat) {
        this.id = id;
        this.nom = nom;
        this.nacionalitat = nacionalitat;
    }

    // Getter per obtenir l'id de l'autor
    public int getId() { return id; }

    // Setter per modificar l'id i notificar canvis
    public void setId(int id) {
        int oldId = this.id;
        this.id = id;
        support.firePropertyChange("id", oldId, id);
    }

    // Getter per obtenir el nom de l'autor
    public String getNom() { return nom; }

    // Setter per modificar el nom i notificar canvis
    public void setNom(String nom) {
        String oldNom = this.nom;
        this.nom = nom;
        support.firePropertyChange("nom", oldNom, nom);
    }

    // Getter per obtenir la nacionalitat de l'autor
    public String getNacionalitat() { return nacionalitat; }

    // Setter per modificar la nacionalitat i notificar canvis
    public void setNacionalitat(String nacionalitat) {
        String oldNat = this.nacionalitat;
        this.nacionalitat = nacionalitat;
        support.firePropertyChange("nacionalitat", oldNat, nacionalitat);
    }

    // Afegir un listener per escoltar canvis en les propietats
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Eliminar un listener per deixar d'escoltar canvis
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // Retorna una representacio en format text de l'autor
    @Override
    public String toString() {
        return "Autor{id=" + id + ", nom='" + nom + "', nacionalitat='" + nacionalitat + "'}";
    }
}
