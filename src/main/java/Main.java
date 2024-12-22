import dao.*;
import model.*;
import utils.ConnexioDB_Biblioteca;
import utils.ConnexioDB_Productes;

import java.sql.*;
import java.util.List;
import java.io.IOException;

// Classe Main per cridar els exercicis 1, 2 i 3
public class Main {
    public static void main(String[] args) {
        try {
            // Exercici 1: Gestió de Contactes amb fitxers de text
            executarExercici1();

            // Exercici 2: Gestió de Productes amb JDBC
            executarExercici2();

            // Exercici 3: Gestió de Biblioteca
            executarExercici3();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Exercici 1: Gestió de Contactes
    public static void executarExercici1() throws IOException {
        System.out.println("Exercici 1: Gestió de Contactes");
        GestorContactes gestorContactes = new GestorContactes();

        // Llegir dades des del fitxer
        gestorContactes.llegirDesDeFitxer("src/main/resources/dades/exemples_contactes.txt");

        // Afegir un nou contacte
        gestorContactes.afegirContacte(new Contacte("Nou Contacte", "nou@example.com", "111222333"));

        // Escriure dades al fitxer
        gestorContactes.escriureAFitxer("src/main/resources/dades/exemples_contactes.txt");

        // Mostrar contactes
        for (Contacte contacte : gestorContactes.obtenirContactes()) {
            System.out.println(contacte);
        }
    }

    // Exercici 2: Gestió de Productes
    public static void executarExercici2() throws SQLException, IOException {
        System.out.println("\nExercici 2: Gestió de Productes");

        // Connexió específica per a productes
        Connection conn = ConnexioDB_Productes.obtenirConnexio();
        GestorProductes gestorProductes = new GestorProductes(conn);

        // Crear un nou producte
        Producte producte = new Producte(0, "Producte Exemple", 19.99);
        gestorProductes.crearProducte(producte);

        // Llegir i mostrar productes
        List<Producte> productes = gestorProductes.llegirProductes();
        for (Producte p : productes) {
            System.out.println(p);
        }

        // Actualitzar un producte
        producte.setPreu(25.99);
        gestorProductes.actualitzarProducte(producte);

        // Eliminar el producte
        gestorProductes.eliminarProducte(producte.getId());

        gestorProductes.tancarConnexio();
    }

    // Exercici 3: Gestió de Biblioteca
    public static void executarExercici3() throws SQLException, IOException {
        System.out.println("\nExercici 3: Gestió de Biblioteca");
        Connection conn = ConnexioDB_Biblioteca.obtenirConnexio();
        GestorAutors gestorAutors = new GestorAutors(conn);
        GestorLlibres gestorLlibres = new GestorLlibres(conn);
        GestorPrestecs gestorPrestecs = new GestorPrestecs(conn);

        // Afegir un autor
        Autor autor = new Autor(0, "Autor Exemple", "Espanya");
        System.out.println("Afegint autor: " + autor.toString());
        gestorAutors.crearAutor(autor);

        // Afegir un llibre
        Llibre llibre = new Llibre(0, "Llibre Exemple", 2023);
        System.out.println("Afegint llibre: " + llibre.toString());
        gestorLlibres.crearLlibre(llibre);

        // Obtenir l'ID del llibre pel seu títol utilitzant el gestor
        int llibreId = gestorLlibres.obtenirIdPerTitol("Llibre Exemple");

        // Registrar un préstec per el llibre
        Prestec prestec = new Prestec(0, llibreId, "Usuari Exemple", "2024-01-01", "2024-01-15");
        System.out.println("Afegint prestec: " + prestec.toString());
        gestorPrestecs.crearPrestec(prestec);

        // Mostrar autors
        System.out.println("Llista d'autors: ");
        List<Autor> autors = gestorAutors.llegirAutors();
        for (Autor a : autors) {
            System.out.println(a);
        }

        // Mostrar llibres
        System.out.println("Llista de llibres: ");
        List<Llibre> llibres = gestorLlibres.llegirLlibres();
        for (Llibre l : llibres) {
            System.out.println(l);
        }

        // Mostrar préstecs
        System.out.println("Llista de prestecs: ");
        List<Prestec> prestecs = gestorPrestecs.llegirPrestecs();
        for (Prestec pr : prestecs) {
            System.out.println(pr);
        }
        conn.close();
    }
}
