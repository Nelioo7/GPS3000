import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import src.CoutConnexion;
import src.TypeCout;

/**
 * Classe de tests pour CoutConnexion.
 * Ce test vérifie le bon fonctionnement du constructeur et de
 * la méthode getCout de la classe CoutConnexion.
 * @author Alaëddine
 * @version 1.1.0
 */
public class TestCoutConnexion {

    // Test du constructeur avec trois valeurs
    @Test
    public void testConstructeurTroisValeurs() {
        CoutConnexion cc = new CoutConnexion(60.0, 80.0, 1.7);
        assertEquals(60.0, cc.getCout(TypeCout.PRIX),"getCout(PRIX) doit retourner 60.0");
        assertEquals(80.0, cc.getCout(TypeCout.TEMPS), "getCout(TEMPS) doit retourner 80.0");
        assertEquals(1.7, cc.getCout(TypeCout.CO2), "getCout(CO2) doit retourner 1.7");
    }
    // Test du constructeur avec une seule valeur
    @Test
    public void testConstructeurUneValeur() {
        CoutConnexion cc = new CoutConnexion(TypeCout.PRIX, 50.0);
        assertEquals(50.0, cc.getCout(TypeCout.PRIX), "getCout(PRIX) doit retourner 50.0");
        assertEquals(null, cc.getCout(TypeCout.TEMPS), "getCout(TEMPS) doit retourner null");
        assertEquals(null, cc.getCout(TypeCout.CO2), "getCout(CO2) doit retourner null");
    }
}
