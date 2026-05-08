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
 * Scénario international (Lille -> Sydney).
 * @author Amayas Mehmel
 * @version 1.2.0
 */
public class GraphesV1 {

    public static void main(String[] args) {
        System.out.println("=== PROTOTYPE GRAPHES V1 : SCÉNARIO INTERNATIONAL ===\n");

        // création du graphe
        MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

        // création des 6 villes (Sommets) d'après le rapport
        Ville lille = new Ville("Lille");
        Ville paris = new Ville("Paris");
        Ville jakarta = new Ville("Jakarta");
        Ville newYork = new Ville("New York");
        Ville sydney = new Ville("Sydney");
        Ville tokyo = new Ville("Tokyo");

        graphe.ajouterSommet(lille);
        graphe.ajouterSommet(paris);
        graphe.ajouterSommet(jakarta);
        graphe.ajouterSommet(newYork);
        graphe.ajouterSommet(sydney);
        graphe.ajouterSommet(tokyo);

        // Préférences de l'utilisatrice
        ModaliteTransport modeChoisi = ModaliteTransport.AVION;
        TypeCout critereOptimisation = TypeCout.PRIX; 

        System.out.println("Préférences de l'utilisatrice :");
        System.out.println("- Transport choisi : " + modeChoisi);
        System.out.println("- Critère à optimiser : " + critereOptimisation);
        System.out.println("- Trajet : Lille -> Sydney\n");

        // tableau des connexions directes (les chemins composés ont été retirés)
        // Rappel de l'ordre : CoutConnexion(prix, temps, co2)
        Trajet[] reseau = {
            // TRAINS
            new Trajet(lille, paris, ModaliteTransport.TRAIN, new CoutConnexion(35.0, 60.0, 1.7)),

            // AVIONS
            new Trajet(lille, jakarta, ModaliteTransport.AVION, new CoutConnexion(550.0, 780.0, 280.0)),
            new Trajet(lille, newYork, ModaliteTransport.AVION, new CoutConnexion(820.0, 1140.0, 440.0)),
            new Trajet(jakarta, sydney, ModaliteTransport.AVION, new CoutConnexion(310.0, 420.0, 155.0)),
            new Trajet(jakarta, newYork, ModaliteTransport.AVION, new CoutConnexion(820.0, 1140.0, 440.0)),
            new Trajet(paris, sydney, ModaliteTransport.AVION, new CoutConnexion(900.0, 1320.0, 405.0)),
            new Trajet(paris, jakarta, ModaliteTransport.AVION, new CoutConnexion(700.0, 810.0, 310.0)),
            new Trajet(paris, tokyo, ModaliteTransport.AVION, new CoutConnexion(620.0, 780.0, 290.0)),
            new Trajet(sydney, tokyo, ModaliteTransport.AVION, new CoutConnexion(480.0, 600.0, 215.0)),
            new Trajet(newYork, tokyo, ModaliteTransport.AVION, new CoutConnexion(520.0, 840.0, 310.0)),
            new Trajet(newYork, sydney, ModaliteTransport.AVION, new CoutConnexion(950.0, 1260.0, 310.0)),

            // BUS (Ajout fictif pour respecter la consigne "3 modes de transport présents dans l'exemple")
            new Trajet(lille, paris, ModaliteTransport.BUS, new CoutConnexion(15.0, 180.0, 5.0))
        };

        // remplissage du graphe
        for (Trajet t : reseau) {
            if (t.getModalite() == modeChoisi) {
                graphe.ajouterArete(t, t.getPoids(critereOptimisation));
            }
        }

        // On cherche de Lille à Sydney et on demande k=4
        List<Chemin> resultats = AlgorithmeKPCC.kpcc(graphe, lille, sydney, 4);

        // Affichage des 4 itinéraires
        System.out.println("=== 4 MEILLEURS ITINÉRAIRES ===");
        int i = 1;
        for (Chemin c : resultats) {
            System.out.println(i + ") " + c.toString() + " [Coût total (" + critereOptimisation + ") : " + c.poids() + " €]");
            i++;
        }
        
        if (resultats.isEmpty()) {
            System.out.println("Aucun itinéraire trouvé.");
        }
    }
}