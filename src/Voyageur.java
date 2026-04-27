package src;

/**
 * Fournit une instance de la classe Voyageur
 * @author Erwann Besegher
 * @version 1.1.0
 */
public class Voyageur {
    
    /** Le nom du voyageur */
    private String nom;
    
    /** Son prénom */
    private String prenom;

    /** Critère à optimiser pour le calcul de l'itinéraire */
    private TypeCout critereOpti;

    public Voyageur(String nom, String prenom, TypeCout critereOpti) {
        this.nom = nom;
        this.prenom = prenom;
        this.critereOpti = critereOpti;
    }

    public Voyageur(String nom, String prenom) { // si on ne fournit pas de critère
        this(nom, prenom, TypeCout.TEMPS); // par défaut on veut économiser le temps de trajet
    }

}