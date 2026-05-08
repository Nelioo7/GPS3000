package poo;

import java.util.List;

import fr.ulille.but.sae_s2_2026.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2026.Connexion;

/**
 * Classe responsable de la construction du graphe et du calcul d'itinéraires
 * @author Erwann Besegher
 * @version 1.0.0
 */

public class GestionnaireItineraire {
    // La base de données contenant les villes et les trajets
    private Plateforme plateforme;

    public GestionnaireItineraire(Plateforme plateforme) {
        this.plateforme = plateforme;
    }

    public List<Chemin> trouverItineraires(Lieu depart, Lieu arrivee, TypeCout critere, int k) {
        MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

        for (Lieu l : plateforme.getLieux()) {
            graphe.ajouterSommet(l);
        }

        for (Connexion c : plateforme.getConnexions()) {
            if (c instanceof Trajet) {
                Trajet t = (Trajet) c;
                double poids = t.getPoids(critere);
                graphe.ajouterArete(c, poids);
            }
        }

        return AlgorithmeKPCC.kpcc(graphe, depart, arrivee, k);
    }
}
