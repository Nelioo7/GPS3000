package poo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Classe de tests pour Historique.
 * Vérifie le bon fonctionnement des méthodes de la classe Historique.
 * @author Alaëddine Oudriss
 * @version 1.0.0
 */
public class TestHistorique {

    public final String CHEMIN_TEST = "res/historique_test.ser";

    // Test du constructeur 
    @Test
    public void testConstructeur() {
        Historique h = new Historique();
        assertEquals(0, h.getNombreRecherches(), "historique vide au depart");
    }

    // Test de ajouterRecherche 
    @Test
    public void testAjouterRecherche() {
        Historique h = new Historique();
        
        h.ajouterRecherche("Nantes Paris");
        assertEquals(1, h.getNombreRecherches(), "1 recherche ajoutee");

        h.ajouterRecherche("Lille Paris");
        assertEquals(2, h.getNombreRecherches(), "2 recherches ajoutees");
    }

    // Test de getRecherches 
    @Test
    public void testGetRecherches() {
        Historique h = new Historique();
        h.ajouterRecherche("Paris Lyon");

        assertEquals("Paris Lyon", h.getRecherches().get(0), "la recherche doit correspondre");
    }

    // Test de getNombreRecherches 
    @Test
    public void testGetNombreRecherches() {
        Historique h = new Historique();
        assertEquals(0, h.getNombreRecherches(), "retourne 0 si vide");

        h.ajouterRecherche("recherche 1");
        h.ajouterRecherche("recherche 2");
        h.ajouterRecherche("recherche 3");
        assertEquals(3, h.getNombreRecherches(), "doit renvoyer 3");
    }

    @Test
    public void testSauvegarderEtCharger() {
        Historique h = new Historique();
        h.ajouterRecherche("Lille Paris");
        h.ajouterRecherche("Bordeaux Toulouse");

        try {
            h.sauvegarder(CHEMIN_TEST);
            
            Historique hCharge = Historique.charger(CHEMIN_TEST);

            assertEquals(2, hCharge.getNombreRecherches(), "doit contenir 2 recherches");
            assertEquals("Lille Paris", hCharge.getRecherches().get(0), "premiere recherche ok");
            assertEquals("Bordeaux Toulouse", hCharge.getRecherches().get(1), "deuxieme recherche ok");

        } catch (Exception e) { 
            assertTrue(false, "erreur serialisation : " + e.getMessage());
        } finally {
            new File(CHEMIN_TEST).delete();
        }
    }
}