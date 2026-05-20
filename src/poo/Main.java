package poo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Imports de la bibliothèque de graphes
import fr.ulille.but.sae_s2_2026.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.Connexion;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;
import fr.ulille.but.sae_s2_2026.MultiGrapheOrienteValue;
/**
 * Programme principal pour l'assistant de voyage
 * @author Amayas Mehmel - Erwann Besegher - Jérôme Pardoue
 * @version 2.0.0
 */


public class Main {

    public static void main(String[] args) {
        if (args.length < 7) {
            System.out.println("Erreur : Il manque des arguments.");
            System.out.println("Utilisation : java -cp \"bin;lib/*\" poo.Main <Nom> <Prenom> <Critere> <Transport> <DureeMax> <VilleDepart> <VilleArrivee>");
            return;
        }

        // Préparation des données brutes
        // ArrayList<String> dataBrutes = new ArrayList<>(Arrays.asList(
        //     "villeA;villeB;TRAIN;60;1.7;80",
        //     "villeB;villeD;TRAIN;22;2.4;40",
        //     "villeA;villeC;TRAIN;42;1.4;50",
      
        //     "villeB;villeC;TRAIN;14;1.4;60",
        //     "villeC;villeD;AVION;110;150;22",
        //     "villeC;villeD;TRAIN;65;1.2;90"
        // ));
String cheminFichier = ("res/exemple-reseau.csv");

        // on importe le fichier avec son chemin
        File fichierCSV = new File(cheminFichier);
        // on initialise l'ArrayList qui va nous servir à stocker les données
        ArrayList<String> dataBrutes = new ArrayList<>();
        // on initialise un scanner pour parcourir le fichier CSV
        try { 
            Scanner scanner = new Scanner(fichierCSV);
            // tant que le scanner possède des lignes
            while(scanner.hasNext()) {
                // CORRECTION ICI : On force la ligne en MAJUSCULES dès la lecture pour que le filtre fonctionne !
                dataBrutes.add(scanner.next().toUpperCase());
            // on les ajoute dans l'ArrayList
            }
            scanner.close();
            // on ferme le scanner

        } catch (FileNotFoundException fileNotFoundException) { // si le fichier n'est pas trouvé on affiche un message + la stackTrace
            System.out.println("Le fichier " + cheminFichier + " n'a pas été trouvé.");
            fileNotFoundException.printStackTrace();

        } catch (Exception exception) { // si il s'agit d'une autre erreur on affiche la stackTrace
            exception.printStackTrace();
        }

        // Récupération des choix utilisateur
        TypeCout critereChoisi = TypeCout.valueOf(args[2].toUpperCase());
        ModaliteTransport modeChoisi = ModaliteTransport.valueOf(args[3].toUpperCase());
        double dureeMax = Double.parseDouble(args[4]);
        Voyageur utilisateur = new Voyageur(args[0], args[1], critereChoisi);
        // Utilisation de la classe TraitementDonnees (Validation & Filtrage)
        if (!TraitementDonnees.verifDatas(dataBrutes)) {
            System.out.println("Erreur: Le format des données sources est invalide.");
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

        Lieu depart = plateforme.trouverLieu(args[5].toUpperCase());
        Lieu arrivee = plateforme.trouverLieu(args[6].toUpperCase());

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

            GestionnaireItineraire gestionnaire = new GestionnaireItineraire(plateforme);

            try {
                gestionnaire.trouverItineraires(depart, arrivee, critereChoisi, 3);
            } catch(VoyageImpossibleException e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("Erreur : Impossible de trouver les villes de départ et d'arrivée dans le réseau.");
        }
    }
}
