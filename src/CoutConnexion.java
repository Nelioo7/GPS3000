package src;

import java.util.HashMap;
import java.util.Map;

/**
 * Objet qui fournit une association entre un type de cout et un cout
 * @author Erwann Besegher
 * @version 1.0.0
 */
public class CoutConnexion {
    private Map<TypeCout, Double> coutConnexion; // table d'association type de cout et cout

    public CoutConnexion(TypeCout typeDeCout, Double cout) {
        this.coutConnexion = new HashMap<TypeCout, Double>(); // création de la table

        this.coutConnexion.put(typeDeCout, cout);
    }

    public CoutConnexion() {
        this.coutConnexion = new HashMap<TypeCout, Double>();
    }

    public Double getCout(TypeCout typeDeCout) {
        return this.coutConnexion.get(typeDeCout);
    }
}
