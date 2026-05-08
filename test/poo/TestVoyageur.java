package poo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

/**
 * Classe de tests pour Voyageur.
 * Vérifie le bon fonctionnement des constructeurs, des accesseurs, de la méthode equals() et de la méthode toString().
 *
 * @author Alaëddine
 * @version 1.0.0
 */
public class TestVoyageur {

    // Teste du constructeur à trois paramètres et les accesseurs associés.
    @Test
    public void testConstructeurComplet() {
        System.out.println("-- Constructeur complet --");

        Voyageur v = new Voyageur("Dupont", "Jean", TypeCout.PRIX);

        assertEquals("Dupont", v.getNom(), "getNom() retourne le nom fourni");
        assertEquals("Jean", v.getPrenom(), "getPrenom() retourne le prénom fourni");
        assertEquals(TypeCout.PRIX, v.getCritereOpti(), "getCritereOpti() retourne le critère fourni");
    }

    //Teste du constructeur à deux paramètres (critère par défaut : TEMPS).
    @Test
    public void testConstructeurParDefaut() {
        System.out.println("-- Constructeur par défaut --");

        Voyageur v = new Voyageur("Martin", "Alice");

        assertEquals(TypeCout.TEMPS, v.getCritereOpti(), "getCritereOpti() vaut TEMPS par défaut");
    }

    //Teste de la méthode equals() dans différents cas.
    @Test
    public void testEquals() {
        System.out.println("-- equals() --");

        Voyageur v1 = new Voyageur("Dupont", "Jean", TypeCout.PRIX);
        Voyageur v2 = new Voyageur("Martin", "Alice");
        Voyageur v3 = new Voyageur("Dupont", "Jean", TypeCout.CO2); // même identité que v1

        assertTrue(v1.equals(v1),"equals() avec lui-même retourne true");
        
        assertTrue(v1.equals(v3), "equals() avec même nom et prénom retourne true");

        assertFalse(v1.equals(v2), "equals() avec nom et prénom différents retourne false");
        
        assertFalse(v1.equals(null), "equals() avec null retourne false");
        
    }

    //Teste de la méthode toString() pour vérifier qu'elle contient les informations essentielles du voyageur.
    @Test
    public void testToString() {
        System.out.println("-- toString() --");
        
        Voyageur v = new Voyageur("Dupont", "Jean", TypeCout.PRIX);
        String str = v.toString();
        assertTrue(str.contains("Dupont"), "toString() contient le nom");
        assertTrue(str.contains("Jean"), "toString() contient le prénom");
        assertTrue(str.contains("PRIX"), "toString() contient le critère");
    }
}
