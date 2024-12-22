package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

// Classe Llibre que representa un llibre amb id, titol i any de publicacio
public class Llibre implements Serializable {
    // Identificador unic del llibre (es autoincrement)
    private int id;
    // Titol del llibre
    private String titol;
    // Any de publicacio del llibre
    private int anyPublicacio;

    // Aquest suport permet gestionar notificacions de canvis en les propietats.
    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    // Constructor per inicialitzar un llibre amb els seus atributs
    public Llibre(int id, String titol, int anyPublicacio) {
        this.id = id;
        this.titol = titol;
        this.anyPublicacio = anyPublicacio;
    }

    // Getter per obtenir l'id del llibre
    public int getId() { return id; }

    // Setter per modificar l'id i notificar canvis
    public void setId(int id) {
        int oldId = this.id;
        this.id = id;
        support.firePropertyChange("id", oldId, id);
    }

    // Getter per obtenir el titol del llibre
    public String getTitol() { return titol; }

    // Setter per modificar el titol i notificar canvis
    public void setTitol(String titol) {
        String oldTitol = this.titol;
        this.titol = titol;
        support.firePropertyChange("titol", oldTitol, titol);
    }

    // Getter per obtenir l'any de publicacio
    public int getAnyPublicacio() { return anyPublicacio; }

    // Setter per modificar l'any de publicacio i notificar canvis
    public void setAnyPublicacio(int anyPublicacio) {
        int oldAny = this.anyPublicacio;
        this.anyPublicacio = anyPublicacio;
        support.firePropertyChange("anyPublicacio", oldAny, anyPublicacio);
    }

    // Afegir un listener per escoltar canvis en les propietats
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Eliminar un listener per deixar d'escoltar canvis
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // Retorna una representacio en format text del llibre
    @Override
    public String toString() {
        return "Llibre{id=" + id + ", titol='" + titol + "', anyPublicacio=" + anyPublicacio + '}';
    }
}
