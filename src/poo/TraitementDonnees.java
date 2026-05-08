package src.poo;


import fr.ulille.but.sae_s2_2026.ModaliteTransport;

import java.util.ArrayList;


/**
 * Classe de traitement des données
 * Cette classe contient des méthodes pour vérifier la validité des données et pour filtrer les données en fonction d'un mode de transport spécifique
 * 
 * @author Jérôme
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
     * @author Jérôme
     * @version 1.0.0
    */
    public static ArrayList<String> filtrerDatas(ArrayList<String> data, ModaliteTransport transport) {
        ArrayList<String> result = new ArrayList<>();

        for (String ligne : data) {
            String champ = "";

            for (int i = 0; i < ligne.length(); i++) {
                if (ligne.charAt(i) == ';') {
                    if (champ.equals(transport.toString())) {
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
}
