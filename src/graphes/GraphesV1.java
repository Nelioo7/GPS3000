package graphes;

import java.util.List;
import java.util.Map;

import fr.ulille.but.sae_s2_2026.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.Connexion;

/**
 * Version 1 du moteur de calcul de graphe
 * @author Amayas Mehmel
 * @version 1.0.0
 */

/**
 * Moteur de calcul de graphe pur.
 * Cette classe est 100% indépendante du reste du projet (zéro import poo).
 */
public class GraphesV1 {

    /**
     * Calcule les k plus courts chemins à partir d'une liste de sommets 
     * et d'une map associant chaque connexion à son poids numérique.
     */
    public static List<Chemin> calculer(List<Lieu> sommets, Map<Connexion, Double> connectionsValuees, Lieu depart, Lieu arrivee, int k) {
        MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

        for (Lieu l : sommets) {
            graphe.ajouterSommet(l);
        }

        for (Map.Entry<Connexion, Double> entry : connectionsValuees.entrySet()) {
            graphe.ajouterArete(entry.getKey(), entry.getValue());
        }

        return AlgorithmeKPCC.kpcc(graphe, depart, arrivee, k);
    }
}