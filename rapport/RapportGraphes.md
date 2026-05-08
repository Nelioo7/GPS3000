# Rapport SAÉ 2.01-2.02, ressource Graphes

Equipe C2: Erwann Besegher, Alaeddine Oudriss, Jérôme Pardoue, Mehmel Amayas

## 1 Version 1: Un seul moyen de transport

### 1.1 Exemple

Le schéma suivant représente la situation que nous avons modélisée.

![image1Osef]()

Nous avons défini les connexions directes:

| Arête | Mode | Durée (min) | CO₂ (kg) | Prix (€) |
|---|---|---|---|---|
| Lille -> Paris | Train | 60 | 1.7 | 35 |
| Lille -> Paris | Bus | 180 | 5.0 | 15  |
| Lille -> Jakarta | Avion | 780 | 280 | 550 |
| Lille -> New York | Avion | 1140 | 440 | 820 |
| Jakarta -> Sydney | Avion | 420 | 155 | 310 |
| Jakarta -> New York | Avion | 1140 | 440 | 820 |
| Paris -> Sydney | Avion | 1320 | 405 | 900 |
| Paris -> Jakarta | Avion | 810 | 310 | 700 |
| Paris -> Tokyo | Avion | 780 | 290 | 620 |
| Sydney -> Tokyo | Avion | 600 | 215 | 480 |
| New York -> Tokyo | Avion | 840 | 310 | 520 |
| New York -> Sydney | Avion | 1260  | 310 | 950 |

**Scénario d'utilisation :**
L'utilisatrice de notre scénario souhaite voyager de Lille à Sydney. Elle choisit l'avion et le prix comme critère d'optimisation.

**Résultats obtenus :**
L'algorithme devait calculer les 4 meilleurs itinéraires.
Dans notre réseau filtré uniquement pour l'avion, il n'existe que 3 chemins possibles pour relier Lille à Sydney.
On affiche donc tous les itinéraires possibles classés par prix :

```bash
javac -cp "lib/*" -d bin src/poo/*.java src/graphes/*.java
java -cp "bin:lib/*" graphes.GraphesV1
=== PROTOTYPE GRAPHES V1 : SCÉNARIO INTERNATIONAL ===

Préférences de l'utilisatrice :
- Transport choisi : AVION
- Critère à optimiser : PRIX
- Trajet : Lille -> Sydney

=== 4 MEILLEURS ITINÉRAIRES ===
1) Chemin(Arêtes: [Lille -> Jakarta (AVION), Jakarta -> Sydney (AVION)], Poids: 860.000000) [Coût total (PRIX) : 860.0 €]
2) Chemin(Arêtes: [Lille -> New York (AVION), New York -> Sydney (AVION)], Poids: 1770.000000) [Coût total (PRIX) : 1770.0 €]
3) Chemin(Arêtes: [Lille -> Jakarta (AVION), Jakarta -> New York (AVION), New York -> Sydney (AVION)], Poids: 2320.000000) [Coût total (PRIX) : 2320.0 €]
```

### 1.2 Modèle pour l'exemple

![image2ToujoursOsef]()

### 1.3 Modélisation pour la Version 1 dans le cas général
Pour résoudre ce problème de manière abstraite:
- Le type de graphe: Nous utilisons un multigraphe orienté et valué car deux villes peuvent être reliées par plusieurs moyens de transport (ex: Train et Bus entre Lille et Paris) et orienté car un trajet de A vers B n'implique pas forcément le même coût ou temps de B vers A.
- Les sommets: Ce sont les `Lieu` du réseau (objets `Ville`).
- Les arêtes: Ce sont les instances de `Connexion` (ici des `Trajet`). Une arête est ajoutée entre un sommet et un autre uniquement si le mode de transport correspond au choix de l'utilisatrice.
Le poids de cette arête correspond au prix.
- L'algorithme: Nous utilisons l'algorithme des k-plus courts chemins (KPCC) pour trouver les meilleurs itinéraires.
Les arguments passés sont: le graphe, le sommet de départ, le sommet d'arrivée, et la valeur `k` (nombre d'itinéraires qu'on veut).

### 1.4 Prototype pour la Version 1

Lien du repo gitlab: https://gitlab.univ-lille.fr/sae2.01-2.02/2026/C2