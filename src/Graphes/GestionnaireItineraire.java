package src.Graphes;

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

    public List<Chemin> trouverItineraires(Lieu depart, Lieu arrivee, Voyageur v, int k) {
        // On crée un nouveau multigraphe vide
        MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

        // On récupère le critère que le voyageur veut optimiser (Prix, Temps ou CO2)
        TypeCout critere = v.getCritereOpti();

        // On ajoute tous les sommets (villes) au graphe
        for (Lieu l : plateforme.getLieux()) {
            graphe.ajouterSommet(l);
        }

        // On ajoute les arêtes avec le poids correspondant au critère du voyageur
        for (Connexion c : plateforme.getConnexions()) {
            // On transforme la connexion en Trajet pour accéder à getPoids
            Trajet t = (Trajet) c;
            double poids = t.getPoids(critere);
            
            // On ajoute l'arête au graphe (la librairie gère l'orientation)
            graphe.ajouterArete(t, poids);
        }

        // On lance l'algorithme KPCC pour trouver les k meilleurs chemins
        return AlgorithmeKPCC.kpcc(graphe, depart, arrivee, k);
    }
}
