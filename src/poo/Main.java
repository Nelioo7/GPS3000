package src.poo;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.Connexion;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;

/**
 * Programme de test pour la Version 1 de l'assistant de voyage.
 * @author Amayas Mehmel
 * @version 1.1.0
 */
public class Main {

    public static void main(String[] args) {
        
        // Vérification des arguments du terminal
        if (args.length < 5) {
            System.out.println("Erreur : Il manque des arguments.");
            System.out.println("Utilisation : java -cp \"bin;lib/*\" src.Main <Nom> <Prenom> <Critere> <Transport> <DureeMax>");
            return;
        }

        // Préparation des données brutes
        ArrayList<String> dataBrutes = new ArrayList<>(Arrays.asList(
            "villeA;villeB;Train;60;1.7;80",
            "villeB;villeD;Train;22;2.4;40",
            "villeA;villeC;Train;42;1.4;50",
            "villeB;villeC;Train;14;1.4;60",
            "villeC;villeD;Avion;110;150;22",
            "villeC;villeD;Train;65;1.2;90"
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
                plateforme.ajouterConnexion(new Trajet(dep, arr, mode, couts));
                plateforme.ajouterConnexion(new Trajet(arr, dep, mode, couts));
            }
        }

        // Calcul et affichage des itinéraires
        System.out.println("Recherche pour : " + utilisateur);
        System.out.println("Transport imposé : " + modeChoisi + "\n");

        GestionnaireItineraire moteur = new GestionnaireItineraire(plateforme);
        Lieu depart = plateforme.trouverLieu("villeA");
        Lieu arrivee = plateforme.trouverLieu("villeD");

        if (depart != null && arrivee != null) {
            List<Chemin> resultats = moteur.trouverItineraires(depart, arrivee, utilisateur, 3);

            int index = 1;
            for (Chemin c : resultats) {
                double tempsTotal = 0;
                for (Connexion a : c.aretes()) {
                    tempsTotal += ((Trajet)a).getPoids(TypeCout.TEMPS);
                }

                if (tempsTotal <= dureeMax) {
                    System.out.println(index + ") " + c.toString() + " [Temps : " + (int)tempsTotal + " min]");
                    index++;
                }
            }
            
            if (index == 1) {
                System.out.println("Aucun itinéraire ne correspond à vos critères.");
            }
        }
    }
}