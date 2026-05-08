package poo;

import fr.ulille.but.sae_s2_2026.Lieu;

/**
 * Interface qui fournit le point de départ, celui d'arrivée et sa modalité de transport
 * @author Erwann Besegher
 * @version 1.0.0
 */
public interface Connexion {
    
    public Lieu getDepart(); // renvoie le point de départ de la connexion
    public Lieu getArrivee(); // renvoie le point d'arrivée de la connexion
    public ModaliteTransport getModalite(); // renvoie le type de transport utilisé
}
