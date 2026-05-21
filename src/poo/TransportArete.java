package poo;
    
import fr.ulille.but.sae_s2_2026.Connexion;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;

/**
 * Gere les differents moyen de transport en parallele
 * @author Amayas Mehmel
 * @version 1.0.0
 */

public class TransportArete implements Connexion {

    private final Lieu depart;
    private final Lieu arrivee;
    private final ModaliteTransport modalite;
    private final double prix;
    private final double temps;
    private final double co2;

    public TransportArete(Lieu depart, Lieu arrivee, ModaliteTransport modalite, double prix, double temps, double co2) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.modalite = modalite;
        this.prix = prix;
        this.temps = temps;
        this.co2 = co2;
    }

    @Override
    public Lieu getDepart() {
        return depart;
    }

    @Override
    public Lieu getArrivee() {
        return arrivee;
    }

    @Override
    public ModaliteTransport getModalite() {
        return modalite;
    }

    public double getPoids(TypeCout critere) {
        if (critere == TypeCout.PRIX) {
            return prix;
        }
        if (critere == TypeCout.CO2) {
            return co2;
        }
        if (critere == TypeCout.TEMPS) {
            return temps;
        }
        return 0;
    }

    @Override
    public String toString() {
        return depart.toString() + " -> " + arrivee.toString() + " (" + modalite + ")";
    }
}