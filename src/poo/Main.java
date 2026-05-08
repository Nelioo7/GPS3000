package poo;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// Imports de la bibliothèque de graphes
import fr.ulille.but.sae_s2_2026.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.Connexion;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;
import fr.ulille.but.sae_s2_2026.MultiGrapheOrienteValue;

/**
 * Programme principal pour l'assistant de voyage (Partie POO).
 * @author Amayas Mehmel
 * @version 1.4.5
 */
public class Main {

    public static void main(String[] args) {
        // Préparation des données brutes
        ArrayList<String> dataBrutes = new ArrayList<>(Arrays.asList(
            "villeA;villeB;TRAIN;60;1.7;80",
            "villeB;villeD;TRAIN;22;2.4;40",
            "villeA;villeC;TRAIN;42;1.4;50",
            "villeB;villeC;TRAIN;14;1.4;60",
            "villeC;villeD;AVION;110;150;22",
            "villeC;villeD;TRAIN;65;1.2;90"
        ));

        // Récupération des choix utilisateur
        TypeCout critereChoisi = TypeCout.valueOf(args[2].toUpperCase());
        ModaliteTransport modeChoisi = ModaliteTransport.valueOf(args[3].toUpperCase());
        double dureeMax = Double.parseDouble(args[4]);
        Voyageur utilisateur = new Voyageur(args[0], args[1], critereChoisi);

        // Utilisation de la classe TraitementDonnees (Validation & Filtrage)
        if (!TraitementDonnees.verifDatas(dataBrutes)) {
            System.out.println("Erreur : Le format des données sources est invalide.");
            return;
        }

        List<String> dataFiltrees = TraitementDonnees.filtrerDatas(dataBrutes, modeChoisi);

        // Construction du réseau à partir des données filtrées
        Plateforme plateforme = new Plateforme();
        for (String ligne : dataFiltrees) {
            String[] parts = ligne.split(";");
            
            if (parts.length >= 6) {
                String nomDep = parts[0];
                String nomArr = parts[1];
                ModaliteTransport mode = ModaliteTransport.valueOf(parts[2].toUpperCase());
                double prix = Double.parseDouble(parts[3]);
                double co2 = Double.parseDouble(parts[4]);
                double temps = Double.parseDouble(parts[5]);

                Lieu dep = plateforme.trouverLieu(nomDep);
                if (dep == null) {
                    dep = new Ville(nomDep);
                    plateforme.ajouterLieu(dep);
                }

                Lieu arr = plateforme.trouverLieu(nomArr);
                if (arr == null) {
                    arr = new Ville(nomArr);
                    plateforme.ajouterLieu(arr);
                }

                CoutConnexion couts = new CoutConnexion(prix, temps, co2);
                // Graphe orienté : on ajoute dans les deux sens si c'est bidirectionnel
                plateforme.ajouterConnexion(new Trajet(dep, arr, mode, couts));
                plateforme.ajouterConnexion(new Trajet(arr, dep, mode, couts));
            }
        }

        // Calcul et affichage des itinéraires
        System.out.println("Recherche pour : " + utilisateur.getNom() + " " + utilisateur.getPrenom());
        System.out.println("Transport imposé : " + modeChoisi);
        System.out.println("Critère d'optimisation : " + critereChoisi + "\n");

        Lieu depart = plateforme.trouverLieu("villeA");
        Lieu arrivee = plateforme.trouverLieu("villeD");

        if (depart != null && arrivee != null) {
            
            // 1. Création du graphe de calcul
            MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

            // 2. Ajout des sommets
            for (Lieu l : plateforme.getLieux()) {
                graphe.ajouterSommet(l);
            }

            // 3. Ajout des arêtes avec le poids correspondant au critère choisi
            for (Connexion c : plateforme.getConnexions()) {
                if (c instanceof Trajet) {
                    double poids = ((Trajet) c).getPoids(utilisateur.getCritereOpti());
                    graphe.ajouterArete(c, poids);
                }
            }

            // 4. Calcul des 3 meilleurs itinéraires avec la bibliothèque fournie
            List<Chemin> resultats = AlgorithmeKPCC.kpcc(graphe, depart, arrivee, 3);

            int index = 1;
            for (Chemin c : resultats) {
                double tempsTotal = 0;
                for (Connexion a : c.aretes()) {
                    tempsTotal += ((Trajet)a).getPoids(TypeCout.TEMPS);
                }

                // Filtrage final sur la durée maximale
                if (tempsTotal <= dureeMax) {
                    System.out.println(index + ") " + c.toString() + " [Temps : " + (int)tempsTotal + " min]");
                    index++;
                }
            }
            
            if (index == 1) {
                System.out.println("Aucun itinéraire ne correspond à vos critères.");
            }
        } else {
            System.out.println("Erreur : Impossible de trouver les villes de départ et d'arrivée dans le réseau.");
        }
    }
}