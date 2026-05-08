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

Nous utilisons JUnit pour nos tests et nos tests sont dans le dossier `test/`

### Cas testés

**Classe Voyageur**

| Méthode | Cas testé | Résultat attendu |
|---------|-----------|------------------|
| `Voyageur(nom, prenom, critere)` | Constructeur complet avec `"Dupont"`, `"Jean"`, `PRIX` | `getNom()` → `"Dupont"`, `getPrenom()` → `"Jean"`, `getCritereOpti()` → `PRIX` |
| `Voyageur(nom, prenom)` | Constructeur sans critère avec `"Martin"`, `"Alice"` | `getCritereOpti()` → `TEMPS` (valeur par défaut) |
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
| `Ville(nom)` / `getNom()` | Construction avec `"Paris"` | `getNom()` → `"Paris"` |
| `toString()` | Ville `"Lille"` | `toString()` → `"Lille"` |
| `equals()` | Deux villes avec le même nom `"Marseille"` | `true` |
| `equals()` | Deux villes avec des noms différents `"Marseille"` et `"Lyon"` | `false` |
| `hashCode()` | Deux villes avec le même nom `"Nice"` | Même hashCode |