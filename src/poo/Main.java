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
 * @author Amayas Mehmel 
 * @author Erwann Besegher (scanner & import csv & docs)
 * @author Jérôme Pardoue (itinerairenNotFoundException)
 * @author Alaëddine Oudriss (Historique)
 * @version 3.0.0
 */

public class Main {
    public static void main(String[] args) {
        if (args.length < 7) {
            System.out.println("Erreur : Il manque des arguments.");
            System.out.println("Utilisation : java -cp \"bin;lib/*\" poo.Main <Nom> <Prenom> <Critere> <Transport> <DureeMax> <VilleDepart> <VilleArrivee>");
            return;
        }
    
        String cheminHistorique = "res/historique"; // fichier de sauvegarde de l'historique
        Historique monHistorique;
        
        try {
            File f = new File(cheminHistorique);
            if (f.exists()) {
                monHistorique = Historique.charger(cheminHistorique);
                System.out.println("Historique charge avec succes.");
            } else {
                monHistorique = new Historique();
                System.out.println("Premier lancement : Creation d'un nouvel historique.");
            }
        } catch (Exception e) {
            monHistorique = new Historique();
            System.out.println("Erreur de chargement, création d'un historique vide.");
        }

    String cheminFichier = "res/reseau.csv";
    String cheminCorrespondances = "res/correspondances.csv";

    // on importe le fichier avec son chemin
    File fichierCSV = new File(cheminFichier);
    File fichierCorresCSV = new File(cheminCorrespondances);

    // on initialise l'ArrayList qui va nous servir à stocker les données
    ArrayList<String> dataBrutes = new ArrayList<>();
    ArrayList<String> corresBrutes = new ArrayList<>();

    // on initialise un scanner pour parcourir le fichier CSV
    try { 
        Scanner scanner = new Scanner(fichierCSV);
        // On utilise hasNextLine() pour lire ligne par ligne
        while (scanner.hasNextLine()) {
            String ligne = scanner.nextLine().trim();
            if (!ligne.isEmpty()) { 
                // on les ajoute dans l'ArrayList 
                dataBrutes.add(ligne.toUpperCase());
            }
        }
        scanner.close();
        // on ferme le scanner
    } catch (FileNotFoundException fileNotFoundException) { // si le fichier n'est pas trouvé on affiche un message + la stackTrace
        System.out.println("Le fichier " + cheminFichier + " n'a pas été trouvé.");
        fileNotFoundException.printStackTrace();
    } catch (Exception exception) { // si il s'agit d'une autre erreur on affiche la stackTrace
        exception.printStackTrace();
    }

    // Lecture du fichier des correspondances pour la V2
    try {
        Scanner scannerCorres = new Scanner(fichierCorresCSV);
        while (scannerCorres.hasNextLine()) {
            String ligne = scannerCorres.nextLine().trim();
            if (!ligne.isEmpty()) {
                corresBrutes.add(ligne.toUpperCase());
            }
        }
        scannerCorres.close();
    } catch (FileNotFoundException e) {
        System.out.println("Le fichier " + cheminCorrespondances + " n'a pas été trouvé.");
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

    // On ne filtre plus par un seul mode pour permettre le multimodal
    List<String> dataFiltrees = dataBrutes;

    // Construction du réseau à partir des données filtrées
    Plateforme plateforme = new Plateforme();
    MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

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
            // La plateforme POO continue d'utiliser tes vrais objets Ville/Lieu de la V1
            plateforme.ajouterConnexion(new Trajet(dep, arr, mode, couts));
            plateforme.ajouterConnexion(new Trajet(arr, dep, mode, couts));

            // V2 : Création et insertion des sommets éclatés (Lieu_Modalite) dans le graphe
            LieuModalite sommetDep = new LieuModalite(dep, mode);
            LieuModalite sommetArr = new LieuModalite(arr, mode);

            graphe.ajouterSommet(sommetDep);
            graphe.ajouterSommet(sommetArr);

            TransportArete axeAller = new TransportArete(sommetDep, sommetArr, mode, prix, temps, co2);
            TransportArete axeRetour = new TransportArete(sommetArr, sommetDep, mode, prix, temps, co2);

            graphe.ajouterArete(axeAller, axeAller.getPoids(utilisateur.getCritereOpti()));
            graphe.ajouterArete(axeRetour, axeRetour.getPoids(utilisateur.getCritereOpti()));
        }
    }

