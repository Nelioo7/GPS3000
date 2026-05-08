package graphes;

import java.util.List;

import fr.ulille.but.sae_s2_2026.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;
import fr.ulille.but.sae_s2_2026.MultiGrapheOrienteValue;
import poo.CoutConnexion;
import poo.Trajet;
import poo.TypeCout;
import poo.Ville;

/**
 * Prototype exécutable pour la Version 1 partie graphes.
 * @author Amayas Mehmel
 * @version 1.1.5
 */

public class GraphesV1 {

    public static void main(String[] args) {
        System.out.println("=== PROTOTYPE GRAPHES V1 : SCÉNARIO 5 VILLES / 15 CONNEXIONS ===\n");

        // création du graphe
        MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

        // création des 5 villes (Sommets)
        Ville lille = new Ville("Lille");
        Ville paris = new Ville("Paris");
        Ville lyon = new Ville("Lyon");
        Ville marseille = new Ville("Marseille");
        Ville bordeaux = new Ville("Bordeaux");

        graphe.ajouterSommet(lille);
        graphe.ajouterSommet(paris);
        graphe.ajouterSommet(lyon);
        graphe.ajouterSommet(marseille);
        graphe.ajouterSommet(bordeaux);

        // création des 15 connexions 
        // on va filtrer manuellement pour le scénario (ex: L'utilisateur ne veut que le TRAIN)
        ModaliteTransport modeChoisi = ModaliteTransport.TRAIN;
        TypeCout critereOptimisation = TypeCout.PRIX; // l'utilisateur veut le moins cher

        System.out.println("Préférences de l'utilisatrice :");
        System.out.println("- Transport choisi : " + modeChoisi);
        System.out.println("- Critère à optimiser : " + critereOptimisation);
        System.out.println("- Trajet : Lille -> Marseille\n");

        // tableau des 15 connexions avec des valeurs
        Trajet[] reseau = {
            // TRAINS
            new Trajet(lille, paris, ModaliteTransport.TRAIN, new CoutConnexion(40.0, 60.0, 2.0)),
            new Trajet(paris, lyon, ModaliteTransport.TRAIN, new CoutConnexion(70.0, 120.0, 3.5)),
            new Trajet(lyon, marseille, ModaliteTransport.TRAIN, new CoutConnexion(50.0, 100.0, 2.5)),
            new Trajet(paris, bordeaux, ModaliteTransport.TRAIN, new CoutConnexion(80.0, 130.0, 3.0)),
            new Trajet(bordeaux, marseille, ModaliteTransport.TRAIN, new CoutConnexion(60.0, 140.0, 3.2)),
            new Trajet(lille, lyon, ModaliteTransport.TRAIN, new CoutConnexion(100.0, 180.0, 4.0)), 
            new Trajet(paris, marseille, ModaliteTransport.TRAIN, new CoutConnexion(110.0, 190.0, 5.0)), 

            // BUS
            new Trajet(lille, paris, ModaliteTransport.BUS, new CoutConnexion(15.0, 180.0, 8.0)),
            new Trajet(paris, lyon, ModaliteTransport.BUS, new CoutConnexion(25.0, 300.0, 12.0)),
            new Trajet(lyon, marseille, ModaliteTransport.BUS, new CoutConnexion(20.0, 240.0, 10.0)),
            new Trajet(paris, bordeaux, ModaliteTransport.BUS, new CoutConnexion(30.0, 360.0, 14.0)),
            new Trajet(bordeaux, marseille, ModaliteTransport.BUS, new CoutConnexion(25.0, 320.0, 11.0)),

            // AVIONS
            new Trajet(lille, marseille, ModaliteTransport.AVION, new CoutConnexion(150.0, 90.0, 120.0)),
            new Trajet(paris, marseille, ModaliteTransport.AVION, new CoutConnexion(100.0, 75.0, 95.0)),
            new Trajet(paris, bordeaux, ModaliteTransport.AVION, new CoutConnexion(90.0, 65.0, 85.0))
        };

        // remplissage du graphe
        for (Trajet t : reseau) {
            if (t.getModalite() == modeChoisi) {
                graphe.ajouterArete(t, t.getPoids(critereOptimisation));
            }
        }

        // On cherche de Lille à Marseille et on demande k=4
        List<Chemin> resultats = AlgorithmeKPCC.kpcc(graphe, lille, marseille, 4);

        // Affichage des 4 itinéraires
        System.out.println("=== 4 MEILLEURS ITINÉRAIRES ===");
        int i = 1;
        for (Chemin c : resultats) {
            System.out.println(i + ") " + c.toString() + " [Coût total (" + critereOptimisation + ") : " + c.poids() + "]");
            i++;
        }
        
        if (resultats.isEmpty()) {
            System.out.println("Aucun itinéraire trouvé.");
        }
    }

}