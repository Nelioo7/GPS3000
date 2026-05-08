package test.poo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import src.poo.Ville;

public class TestVille {

    /**
    * Classe de tests pour Ville.
    * Ce test vérifie le bon fonctionnement du constructeur, 
    * des méthodes getNom, toString, equals et hashCode de la classe Ville.
    * @author Alaëddine
    * @version 1.0.0
    */
   // Test du constructeur et de la méthode getNom
    @Test
    public void testGetNom() {
        Ville ville = new Ville("Paris");
        assertEquals(ville.getNom(), "Paris");
    }
    // Test de la méthode toString
    @Test
    public void testToString() {
        Ville ville = new Ville("Lille");
        assertEquals(ville.toString(), "Lille");
    }
    // Test de la méthode equals
    @Test
    public void testEquals() {
        Ville ville1 = new Ville("Marseille");
        Ville ville2 = new Ville("Marseille");
        Ville ville3 = new Ville("Lyon");
        assertEquals(ville1.equals(ville2), true); // Les deux villes sont égales
        assertEquals(ville1.equals(ville3), false); // Les deux villes ne sont pas égales
    }

    // Test de la méthode hashCode
    @Test
    public void testHashCode() {
        Ville ville1 = new Ville("Nice");
        Ville ville2 = new Ville("Nice");
        assertEquals(ville1.hashCode(), ville2.hashCode()); // Les deux villes ont le même code de hachage
    }

    
}
