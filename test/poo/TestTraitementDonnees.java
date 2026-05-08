package poo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * Classe de tests pour TraitementDonnees.
 * Vérifie le bon fonctionnement des méthodes.
 * * @author Alaëddine
 * @version 1.0.0
 */
public class TestTraitementDonnees {

    @Test
    public void testVerifDatas() {
        System.out.println("-- verifDatas() --");

        //Test avec des données valides
        ArrayList<String> dataValide = new ArrayList<>();
        dataValide.add("Paris;Lyon;TRAIN;120");
        dataValide.add("Lille;Marseille;AVION;80");
        assertTrue(TraitementDonnees.verifDatas(dataValide), "Doit retourner true pour des données complètes");
        assertFalse(TraitementDonnees.verifDatas(null), "Doit retourner false si la liste est nulle");

        //test avec une ligne vide
        ArrayList<String> dataLigneVide = new ArrayList<>();
        dataLigneVide.add("Paris;Lyon;TRAIN;120");
        dataLigneVide.add("");        
        
        assertFalse(TraitementDonnees.verifDatas(dataLigneVide), "Doit retourner false s'il y a une ligne vide");

        //Test avec un champ manquant au milieu.
        ArrayList<String> dataChampVideMilieu = new ArrayList<>();
        dataChampVideMilieu.add("Paris;;TRAIN;120");
        assertFalse(TraitementDonnees.verifDatas(dataChampVideMilieu), "Doit retourner false s'il manque un champ au milieu");

        //Test avec un champ manquant à la fin
        ArrayList<String> dataChampVideFin = new ArrayList<>();
        dataChampVideFin.add("Paris;Lyon;TRAIN;");
        assertFalse(TraitementDonnees.verifDatas(dataChampVideFin), "Doit retourner false s'il manque le dernier champ");
    }
    
    @Test
    public void testFiltrerDatas() {
        System.out.println("-- filtrerDatas() --");
        
        //ajout de données pour tester le filtrage.
        ArrayList<String> data = new ArrayList<>();
        data.add("Paris;Lyon;TRAIN;120");
        data.add("Lille;Marseille;AVION;80");
        data.add("Bordeaux;Nantes;BUS;60");
        data.add("Paris;Marseille;TRAIN;150");
        
        //test du filtrage pour les trajets en train.
        ArrayList<String> resultTrain = TraitementDonnees.filtrerDatas(data, ModaliteTransport.TRAIN);
        assertEquals(2, resultTrain.size(), "Doit retourner 2 trajets en train");
        
        //verification que les trajets retournés sont corrects.
        assertTrue(resultTrain.get(0).contains("Paris"), "le trajet doit partir de Paris");
        assertTrue(resultTrain.get(1).contains("Marseille"), "le trajet doit arriver à Marseille");

    }
}