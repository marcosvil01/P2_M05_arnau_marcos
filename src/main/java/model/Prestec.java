package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Prestec implements Serializable {
    // Identificador unic del prestec
    private int id;
    // Identificador del llibre prestat
    private int llibreId;
    // Nom de l'usuari que ha prestat el llibre
    private String usuari;
    // Data en que es va prestar el llibre
    private String dataPrestec;
    // Data de devolucio prevista per al llibre
    private String dataDevolucio;

    // Aquest suport permet gestionar notificacions de canvis en les propietats.
    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    // Constructor per inicialitzar un prestec amb els seus atributs
    public Prestec(int id, int llibreId, String usuari, String dataPrestec, String dataDevolucio) {
        this.id = id;
        this.llibreId = llibreId;
        this.usuari = usuari;
        this.dataPrestec = dataPrestec;
        this.dataDevolucio = dataDevolucio;
    }

    // Getter per obtenir l'id del prestec
    public int getId() { return id; }

    // Setter per modificar l'id i notificar canvis
    public void setId(int id) {
        int oldId = this.id;
        this.id = id;
        support.firePropertyChange("id", oldId, id);
    }

    // Getter per obtenir l'id del llibre prestat
    public int getLlibreId() { return llibreId; }

    // Setter per modificar l'id del llibre i notificar canvis
    public void setLlibreId(int llibreId) {
        int oldLlibreId = this.llibreId;
        this.llibreId = llibreId;
        support.firePropertyChange("llibreId", oldLlibreId, llibreId);
    }

    // Getter per obtenir el nom de l'usuari
    public String getUsuari() { return usuari; }

    // Setter per modificar l'usuari i notificar canvis
    public void setUsuari(String usuari) {
        String oldUsuari = this.usuari;
        this.usuari = usuari;
        support.firePropertyChange("usuari", oldUsuari, usuari);
    }

    // Getter per obtenir la data de prestec
    public String getDataPrestec() { return dataPrestec; }

    // Setter per modificar la data de prestec i notificar canvis
    public void setDataPrestec(String dataPrestec) {
        String oldData = this.dataPrestec;
        this.dataPrestec = dataPrestec;
        support.firePropertyChange("dataPrestec", oldData, dataPrestec);
    }

    // Getter per obtenir la data de devolucio
    public String getDataDevolucio() { return dataDevolucio; }

    // Setter per modificar la data de devolucio i notificar canvis
    public void setDataDevolucio(String dataDevolucio) {
        String oldData = this.dataDevolucio;
        this.dataDevolucio = dataDevolucio;
        support.firePropertyChange("dataDevolucio", oldData, dataDevolucio);
    }

    // Afegir un listener per escoltar canvis en les propietats
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Eliminar un listener per deixar d'escoltar canvis
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // Retorna una representacio en format text del prestec
    @Override
    public String toString() {
        return "Prestec{id=" + id + ", llibreId=" + llibreId + ", usuari='" + usuari + "', dataPrestec='" + dataPrestec + "', dataDevolucio='" + dataDevolucio + "'}";
    }
}