    // Traitement des correspondances lues et injection des arêtes de transfert inter-modalités
    for (String ligneCorres : corresBrutes) {
        String[] parts = ligneCorres.split(";");
        if (parts.length >= 6) {
            String nomVille = parts[0];
            ModaliteTransport modeDep = ModaliteTransport.valueOf(parts[1].toUpperCase());
            ModaliteTransport modeArr = ModaliteTransport.valueOf(parts[2].toUpperCase());
            double prixCorres = Double.parseDouble(parts[3]);
            double co2Corres = Double.parseDouble(parts[4]);
            double tempsCorres = Double.parseDouble(parts[5]);

            Lieu ville = plateforme.trouverLieu(nomVille);
            if (ville != null) {
                LieuModalite source = new LieuModalite(ville, modeDep);
                LieuModalite cible = new LieuModalite(ville, modeArr);

                graphe.ajouterSommet(source);
                graphe.ajouterSommet(cible);

                CorrespondanceArete lienCorres = new CorrespondanceArete(source, cible, modeArr, prixCorres, tempsCorres, co2Corres);
                graphe.ajouterArete(lienCorres, lienCorres.getPoids(utilisateur.getCritereOpti()));
            }
        }
    }

    // calcul et affichage des itinéraires
    System.out.println("Recherche pour : " + utilisateur.getNom() + " " + utilisateur.getPrenom());
    System.out.println("Transport imposé : " + modeChoisi);
    System.out.println("Critère d'optimisation : " + critereChoisi + "\n");

    Lieu departBase = plateforme.trouverLieu(args[5].toUpperCase());
    Lieu arriveeBase = plateforme.trouverLieu(args[6].toUpperCase());

    Lieu depart = null;
    if (departBase != null) {
        depart = new LieuModalite(departBase, modeChoisi);
    }

    Lieu arrivee = null;
    if (arriveeBase != null) {
        arrivee = new LieuModalite(arriveeBase, modeChoisi);
    }

    if (depart != null && arrivee != null) {
    try {
        // Calcul des 3 meilleurs itinéraires avec la bibliothèque fournie
        List<Chemin> resultats = AlgorithmeKPCC.kpcc(graphe, depart, arrivee, 3);
        int index = 1;

        for (Chemin c : resultats) {
            double tempsTotal = 0;
            for (Connexion a : c.aretes()) {
                if (a instanceof TransportArete) {
                    tempsTotal += ((TransportArete) a).getPoids(TypeCout.TEMPS);
                }
                if (a instanceof CorrespondanceArete) {
                    tempsTotal += ((CorrespondanceArete) a).getPoids(TypeCout.TEMPS);
                }
            }

            // Filtrage final sur la durée maximale et conversion en heures/minutes
            if (tempsTotal <= dureeMax) {
                int totalMinutes = (int) tempsTotal;
                int heures = totalMinutes / 60;
                int minutes = totalMinutes % 60;

                String affichageTemps = "";
                if (heures > 0) {
                    affichageTemps = heures + "h";
                    if (minutes > 0) {
                        affichageTemps = affichageTemps + minutes;
                    }
                }
                if (heures == 0) {
                    affichageTemps = minutes + " min";
                }

                System.out.println(index + ") " + c.toString() + " [Temps : " + affichageTemps + "]");
                index++;
            }
        }

        if (index == 1) {
            System.out.println("Aucun itinéraire ne correspond à vos critères.");
        }
        } catch (IllegalArgumentException e) {
            System.out.println("Aucun itinéraire trouvé : La ville de départ ou d'arrivée n'est pas desservie par le transport imposé (" + modeChoisi + ").");
        }

        // Scénario d'utilisation -- Pardoue Jérôme
        GestionnaireItineraire gestionnaire = new GestionnaireItineraire(plateforme);

        Voyageur alice = new Voyageur("Dupont", "Alice", TypeCout.PRIX);
        Voyageur bob = new Voyageur("Martin", "Bob", TypeCout.TEMPS);
        Voyageur camille = new Voyageur("Leroy", "Camille", TypeCout.CO2);

        try {
            List<Chemin> trajet1 = gestionnaire.trouverItineraires(plateforme.trouverLieu("D"), plateforme.trouverLieu("A"), alice.getCritereOpti(), 3);
            List<Chemin> trajet2 = gestionnaire.trouverItineraires(plateforme.trouverLieu("L"), plateforme.trouverLieu("B"), bob.getCritereOpti(), 3);
            List<Chemin> trajet3 = gestionnaire.trouverItineraires(plateforme.trouverLieu("D"), plateforme.trouverLieu("G"), camille.getCritereOpti(), 3);

            System.out.println("Alice (PRIX) : " + trajet1);
            System.out.println("Bob (TEMPS) : " + trajet2);
            System.out.println("Camille (CO2) : " + trajet3);
        } catch (VoyageImpossibleException e) {
            System.out.println(e.getMessage());
        }

        } else {
            System.out.println("Erreur : Impossible de trouver les villes de départ et d'arrivée spécifiées avec ce mode de transport.");
        }

        String villeDep = args[5].toUpperCase();
        String villeArr = args[6].toUpperCase();
        monHistorique.ajouterRecherche(villeDep + " -> " + villeArr);

        try {
            monHistorique.sauvegarder(cheminHistorique);
            System.out.println("\n--- Historique des recherches ---");
            monHistorique.afficherHistorique();
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

}
