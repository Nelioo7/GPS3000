package src;
/**
 * Description
 * @author Amayas Mehmel
 * @version 1.0.0
 */
import java.util.Objects;

public class Ville implements Lieu {
    // Le nom de la ville
    private String nom;

    public Ville(String nom) {
        this.nom = nom;
    }

    // renvoie le nom de la ville
    public String getNom() { 
        return nom;
    }

    // renvoie le nom pour l'affichage
    @Override
    public String toString() { 
        return nom;
    }

    // vérifie l'égalité entre deux villes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        if(ville.getNom() == null || this.nom == null){return false;}
        return Objects.equals(this.nom, ville.nom);
    }

    // génère un code de hachage basé sur le nom
    @Override
    public int hashCode() {
        return Objects.hash(nom); 
    }
}

