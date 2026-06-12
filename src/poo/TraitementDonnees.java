package poo;

import fr.ulille.but.sae_s2_2026.ModaliteTransport;

import java.util.ArrayList;


/**
 * Classe de traitement des données
 * Cette classe contient des méthodes pour vérifier la validité des données et pour filtrer les données en fonction d'un mode de transport spécifique
 * 
 * @author Jérôme - Alaëddine Oudriss
 * @version 1.0.0
*/

public class TraitementDonnees {
    
    /**
     * Vérifie que les données sont valides (non nulles, non vides, et que chaque champ est rempli)
     * 
     * Les données sont considérées comme valides si :
     * la liste de données n'est pas nulle
     * aucune ligne de données n'est nulle ou vide
     * chaque champ de chaque ligne de données est rempli (c'est-à-dire qu'il n'est pas vide)
     * 
     * @param data la liste de données à vérifier
     * @return true si les données sont valides, false sinon
     * 
     * @author Jérôme
     * @version 1.0.0
    */
    public static boolean verifDatas(ArrayList<String> data) {
        if (data == null) {
            return false;
        };

        for (String ligne : data) {
            
            if (ligne == null || ligne.equals("")) {
                return false;
            };

            String champ = "";

            for (int i = 0; i < ligne.length(); i++) {
                
                if (ligne.charAt(i) == ';') {
                    if (champ.equals("")) {
                        return false;
                    };
                    champ = "";
                } else {
                    champ += ligne.charAt(i);
                }
            }

            if (champ.equals("")) {
                return false;
            } else if (Integer.parseInt(champ) < 0) {
                return false;
            };
        }
        
        return true;
    }

    /**
     * Filtre les données en fonction d'un mode de transport spécifique
     * 
     * @param data la liste de données à filtrer
     * @param transport le mode de transport à filtrer
     * @return une nouvelle liste de données contenant uniquement les lignes correspondant au mode de transport spéc
     * 
     * @author Jérôme - Erwann Besegher - Alaëddine Oudriss
     * @version 2.0.0
    */
    public static ArrayList<String> filtrerDatas(ArrayList<String> data, ModaliteTransport transport) {
        ArrayList<String> result = new ArrayList<>();

        for (String ligne : data) {
            String champ = "";

            for (int i = 0; i < ligne.length(); i++) {
                if (ligne.charAt(i) == ';') {
                    if (champ.equals(transport.toString().toUpperCase())) {
                        result.add(ligne);
                        break;
                    };
                    champ = "";
                } else {
                    champ += ligne.charAt(i);
                }
            }
        }

        return result;
    }

    //Charge la plateforme à partir du fichier CSV.

    public static poo.Plateforme chargerPlateforme() {
        String cheminFichier = "res/villes.csv";
        ArrayList<String> dataBrutes = new ArrayList<>();
        poo.Plateforme plateforme = new poo.Plateforme();

        try { 
            java.util.Scanner scanner = new java.util.Scanner(new java.io.File(cheminFichier));
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine().trim();
                if (!ligne.isEmpty()) { 
                    dataBrutes.add(ligne.toUpperCase());
                }
            }
            scanner.close();
        } catch (Exception exception) { 
            System.out.println("Erreur lecture CSV : " + exception.getMessage());
            return plateforme;
        }

        if (!verifDatas(dataBrutes)){ 
            return plateforme;
        }

        for (String ligne : dataBrutes) {
            String[] parts = ligne.split(";");
            
            if (parts.length >= 6) {
                String nomDep = parts[0];
                String nomArr = parts[1];
                fr.ulille.but.sae_s2_2026.ModaliteTransport mode = fr.ulille.but.sae_s2_2026.ModaliteTransport.valueOf(parts[2].toUpperCase());
                double prix = Double.parseDouble(parts[3]);
                double co2 = Double.parseDouble(parts[4]);
                double temps = Double.parseDouble(parts[5]);

                fr.ulille.but.sae_s2_2026.Lieu dep = plateforme.trouverLieu(nomDep);
                if (dep == null) {
                    dep = new Ville(nomDep);
                    plateforme.ajouterLieu(dep);
                }

                fr.ulille.but.sae_s2_2026.Lieu arr = plateforme.trouverLieu(nomArr);
                if (arr == null) {
                    arr = new Ville(nomArr);
                    plateforme.ajouterLieu(arr);
                }

                poo.CoutConnexion couts = new poo.CoutConnexion(prix, temps, co2);
                plateforme.ajouterConnexion(new poo.Trajet(dep, arr, mode, couts));
                plateforme.ajouterConnexion(new poo.Trajet(arr, dep, mode, couts));
            }
        }
        return plateforme;
    }

    // cherche si il y a un trajet direct entre deux villes.
     
    public static poo.Trajet trouverTrajetDirect(poo.Plateforme plateforme, fr.ulille.but.sae_s2_2026.Lieu depart, fr.ulille.but.sae_s2_2026.Lieu arrivee) {
        for (fr.ulille.but.sae_s2_2026.Connexion c : plateforme.getConnexions()) {
            if (c.getDepart().equals(depart) && c.getArrivee().equals(arrivee)) {
                return (poo.Trajet) c;
            }
        }
        return null;
    }
}
