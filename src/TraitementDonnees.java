package src;

import java.util.ArrayList;

public class TraitementDonnees {
    
    /**
     * Vérifie que les données sont valides (non nulles, non vides, et que chaque champ est rempli)
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
            };
        }
        
        return true;
    }

    /**
     * Filtre les données en fonction d'un mode de transport spécifique
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
