package src;

/**
 * Fournit une instance de la classe Voyageur
 * @author Erwann Besegher
 * @version 1.0.0
 */
public class Voyageur {
    
    /** Le nom du voyageur */
    private String nom;
    
    /** Son prénom */
    private String prenom;

    // TODO: ajouter un critère à optimiser

    public Voyageur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;

        //ajouter le critère à optimiser explicitement
    }

    // Constructeur sans le critère donné -> valeur par défaut
    // public Voyageur(String nom, String prenom) {
    //     this(nom, prenom);
    // }

}