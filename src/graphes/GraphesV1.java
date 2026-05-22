package graphes;

import java.util.List;

import fr.ulille.but.sae_s2_2026.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.Connexion;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;
import fr.ulille.but.sae_s2_2026.MultiGrapheOrienteValue;

/**
 * Prototype exécutable pour la Version 1 partie graphes.
 * Scénario international (Lille -> Sydney).
 * @author Amayas Mehmel
 * @version 2.0.0 (remplacement import par classes internes)
 */
public class GraphesV1 {


    private static class Ville implements Lieu {
        private final String nom;

        public Ville(String nom) {
            this.nom = nom;
        }

        @Override
        public String toString() {
            return nom;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Ville that = (Ville) obj;
            return nom.equals(that.nom);
        }

        @Override
        public int hashCode() {
            return nom.hashCode();
        }
    }

    private static class Trajet implements Connexion {
        private final Lieu depart;
        private final Lieu arrivee;
        private final ModaliteTransport mode;
        private final double prix;
        private final double temps;
        private final double co2;

        public Trajet(Lieu depart, Lieu arrivee, ModaliteTransport mode, double prix, double temps, double co2) {
            this.depart = depart;
            this.arrivee = arrivee;
            this.mode = mode;
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
            return mode;
        }

        public double getPoids(String critere) {
            if (critere.equals("PRIX")) {
                return prix;
            }
            if (critere.equals("TEMPS")) {
                return temps;
            }
            if (critere.equals("CO2")) {
                return co2;
            }
            return 0;
        }

        @Override
        public String toString() {
            return depart.toString() + " -> " + arrivee.toString() + " (" + mode + ")";
        }
    }


    public static void main(String[] args) {
        System.out.println("=== PROTOTYPE GRAPHE V1 ===\n");

        MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

        // création des 6 villes (Sommets)
        Ville lille = new Ville("Lille");
        Ville paris = new Ville("Paris");
        Ville jakarta = new Ville("Jakarta");
        Ville newYork = new Ville("New York");
        Ville sydney = new Ville("Sydney");
        Ville tokyo = new Ville("Tokyo");

        graphe.ajouterSommet(lille);
        graphe.ajouterSommet(paris);
        graphe.ajouterSommet(jakarta);
        graphe.ajouterSommet(newYork);
        graphe.ajouterSommet(sydney);
        graphe.ajouterSommet(tokyo);

        // Préférences de l'utilisatrice
        ModaliteTransport modeChoisi = ModaliteTransport.AVION;
        String critereOptimisation = "PRIX"; 

        System.out.println("Préférences de l'utilisatrice :");
        System.out.println("- Transport choisi : " + modeChoisi);
        System.out.println("- Critère à optimiser : " + critereOptimisation);
        System.out.println("- Trajet : Lille -> Sydney\n");

        // tableau des connexions avec les 3 valeurs directes (prix, temps, co2)
        Trajet[] reseau = {
            // TRAINS
            new Trajet(lille, paris, ModaliteTransport.TRAIN, 35.0, 60.0, 1.7),

            // AVIONS
            new Trajet(lille, jakarta, ModaliteTransport.AVION, 550.0, 780.0, 280.0),
            new Trajet(lille, newYork, ModaliteTransport.AVION, 820.0, 1140.0, 440.0),
            new Trajet(jakarta, sydney, ModaliteTransport.AVION, 310.0, 420.0, 155.0),
            new Trajet(jakarta, newYork, ModaliteTransport.AVION, 820.0, 1140.0, 440.0),
            new Trajet(paris, sydney, ModaliteTransport.AVION, 900.0, 1320.0, 405.0),
            new Trajet(paris, jakarta, ModaliteTransport.AVION, 700.0, 810.0, 310.0),
            new Trajet(paris, tokyo, ModaliteTransport.AVION, 620.0, 780.0, 290.0),
            new Trajet(sydney, tokyo, ModaliteTransport.AVION, 480.0, 600.0, 215.0),
            new Trajet(newYork, tokyo, ModaliteTransport.AVION, 520.0, 840.0, 310.0),
            new Trajet(newYork, sydney, ModaliteTransport.AVION, 950.0, 1260.0, 310.0),

            // BUS
            new Trajet(lille, paris, ModaliteTransport.BUS, 15.0, 180.0, 5.0)
        };

        // remplissage du graphe
        for (Trajet t : reseau) {
            if (t.getModalite() == modeChoisi) {
                graphe.ajouterArete(t, t.getPoids(critereOptimisation));
            }
        }

        // On cherche de Lille à Sydney et on demande k=4
        List<Chemin> resultats = AlgorithmeKPCC.kpcc(graphe, lille, sydney, 4);

        // Affichage des 4 itinéraires
        System.out.println("=== 4 MEILLEURS ITINÉRAIRES ===");
        int i = 1;
        for (Chemin c : resultats) {
            System.out.println(i + ") " + c.toString() + " [Coût total (" + critereOptimisation + ") : " + c.poids() + " €]");
            i++;
        }
        
        if (resultats.isEmpty()) {
            System.out.println("Aucun itinéraire trouvé.");
        }
    }
}