package src;

import java.util.HashMap;
import java.util.Map;

/**
 * Objet qui fournit une association entre un type de cout et un cout
 * @author Erwann Besegher
 * @author Amayas Mehmel
 * @version 1.1.0
 */
public class CoutConnexion {
    private Map<TypeCout, Double> coutConnexion; // table d'association type de cout et cout

    public CoutConnexion(Double prix, Double temps, Double co2) {
        this.coutConnexion = new HashMap<TypeCout, Double>(); // création de la table

        // on remplit la Map directement avec les 3 valeurs
        this.coutConnexion.put(TypeCout.PRIX, prix);
        this.coutConnexion.put(TypeCout.TEMPS, temps);
        this.coutConnexion.put(TypeCout.CO2, co2);
    }

     public CoutConnexion(TypeCout typeDeCout, Double cout) {
        this.coutConnexion = new HashMap<TypeCout, Double>(); // création de la table
        this.coutConnexion.put(typeDeCout, cout); // ajout du premier coût
    }

    public Double getCout(TypeCout typeDeCout) {
        return this.coutConnexion.get(typeDeCout);
    }


}
