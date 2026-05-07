package src;

/**
 * Fournit une instance de la classe Voyageur
 * @author Erwann Besegher
 * @version 1.3.0
 */
public class Voyageur {
    
    // Le nom du voyageur
    private String nom;
    
    // Son prénom
    private String prenom;

    // Critère à optimiser pour le calcul de l'itinéraire
    private TypeCout critereOpti;

    public Voyageur(String nom, String prenom, TypeCout critereOpti) {
        this.nom = nom;
        this.prenom = prenom;
        this.critereOpti = critereOpti;
    }

    public Voyageur(String nom, String prenom) { // si on ne fournit pas de critère
        this(nom, prenom, TypeCout.TEMPS); // par défaut on veut économiser le temps de trajet
    }

    public String getNom() { // renvoie le nom
        return this.nom;
    }

    public String getPrenom() { // renvoie le prenom
        return this.prenom;
    }

    public TypeCout getCritereOpti() { // renvoie le critère à optimiser pour le calcul
        return this.critereOpti;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Voyageur other = (Voyageur) obj;
        if ((!this.nom.equals(other.nom)) && (!this.prenom.equals(other.prenom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s %s -> %s", this.nom, this.prenom, this.critereOpti.toString());
    }
}