package src;

/**
 * Base de données centrale du réseau de transport.
 * @author Erwann Besegher
 * @author Amayas Mehmel
 * @version 1.1.0
 */

import java.util.ArrayList;

public class Plateforme {
    // Liste des villes disponibles
    private ArrayList<Lieu> lieux;
    
    // Liste des trajets disponibles
    private ArrayList<Connexion> connexions;

    public Plateforme(ArrayList<Lieu> lieux, ArrayList<Connexion> connexions) {
        this.lieux = lieux;
        this.connexions = connexions;
    }

    // si on ne fournit pas de listes au départ
    public Plateforme() { 
        this(new ArrayList<Lieu>(), new ArrayList<Connexion>()); // on crée des listes vides par défaut
    }

    // ajoute une ville si elle n'existe pas déjà
    public void ajouterLieu(Lieu l) { 
        if (!lieux.contains(l)) {
            lieux.add(l);
        }
    }

    // ajoute un trajet à la plateforme
    public void ajouterConnexion(Connexion c) { 
        connexions.add(c);
    }

    // renvoie la liste des lieux
    public ArrayList<Lieu> getLieux() { 
        return lieux;
    }

    // renvoie la liste des connexions
    public ArrayList<Connexion> getConnexions() { 
        return connexions;
    }

    // cherche une ville par son nom
    public Lieu trouverLieu(String nom) { 
        for (Lieu l : lieux) {
            if (l instanceof Ville && ((Ville) l).getNom().equalsIgnoreCase(nom)) {
                return l; // renvoie le lieu trouvé
            }
        }
        return null; // renvoie null si aucune ville ne correspond
    }
}