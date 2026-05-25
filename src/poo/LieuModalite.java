package poo;

import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;

/**
 * Interface de lieu modale
 * @author Amayas Mehmel
 * @version 1.0.0
 */
public class LieuModalite implements Lieu {
    private final Lieu LIEUORIGINE;
    private final ModaliteTransport modalite;

    public LieuModalite(Lieu lieuOrigine, ModaliteTransport modalite) {
        this.LIEUORIGINE = lieuOrigine;
        this.modalite = modalite;
    }

    @Override
    public String toString() {
        return LIEUORIGINE.toString() + "_" + modalite;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LieuModalite that = (LieuModalite) obj;
        return LIEUORIGINE.equals(that.LIEUORIGINE) && modalite == that.modalite;
    }

    @Override
    public int hashCode() {
        return 31 * LIEUORIGINE.hashCode() + modalite.hashCode();
    }
}