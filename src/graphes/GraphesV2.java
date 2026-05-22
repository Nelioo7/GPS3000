package graphes;

import java.util.List;

import fr.ulille.but.sae_s2_2026.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2026.Chemin;
import fr.ulille.but.sae_s2_2026.Connexion;
import fr.ulille.but.sae_s2_2026.Lieu;
import fr.ulille.but.sae_s2_2026.ModaliteTransport;
import fr.ulille.but.sae_s2_2026.MultiGrapheOrienteValue;

/**
 * Prototype indépendant pour la Version 2 (Modélisation multimodale)
 * @author Mehmel Amayas 
 * @author Besegher Erwann (commentaires)
 * @version 1.0
 */
public class GraphesV2 {

    // 1. Implémentation interne Lieu (éclatement des sommets)
    private static class SommetEclate implements Lieu {
        private final String nomVille;
        private final ModaliteTransport mode;

        public SommetEclate(String nomVille, ModaliteTransport mode) {
            this.nomVille = nomVille;
            this.mode = mode;
        }

        @Override
        public String toString() {
            return nomVille + "_" + mode;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SommetEclate that = (SommetEclate) obj;
            return nomVille.equals(that.nomVille) && mode == that.mode;
        }

        @Override
        public int hashCode() {
            return 31 * nomVille.hashCode() + mode.hashCode();
        }
    }

    // 2. Implémentation interne de Connexion
    private static class LienReseau implements Connexion {
        private final Lieu depart;
        private final Lieu arrivee;
        private final ModaliteTransport mode;
        private final double poids; // Représente le critère choisi (ex: Temps)
        private final boolean estCorrespondance;

        public LienReseau(Lieu depart, Lieu arrivee, ModaliteTransport mode, double poids, boolean estCorrespondance) {
            this.depart = depart;
            this.arrivee = arrivee;
            this.mode = mode;
            this.poids = poids;
            this.estCorrespondance = estCorrespondance;
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

        public double getPoids() {
            return poids;
        }

        @Override
        public String toString() {
            if (estCorrespondance) {
                return "[Correspondance] " + depart + " -> " + arrivee;
            }
            return depart + " -> " + arrivee + " (" + mode + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Démonstration Graphes V2 (Scénario Multimodal) ---");
        System.out.println("Critère d'optimisation : TEMPS\n");

        MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();

        // Création manuelle des sommets éclatés pour l'exemple (5 Villes : A, B, C, D, E)
        SommetEclate aTrain = new SommetEclate("A", ModaliteTransport.TRAIN);
        SommetEclate aBus = new SommetEclate("A", ModaliteTransport.BUS);
        SommetEclate aAvion = new SommetEclate("A", ModaliteTransport.AVION);

        SommetEclate bTrain = new SommetEclate("B", ModaliteTransport.TRAIN);
        SommetEclate bBus = new SommetEclate("B", ModaliteTransport.BUS);

        SommetEclate cTrain = new SommetEclate("C", ModaliteTransport.TRAIN);
        SommetEclate cBus = new SommetEclate("C", ModaliteTransport.BUS);

        SommetEclate dTrain = new SommetEclate("D", ModaliteTransport.TRAIN);
        SommetEclate dBus = new SommetEclate("D", ModaliteTransport.BUS);
        SommetEclate dAvion = new SommetEclate("D", ModaliteTransport.AVION);

        SommetEclate eTrain = new SommetEclate("E", ModaliteTransport.TRAIN);
        SommetEclate eBus = new SommetEclate("E", ModaliteTransport.BUS);

        // Ajout des sommets au graphe
        Lieu[] tousLesSommets = {aTrain, aBus, aAvion, bTrain, bBus, cTrain, cBus, dTrain, dBus, dAvion, eTrain, eBus};
        for (Lieu sommet : tousLesSommets) {
            graphe.ajouterSommet(sommet);
        }

        // Ajout des arêtes de transport (Temps en minutes)
        LienReseau t1 = new LienReseau(aTrain, bTrain, ModaliteTransport.TRAIN, 60.0, false);
        LienReseau t2 = new LienReseau(bTrain, cTrain, ModaliteTransport.TRAIN, 90.0, false);
        LienReseau t3 = new LienReseau(aBus, cBus, ModaliteTransport.BUS, 180.0, false);
        LienReseau t4 = new LienReseau(cTrain, dTrain, ModaliteTransport.TRAIN, 75.0, false);
        LienReseau t5 = new LienReseau(aAvion, dAvion, ModaliteTransport.AVION, 45.0, false);
        LienReseau t6 = new LienReseau(dBus, eBus, ModaliteTransport.BUS, 50.0, false);
        LienReseau t7 = new LienReseau(cTrain, eTrain, ModaliteTransport.TRAIN, 110.0, false);
        LienReseau t8 = new LienReseau(bBus, dBus, ModaliteTransport.BUS, 120.0, false);

        graphe.ajouterArete(t1, t1.getPoids());
        graphe.ajouterArete(t2, t2.getPoids());
        graphe.ajouterArete(t3, t3.getPoids());
        graphe.ajouterArete(t4, t4.getPoids());
        graphe.ajouterArete(t5, t5.getPoids());
        graphe.ajouterArete(t6, t6.getPoids());
        graphe.ajouterArete(t7, t7.getPoids());
        graphe.ajouterArete(t8, t8.getPoids());

        // Ajout des arêtes de correspondances inter-modalités dans une même ville (Temps en minutes)
        LienReseau c1 = new LienReseau(aTrain, aBus, ModaliteTransport.BUS, 15.0, true);
        LienReseau c2 = new LienReseau(bTrain, bBus, ModaliteTransport.BUS, 10.0, true);
        LienReseau c3 = new LienReseau(cBus, cTrain, ModaliteTransport.TRAIN, 20.0, true);
        LienReseau c4 = new LienReseau(dAvion, dBus, ModaliteTransport.BUS, 45.0, true);

        graphe.ajouterArete(c1, c1.getPoids());
        graphe.ajouterArete(c2, c2.getPoids());
        graphe.ajouterArete(c3, c3.getPoids());
        graphe.ajouterArete(c4, c4.getPoids());

        // Calcul de l'itinéraire de A à E en autorisant le départ depuis n'importe quelle modalité
        System.out.println("Recherche d'itinéraires de la ville A vers la ville E :\n");
        
        try {
            // Dans ce scénario, on simule un départ depuis l'aéroport (A_AVION) et une arrivée en gare routière (E_BUS)
            List<Chemin> resultats = AlgorithmeKPCC.kpcc(graphe, aAvion, eBus, 4);
            int index = 1;

            for (Chemin chemin : resultats) {
                double tempsTotal = 0;
                for (Connexion arete : chemin.aretes()) {
                    tempsTotal += ((LienReseau) arete).getPoids();
                }

                System.out.println(index + ") " + chemin.toString());
                System.out.println("    -> Temps total : " + (int) tempsTotal + " minutes\n");
                index++;
            }

            if (index == 1) {
                System.out.println("Aucun itinéraire trouvé.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erreur de calcul : Les points de départ ou d'arrivée sont invalides.");
        }
    }
}