package src;

import java.util.ArrayList;

/**
 * Regroupe l'ensemble des connexions et lieux possibles
 * @author Erwann Besegher
 * @version 1.0.0
 */
public class Plateforme {
    ArrayList<Lieu> lieux;
    ArrayList<Connexion> connexions;

    public Plateforme(ArrayList<Lieu> lieux, ArrayList<Connexion> connexions) {
        this.lieux = lieux;
        this.connexions = connexions;
    }

    public Plateforme() {
        this(new ArrayList<Lieu>(), new ArrayList<Connexion>());
    }
}
