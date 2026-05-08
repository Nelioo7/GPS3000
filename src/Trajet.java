package src;

/**
 * Liaison entre deux lieux avec coûts et transport.
 * @author Amayas Mehmel
 * @version 1.0.0
 */

public class Trajet implements Connexion {
    // Le lieu de départ
    private Lieu depart;

    // Le lieu d'arrivée
    private Lieu arrive;

    // Le mode de transport
    private ModaliteTransport modalite;

    // Les différents coûts associés (Prix, Temps, CO2)
    private CoutConnexion couts;


    public Trajet(Lieu depart, Lieu arrive, ModaliteTransport modalite, CoutConnexion couts){
        this.depart = depart;
        this.arrive = arrive;
        this.modalite = modalite;
        this.couts = couts;
    }

    // renvoie le point de départ de la connexion
    @Override
    public Lieu getDepart() { 
        return this.depart;
    }

    // renvoie le point d'arrivée de la connexion
    @Override
    public Lieu getArrivee() { 
        return this.arrive;
    }

    // renvoie le type de transport utilisé
    @Override
    public ModaliteTransport getModalite() { 
        return this.modalite;
    }

    // renvoie l'ensemble des coûts
    public CoutConnexion getCouts(){ 
        return this.couts;
    }
    
    // renvoie le coût spécifique selon le critère choisi
    public Double getPoids(TypeCout critere) { 
        return this.couts.getCout(critere);
    }


}
