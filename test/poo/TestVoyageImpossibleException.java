package poo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import fr.ulille.but.sae_s2_2026.ModaliteTransport;

/**
 * Classe de tests pour VoyageImpossibleException.
 * Vérifie que l'exception est bien levée par trouverItineraires() et que son message est correct.
 * @author Alaëddine Oudriss
 * @version 1.0.0
 */
public class TestVoyageImpossibleException {

    public final Ville villeA = new Ville("villeA");
    public final Ville villeB = new Ville("villeB");
    public final Ville villeC = new Ville("villeC");
    public final Ville villeD = new Ville("villeD");
    public final CoutConnexion couts = new CoutConnexion(10.0, 30.0, 1.0);

    // Aucune connexion une exception doit être levée
    @Test
    public void testExceptionAucuneConnexion() {
        Plateforme plateforme = new Plateforme();
        plateforme.ajouterLieu(villeA);
        plateforme.ajouterLieu(villeD);

        GestionnaireItineraire gestionnaire = new GestionnaireItineraire(plateforme);

        boolean exceptionLevee = false;
        try {
            gestionnaire.trouverItineraires(villeA, villeD, TypeCout.TEMPS, 3);
        } catch (VoyageImpossibleException e) {
            exceptionLevee = true;
        }

        assertTrue(exceptionLevee, "Doit lever VoyageImpossibleException si aucune connexion");
    }

    // les chemins de A vers B et C vers D existent mais pas de A jusqu'à D doonc : exception
    @Test
    public void testExceptionCheminInexistant() {
        Plateforme plateforme = new Plateforme();
        plateforme.ajouterLieu(villeA);
        plateforme.ajouterLieu(villeB);
        plateforme.ajouterLieu(villeC);
        plateforme.ajouterLieu(villeD);
        plateforme.ajouterConnexion(new Trajet(villeA, villeB, ModaliteTransport.TRAIN, couts));
        plateforme.ajouterConnexion(new Trajet(villeC, villeD, ModaliteTransport.TRAIN, couts));

        GestionnaireItineraire gestionnaire = new GestionnaireItineraire(plateforme);

        boolean exceptionLevee = false;
        try {
            gestionnaire.trouverItineraires(villeA, villeD, TypeCout.TEMPS, 3);
        } catch (VoyageImpossibleException e) {
            exceptionLevee = true;
        }

        assertTrue(exceptionLevee, "Doit lever VoyageImpossibleException si chemin inexistant");
    }

    //testMessageException Vérifie que getMessage() contient les deux noms de villes
    @Test
    public void testMessageException() {
        Plateforme plateforme = new Plateforme();
        plateforme.ajouterLieu(villeA);
        plateforme.ajouterLieu(villeD);

        GestionnaireItineraire gestionnaire = new GestionnaireItineraire(plateforme);

        try {
            gestionnaire.trouverItineraires(villeA, villeD, TypeCout.TEMPS, 3);
        } catch (VoyageImpossibleException e) {
            assertTrue(e.getMessage().contains("villeA"), "getMessage() doit contenir villeA");
            assertTrue(e.getMessage().contains("villeD"), "getMessage() doit contenir villeD");
        }
    }
}