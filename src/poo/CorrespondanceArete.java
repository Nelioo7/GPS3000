package poo;

import fr.ulille.but.sae_s2_2026.Connexion;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;

/**
 * Gere les aretes de correspondances
 * @author Amayas Mehmel
 * @version 1.0.0
 */

public class CorrespondanceArete implements Connexion {

    private final Lieu DEPART;
    private final Lieu ARRIVEE;
    private final ModaliteTransport MODALITE;
    private final double PRIX;
    private final double TEMPS;
    private final double CO2;

    public CorrespondanceArete(Lieu depart, Lieu arrivee, ModaliteTransport modalite, double prix, double temps, double co2) {
        this.DEPART = depart;
        this.ARRIVEE = arrivee;
        this.MODALITE = modalite;
        this.PRIX = prix;
        this.TEMPS = temps;
        this.CO2 = co2;
    }

    @Override
    public Lieu getDepart() {
        return DEPART;
    }

    @Override
    public Lieu getArrivee() {
        return ARRIVEE;
    }

    @Override
    public ModaliteTransport getModalite() {
        return MODALITE;
    }

    public double getPoids(TypeCout critere) {
        if (critere == TypeCout.PRIX) {
            return PRIX;
        }
        if (critere == TypeCout.CO2) {
            return CO2;
        }
        if (critere == TypeCout.TEMPS) {
            return TEMPS;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "[Correspondance] " + DEPART.toString() + " -> " + ARRIVEE.toString();
    }
}