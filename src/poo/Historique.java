package poo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe qui gère l'historique des recherches de voyages et sauvegarde dans un fichier binaire .ser
 * @author Alaëddine Oudriss
 * @version 1.0.0
 */
public class Historique implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private ArrayList<String> recherches;

    public Historique() {
        this.recherches = new ArrayList<>();
    }

    public void ajouterRecherche(String recherche) {
        this.recherches.add(recherche);
    }

    public ArrayList<String> getRecherches() {
        return this.recherches;
    }

    public int getNombreRecherches() {
        return this.recherches.size();
    }

    //affiche toutes les recherches 
    public void afficherHistorique() {

        if (this.recherches.isEmpty()) {
            System.out.println("Aucune recherche dans l'historique.");
        }else{
            for (int i = 0; i < this.recherches.size(); i++) {
                System.out.println((i + 1) + ") " + this.recherches.get(i));
            }
        }
    }

    //sauvegarde l'historique dans un fichier .ser
    public void sauvegarder(String cheminFichier) {
        
        try (ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream(new File(cheminFichier)))) {
            oS.writeObject(this);    
        } catch (Exception e) { 
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    //charge un historique depuis un fichier .ser
    public static Historique charger(String cheminFichier) {
        Historique h = null;

        try (ObjectInputStream iS = new ObjectInputStream(new FileInputStream(new File(cheminFichier)))) {
            h = (Historique) iS.readObject();
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
            e.printStackTrace(); 
        }
        
        return h;
    } 
}
