package poo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.Test;
import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;

/**
 * Classe de tests pour GestionnaireItineraire.
 * Vérifie le bon fonctionnement de getPointsInteret().
 * @author Alaëddine Oudriss
 * @version 1.0.0
 */
public class TestGestionnaireItineraire {

    public final Ville villeA = new Ville("villeA");
    public final Ville villeB = new Ville("villeB");
    public final Ville villeC = new Ville("villeC");
    public final Ville villeD = new Ville("villeD");
    public final CoutConnexion couts = new CoutConnexion(10.0, 30.0, 1.0);

    // Pas de changement de transport : A->B->C->D (train-train-train)
    // On attend seulement [A, D], villeB et villeC doivent être absentes
    @Test
    public void testPointsInteretSansChangement() {
        Plateforme plateforme = new Plateforme();
        plateforme.ajouterLieu(villeA);
        plateforme.ajouterLieu(villeB);
        plateforme.ajouterLieu(villeC);
        plateforme.ajouterLieu(villeD);
        plateforme.ajouterConnexion(new Trajet(villeA, villeB, ModaliteTransport.TRAIN, couts));
        plateforme.ajouterConnexion(new Trajet(villeB, villeC, ModaliteTransport.TRAIN, couts));
        plateforme.ajouterConnexion(new Trajet(villeC, villeD, ModaliteTransport.TRAIN, couts));

        GestionnaireItineraire gestionnaire = new GestionnaireItineraire(plateforme);

        try {
            List<Chemin> chemins = gestionnaire.trouverItineraires(villeA, villeD, TypeCout.TEMPS, 1);
            List<Lieu> points = gestionnaire.getPointsInteret(chemins.get(0));

            assertEquals(2, points.size(), "Doit avoir 2 points (départ + arrivée)");
            assertEquals(villeA, points.get(0) , "Premier point doit être villeA");
            assertEquals(villeD, points.get(1), "Dernier point doit être villeD");
            assertFalse(points.contains(villeB), "villeB ne doit PAS être un point d'intérêt");
            assertFalse(points.contains(villeC), "villeC ne doit PAS être un point d'intérêt");

        } catch (VoyageImpossibleException e) {
            assertTrue(false, e.getMessage());
        }
    }

    // Un changement de transport : A->B->C->D (train-train-avion)
    // On attend [A, C, D], villeB doit être absente
    @Test
    public void testPointsInteretAvecUnChangement() {
        Plateforme plateforme = new Plateforme();
        plateforme.ajouterLieu(villeA);
        plateforme.ajouterLieu(villeB);
        plateforme.ajouterLieu(villeC);
        plateforme.ajouterLieu(villeD);
        plateforme.ajouterConnexion(new Trajet(villeA, villeB, ModaliteTransport.TRAIN, couts));
        plateforme.ajouterConnexion(new Trajet(villeB, villeC, ModaliteTransport.TRAIN, couts));
        plateforme.ajouterConnexion(new Trajet(villeC, villeD, ModaliteTransport.AVION, couts));

        GestionnaireItineraire gestionnaire = new GestionnaireItineraire(plateforme);

        try {
            List<Chemin> chemins = gestionnaire.trouverItineraires(villeA, villeD, TypeCout.TEMPS, 1);
            List<Lieu> points = gestionnaire.getPointsInteret(chemins.get(0));

            assertEquals(3, points.size(), "Doit avoir 3 points");
            assertEquals(villeA, points.get(0), "Premier point doit être villeA");
            assertEquals(villeC, points.get(1), "Point de changement doit être villeC");
            assertEquals(villeD, points.get(2), "Dernier point doit être villeD");
            assertFalse(points.contains(villeB), "villeB ne doit PAS être un point d'intérêt");

        } catch (VoyageImpossibleException e) {
            assertTrue(false,  e.getMessage());
        }
    }

    // Deux changements : A->B->C->D (train-avion-bus)
    // On attend [A, B, C, D], tous les noeuds sont des points d'intérêt
    @Test
    public void testPointsInteretAvecDeuxChangements() {
        Plateforme plateforme = new Plateforme();
        plateforme.ajouterLieu(villeA);
        plateforme.ajouterLieu(villeB);
        plateforme.ajouterLieu(villeC);
        plateforme.ajouterLieu(villeD);
        plateforme.ajouterConnexion(new Trajet(villeA, villeB, ModaliteTransport.TRAIN, couts));
        plateforme.ajouterConnexion(new Trajet(villeB, villeC, ModaliteTransport.AVION, couts));
        plateforme.ajouterConnexion(new Trajet(villeC, villeD, ModaliteTransport.BUS,   couts));

        GestionnaireItineraire gestionnaire = new GestionnaireItineraire(plateforme);

        try {
            List<Chemin> chemins = gestionnaire.trouverItineraires(villeA, villeD, TypeCout.TEMPS, 1);
            List<Lieu> points = gestionnaire.getPointsInteret(chemins.get(0));

            assertEquals(4, points.size(), "Doit avoir 4 points");
            assertEquals(villeA, points.get(0));
            assertEquals(villeB, points.get(1));
            assertEquals(villeC, points.get(2));
            assertEquals(villeD, points.get(3));

        } catch (VoyageImpossibleException e) {
            assertTrue(false, e.getMessage());
        }
    }

    // Chemin qui existe → pas d'exception, liste non vide
    @Test
    public void testPasExceptionSiCheminExiste() {
        Plateforme plateforme = new Plateforme();
        plateforme.ajouterLieu(villeA);
        plateforme.ajouterLieu(villeB);
        plateforme.ajouterLieu(villeD);
        plateforme.ajouterConnexion(new Trajet(villeA, villeB, ModaliteTransport.TRAIN, couts));
        plateforme.ajouterConnexion(new Trajet(villeB, villeD, ModaliteTransport.TRAIN, couts));

        GestionnaireItineraire gestionnaire = new GestionnaireItineraire(plateforme);

        try {
            List<Chemin> resultats = gestionnaire.trouverItineraires(villeA, villeD, TypeCout.TEMPS, 3);
            assertTrue(resultats.size() > 0, "Retourner au moins 1 chemin");
        } catch (VoyageImpossibleException e) {
            assertTrue(false, "pas d'exception car le chemin existe");
        }
    }
}