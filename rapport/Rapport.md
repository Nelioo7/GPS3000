# Rapport SAE 2.01/2.02

---

- [Rapport SAE 2.01/2.02](#rapport-sae-201202)
  - [Lancement de de l'application](#lancement-de-de-lapplication)
    - [Execution](#execution)
    - [Paramètres d'entrée](#paramètres-dentrée)
    - [Structure du projet](#structure-du-projet)
  - [Conception](#conception)
  - [Logique](#logique)
    - [Filtrage](#filtrage)
    - [Validation des données](#validation-des-données)
    - [Graphes](#graphes)
  - [Tests](#tests)

---

## Lancement de de l'application

### Execution

    Pour executer l'application il faut taper la commande ``.

### Paramètres d'entrée

    Les paramètres à entrer dans l'appli sont les suivants:
    - parafhdjkqhf
    - fjeqj (faut qu'on mette les paramètres)

### Structure du projet

    *Le projet suit la structure suivante*
    ```
    .
    ├── bin
    ├── lib
    │   └── junit-platform-console-standalone-6.0.3 (1).jar
    ├── rapport
    │   ├── img
    │   ├── Rapport.md
    │   └── uml.drawio
    ├── README.md
    ├── src
    │   ├── Connexion.java
    │   ├── CoutConnexion.java
    │   ├── Lieu.java
    │   ├── ModaliteTransport.java
    │   ├── Plateforme.java
    │   ├── TraitementDonnees.java
    │   ├── TypeCout.java
    │   └── Voyageur.java
    └── test
        └── testVoyageur.java
    ```

## Conception

mettre uml + justifier nos choix

## Logique

  ### Filtrage

  Pour l'instant notre application ne permet que de filtrer les types de transport.

  ### Validation des données

  La fonction verifDatas() traite nos données sous la forme d'une ArrayList
  Elle vérifie si:
  - la liste n'est pas *null*
  - chaque ligne n'est ni *null* ni égale à *""*
  - chaque champ séparé de *;* n'est pas vide
  
  La fonction filtrerDatas() filtre par mode de transport.
  Elle parcourt toutes les lignes et ajoute les lignes dont le mode de transport correspond à celui selectionné.

  ### Graphes

  Dans notre modélisation, les sommets représentent les villes, les arêtes représentent les connexions et le poid représente le critère choisi à optimiser.

  Nous utilisons les lib fournies pour effectuer le calcul.

## Tests
