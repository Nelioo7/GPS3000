package poo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;

/**
     * Classe de tests pour Plateforme.
     * Ce test vérifie le bon fonctionnement des constructeurs et de la méthode getNom de la classe Plateforme.
     * @author Alaëddine
     * @version 1.0.0
     */
    
public class TestPlateforme {
    
    @Test
    public void testPlateforme() {
        // Test du constructeur et des 
        Plateforme plateforme1 = new Plateforme();
        assertEquals(0, plateforme1.getLieux().size(), "La liste des lieux doit être vide");
        assertEquals(0, plateforme1.getConnexions().size(), "La liste des connexions doit être vide");
    }

    @Test
    public void testAjouterLieu() {
        Plateforme plateforme = new Plateforme();

        Ville paris = new Ville("Paris");
        // Ajouter une ville à la plateforme
        plateforme.ajouterLieu(paris);

        assertEquals(1, plateforme.getLieux().size(), "La liste des lieux doit contenir 1 élément");     
        // Vérifier que la ville doublon n'est pas ajoutée
        plateforme.ajouterLieu(paris);
        assertEquals(1, plateforme.getLieux().size(), "La liste des lieux doit toujours contenir 1 élément après tentative d'ajout d'un doublon");
        // Vérifier que la ville ajoutée est bien celle attendue
        assertEquals(paris, plateforme.getLieux().get(0), "Le lieu ajouté doit être Paris");
    }

    @Test
    public void testAjouterConnexion() {
        Plateforme plateforme = new Plateforme();

        // creation de deux villes et d'une connexion entre elles
        Ville paris = new Ville("Paris");
        Ville lyon = new Ville("Lyon");
        plateforme.ajouterLieu(paris);
        plateforme.ajouterLieu(lyon);
        Trajet trajet = new Trajet(paris, lyon, null, null);

        plateforme.ajouterConnexion(trajet);
        // Vérifier que la connexion a été ajoutée à la plateforme
        assertEquals(1, plateforme.getConnexions().size(), "La liste des connexions doit contenir 1 élément");
        // Vérifier que la connexion ajoutée est bien celle entre Paris et Lyon
        assertEquals(trajet, plateforme.getConnexions().get(0), "La connexion ajoutée doit être le trajet entre Paris et Lyon");
            
    }

    @Test
    public void testTrouverLieu() {
        Plateforme plateforme = new Plateforme();

        Ville Lille = new Ville("Lille");
        Ville lyon = new Ville("Lyon");
        plateforme.ajouterLieu(Lille);
        plateforme.ajouterLieu(lyon);

        // Vérifier que la méthode trouverLieu retourne le bon lieu
        assertEquals(Lille, plateforme.trouverLieu("Lille"),"trouverLieu() doit retourner la ville correspondante");
        // Vérifier que la méthode trouverLieu retourne null si la ville n'existe pas
        assertNull(plateforme.trouverLieu("Marseille"),"trouverLieu() doit retourner null si la ville est absente");
        
    }
}