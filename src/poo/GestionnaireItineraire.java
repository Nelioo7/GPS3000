package poo;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2026.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;
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

    public List<Chemin> trouverItineraires(Lieu depart, Lieu arrivee, TypeCout critere, int k) 
    //  AJOUTÉ : déclaration de l'exception
        throws VoyageImpossibleException { 

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

        List<Chemin> resultats = AlgorithmeKPCC.kpcc(graphe, depart, arrivee, k);

        //si la liste est vide, aucun chemin n'existe
        if (resultats.isEmpty()) {
            throw new VoyageImpossibleException(depart.toString(), arrivee.toString()); // On lance l'exception
        }
        return resultats;
    }


    //Ajout: méthode getPointsInteret

    /* Retourne la liste des villes "importantes" d'un chemin :
    --ville de départ -- chaque ville où le mode de transport CHANGE -- ville d'arrivée.
    */
    public List<Lieu> getPointsInteret(Chemin chemin) {
        
        List<Lieu> pointsInteret = new ArrayList<>();
        List<Connexion> arretes = chemin.aretes();

        if (arretes.isEmpty()) {
            return pointsInteret;
        }
        //ville de départ
        pointsInteret.add(arretes.get(0).getDepart());
        
        // On compare chaque trajet avec le suivant
        for (int i = 0; i < arretes.size() -1; i++) {
            
            Trajet trajetActuel = (Trajet) arretes.get(i);
            Trajet trajetSuivant = (Trajet) arretes.get(i + 1);

            ModaliteTransport modeActuel = trajetActuel.getModalite();
            ModaliteTransport modeSuivant = trajetSuivant.getModalite();

            if (!modeActuel.equals(modeSuivant)) {
                pointsInteret.add(trajetActuel.getArrivee());
            }
        }
        //on ajoute la ville d'arrivée au dernier trajet .
        pointsInteret.add(arretes.get(arretes.size() - 1).getArrivee());

        return pointsInteret;
    }

    
    // Ajout: méthode afficherPointsInteret pour afficher le résultat dans la console.
    public void afficherPointsInteret(Chemin chemin) {
        List<Lieu> points = getPointsInteret(chemin);

        StringBuilder sb = new StringBuilder("Points d'intérêt : ");
        for (int i = 0; i < points.size(); i++) {
            sb.append(points.get(i).toString());
            if (i < points.size() - 1) {
                sb.append(" → ");
            }
        }

        System.out.println(sb.toString());
    }
}
