package test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

import src.Trajet;
import src.Ville;
import src.CoutConnexion;
import src.TypeCout;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;



/**
 * Classe de tests pour Trajet.
 * @author Jérôme Pardoue
 * @version 1.0.0
 */
public class TestTrajet {
    public final Ville ville1 = new Ville("Paris");
    public final Ville ville2 = new Ville("Lille");
    public final Ville ville3 = new Ville("Marseille");
    public final Ville ville4 = new Ville("Lyon");
    public final Ville ville5 = new Ville("Bordeaux");

    /** 
     * Test du constructeur 
     * Vérifie que les objets Trajet sont créés sans erreur avec différentes modalités et coûts.
     * Aucun assert n'est nécessaire ici, l'absence d'exception est suffisante pour valider le test.
     * @author Jérôme Pardoue
     * @version 1.0.0
    */
    @Test
    public void testConstructor() {
        Trajet trajet1 = new Trajet(ville1, ville2, ModaliteTransport.TRAIN, new CoutConnexion(60.0, 80.0, 1.7));
        Trajet trajet2 = new Trajet(ville3, ville4, ModaliteTransport.AVION, new CoutConnexion(150.0, 200.0, 0.5));
        Trajet trajet3 = new Trajet(ville5, ville1, ModaliteTransport.BUS, new CoutConnexion(30.0, 50.0, 2.0));
    }

    /** 
     * Test des méthodes d'accès 
     * Vérifie que les méthodes getDepart, getArrivee, getModalite et getCouts renvoient les valeurs correctes.
     * @author Jérôme Pardoue
     * @version 1.0.0
    */
    @Test
    public void testAccessors() {
        Trajet trajet = new Trajet(ville1, ville2, ModaliteTransport.TRAIN, new CoutConnexion(60.0, 80.0, 1.7));
        assertEquals(ville1, trajet.getDepart());
        assertEquals(ville2, trajet.getArrivee());
        assertEquals(ModaliteTransport.TRAIN, trajet.getModalite());
        assertEquals(60.0, trajet.getCouts().getCout(TypeCout.PRIX));
        assertEquals(80.0, trajet.getCouts().getCout(TypeCout.TEMPS));
        assertEquals(1.7, trajet.getCouts().getCout(TypeCout.CO2));
    }

    /** 
     * Test de la méthode toString 
     * Vérifie que la représentation textuelle du trajet est correcte.
     * @author Jérôme Pardoue
     * @version 1.0.0
    */
    @Test
    public void testToString() {
        Trajet trajet1 = new Trajet(ville1, ville2, ModaliteTransport.TRAIN, new CoutConnexion(60.0, 80.0, 1.7));
        assertEquals("Paris -> Lille (TRAIN)", trajet1.toString());
        Trajet trajet2 = new Trajet(ville3, ville4, ModaliteTransport.AVION, new CoutConnexion(150.0, 200.0, 0.5));
        assertEquals("Marseille -> Lyon (AVION)", trajet2.toString());
        Trajet trajet3 = new Trajet(ville5, ville1, ModaliteTransport.BUS, new CoutConnexion(30.0, 50.0, 2.0));
        assertEquals("Bordeaux -> Paris (BUS)", trajet3.toString());
    }
}