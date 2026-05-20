package poo;

/**
 * Exception levée quand aucun itinéraire n'existe entre deux lieux.
 * Utilisée dans GestionnaireItineraire.trouverItineraires()
 * @author Alaeddine
 * @version 1.0.0
 */

public class VoyageImpossibleException extends Exception {
    // on stocke les noms des 2 villes concernées pour le message d'erreur
    private String villeDepart;
    private String villeArrivee;

    public VoyageImpossibleException(String villeDepart, String villeArrivee) {
        super("Aucun itinéraire possible entre " + villeDepart + " et " + villeArrivee + ".");
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
    }

    public String getVilleDepart()  {
        return villeDepart;
    }
    
    public String getVilleArrivee() { 
        return villeArrivee; 
    }
    
}
