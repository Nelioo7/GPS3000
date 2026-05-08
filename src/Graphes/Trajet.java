package src.Graphes;

import fr.ulille.but.sae_s2_2026.Lieu; 
import fr.ulille.but.sae_s2_2026.Connexion; 
import fr.ulille.but.sae_s2_2026.ModaliteTransport;

/**
 * Liaison entre deux lieux avec coûts et transport.
 * @author Amayas Mehmel
 * @version 1.1.0
 */

public class Trajet implements Connexion {
    private Lieu depart;
    private Lieu arrive;
    private ModaliteTransport modalite;
    private CoutConnexion couts;

    public Trajet(Lieu depart, Lieu arrive, ModaliteTransport modalite, CoutConnexion couts){
        this.depart = depart;
        this.arrive = arrive;
        this.modalite = modalite;
        this.couts = couts;
    }

    @Override
    public Lieu getDepart() { // renvoie le Lieu officiel de départ
        return this.depart;
    }

    @Override
    public Lieu getArrivee() { // renvoie le Lieu officiel d'arrivée
        return this.arrive;
    }

    @Override
    public ModaliteTransport getModalite() { // renvoie le type de transport
        return this.modalite;
    }

    public CoutConnexion getCouts(){ // renvoie l'ensemble des coûts
        return this.couts;
    }

    public Double getPoids(TypeCout critere) { // renvoie le coût spécifique
        return this.couts.getCout(critere);
    }

    @Override
    public String toString() {
        // affiche sous la forme : "villeA -> villeB (TRAIN)"
        return this.depart.toString() + " -> " + this.arrive.toString() + " (" + this.modalite + ")";
    }
}