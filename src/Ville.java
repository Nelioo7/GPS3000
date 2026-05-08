package src;

import java.util.Objects;
import fr.ulille.but.sae_s2_2026.Lieu;

/**
 * Représentation d'une ville par son nom.
 * @author Amayas Mehmel
 * @version 1.1.0
 */
public class Ville implements Lieu {
    private String nom;

    public Ville(String nom) {
        this.nom = nom;
    }

    public String getNom() { // renvoie le nom de la ville
        return nom;
    }

    @Override
    public String toString() { // renvoie le nom pour l'affichage
        return nom;
    }

    @Override
    public boolean equals(Object o) { // vérifie l'égalité entre deux villes
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        return Objects.equals(this.nom, ville.getNom());
    }

    @Override
    public int hashCode() { // génère un code de hachage basé sur le nom
        return Objects.hash(nom); 
    }
}