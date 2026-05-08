# SAE 2.1/2.2

---
- [SAE 2.1/2.2](#sae-2122)
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
    - [Cas testés](#cas-testés)

---

## Lancement de de l'application

### Execution

Pour compiler il faut utiliser `javac -cp "lib/" -d bin src/.java`.

Pour executer il faut utiliser `java -cp "bin:lib/*" src.Main <nom> <prenom> <critereOpti> <transport> <tempsMax>`.

### Paramètres d'entrée

Les paramètres à entrer dans l'appli sont les suivants:
- le nom
- le prenom
- le critère à opti
- le moyen de transport
- le temps max

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

![Diagramme UML](./rapport/img/)

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

Nous utilisons JUnit pour nos tests et nos tests sont dans le dossier `test/`

### Cas testés

**Classe Voyageur**

| Méthode | Cas testé | Résultat attendu |
|---------|-----------|------------------|
| `Voyageur(nom, prenom, critere)` | Constructeur complet avec `"Dupont"`, `"Jean"`, `PRIX` | `getNom()` - `"Dupont"`, `getPrenom()` - `"Jean"`, `getCritereOpti()` - `PRIX` |
| `Voyageur(nom, prenom)` | Constructeur sans critère avec `"Martin"`, `"Alice"` | `getCritereOpti()` - `TEMPS` (valeur par défaut) |
| `equals()` | Comparaison avec lui-même (`v1.equals(v1)`) | `true` |
| `equals()` | Même nom et prénom, critère différent (`v1.equals(v3)`) | `true` |
| `equals()` | Nom et prénom différents (`v1.equals(v2)`) | `false` |
| `equals()` | Comparaison avec `null` | `false` |
| `toString()` | Voyageur avec `"Dupont"`, `"Jean"`, `PRIX` | La chaîne contient `"Dupont"`, `"Jean"` et `"PRIX"` |

**Classe TraitementDonnées**

| Méthode | Cas testé | Résultat attendu |
|---|---|---|
| `verifDatas()` | Liste valide avec deux lignes complètes | `true` |
| `verifDatas()` | Liste `null` | `false` |
| `verifDatas()` | Liste contenant ligne vide `""` | `false` |
| `verifDatas()` | Champ vide au milieu `"Paris;;TRAIN;120"` | `false` |
| `verifDatas()` | Dernier champ vide `"Paris;Lyon;TRAIN;"` | `false` |
| `filtrerDatas()` | Filtre `TRAIN` sur liste mixte (TRAIN, AVION, BUS) | 2 résultats retournés |
| `filtrerDatas()` | Contenu du premier résultat | Contient `"Paris"` |
| `filtrerDatas()` | Contenu du second résultat | Contient `"Marseille"` |

**Classe Ville**

| Méthode | Cas testé | Résultat attendu |
|---|---|---|
| `Ville(nom)` / `getNom()` | Construction avec `"Paris"` | `getNom()` - `"Paris"` |
| `toString()` | Ville `"Lille"` | `toString()` - `"Lille"` |
| `equals()` | Deux villes avec le même nom `"Marseille"` | `true` |
| `equals()` | Deux villes avec des noms différents `"Marseille"` et `"Lyon"` | `false` |
| `hashCode()` | Deux villes avec le même nom `"Nice"` | Même hashCode |

**Classe CoutConnexion**

| Méthode | Cas testé | Résultat attendu |
|---|---|---|
| `CoutConnexion(prix, temps, co2)` | Construction avec `60.0`, `80.0`, `1.7` | `getCout(PRIX)` - `60.0` |
| `CoutConnexion(prix, temps, co2)` | Construction avec `60.0`, `80.0`, `1.7` | `getCout(TEMPS)` - `80.0` |
| `CoutConnexion(prix, temps, co2)` | Construction avec `60.0`, `80.0`, `1.7` | `getCout(CO2)` - `1.7` |
| `CoutConnexion(typeCout, valeur)` | Construction avec uniquement `PRIX = 50.0` | `getCout(PRIX)` - `50.0` |
| `CoutConnexion(typeCout, valeur)` | Construction avec uniquement `PRIX = 50.0` | `getCout(TEMPS)` - `null` |
| `CoutConnexion(typeCout, valeur)` | Construction avec uniquement `PRIX = 50.0` | `getCout(CO2)` - `null` |

**Classe TestTrajet**

| Méthode | Cas testé | Résultat attendu |
|---|---|---|
| `Trajet(depart, arrivee, modalite, cout)` | Construction avec TRAIN, AVION et BUS | Tout va bien |
| `getDepart()` | Trajet Paris - Lille en TRAIN | `ville1` (Paris) |
| `getArrivee()` | Trajet Paris - Lille en TRAIN | `ville2` (Lille) |
| `getModalite()` | Trajet Paris - Lille en TRAIN | `TRAIN` |
| `getCouts().getCout(PRIX)` | Trajet avec coûts `60.0, 80.0, 1.7` | `60.0` |
| `getCouts().getCout(TEMPS)` | Trajet avec coûts `60.0, 80.0, 1.7` | `80.0` |
| `getCouts().getCout(CO2)` | Trajet avec coûts `60.0, 80.0, 1.7` | `1.7` |
| `toString()` | Trajet Paris - Lille en TRAIN | `"Paris -> Lille (TRAIN)"` |
| `toString()` | Trajet Marseille - Lyon en AVION | `"Marseille -> Lyon (AVION)"` |
| `toString()` | Trajet Bordeaux - Paris en BUS | `"Bordeaux -> Paris (BUS)"` |