package dao;

import model.Contacte;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// GestorContactes: Gestiona contactes emmagatzemats en fitxers de text
public class GestorContactes {
    private List<Contacte> contactes;
    private transient PropertyChangeSupport support;

    // Constructor que inicialitza la llista i el suport per a notificacions
    public GestorContactes() {
        this.contactes = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);
    }

    // Afegir un nou contacte
    public void afegirContacte(Contacte contacte) {
        contactes.add(contacte);
        support.firePropertyChange("contactes", null, contactes);
    }

    // Eliminar un contacte
    public void eliminarContacte(Contacte contacte) {
        contactes.remove(contacte);
        support.firePropertyChange("contactes", null, contactes);
    }

    // Obtenir tots els contactes
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

    // Afegir un listener per notificacions
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Eliminar un listener per notificacions
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
