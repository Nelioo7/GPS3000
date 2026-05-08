# Rapport SAÉ 2.01-2.02, ressource Graphes

Equipe C2: Erwann Besegher, Alaeddine Oudriss, Jérôme Pardoue, Mehmel Amayas

Lien du repo gitlab: https://gitlab.univ-lille.fr/sae2.01-2.02/2026/C2

## 1 Version 1: Un seul moyen de transport

### 1.1 Exemple

Le schéma suivant représente la situation que nous avons modélisée (réseau international de 6 villes).

![Graphe de la Version 1](./img/graphe1.png)

Nous avons défini les connexions directes suivantes (arêtes du graphe) :

| # | Arête (Connexion directe) | Mode | Durée (min) | CO₂ (kg éq.) | Prix (€) |
|---|---------------------------|------|-------------|--------------|----------|
| 1 | Lille -> Paris            | Train| 60          | 1.7          | 35       |
| 2 | Lille -> Paris            | Bus  | 180         | 5.0          | 15       |
| 3 | Lille -> Jakarta          | Avion| 780         | 280          | 550      |
| 4 | Lille -> New York         | Avion| 1140        | 440          | 820      |
| 5 | Jakarta -> Sydney         | Avion| 420         | 155          | 310      |
| 6 | Jakarta -> New York       | Avion| 1140        | 440          | 820      |
| 7 | Paris -> Sydney           | Avion| 1320        | 405          | 900      |
| 8 | Paris -> Jakarta          | Avion| 810         | 310          | 700      |
| 9 | Paris -> Tokyo            | Avion| 780         | 290          | 620      |
| 10| Sydney -> Tokyo           | Avion| 600         | 215          | 480      |
| 11| New York -> Tokyo         | Avion| 840         | 310          | 520      |
| 12| New York -> Sydney        | Avion| 1260        | 310          | 950      |

**Scénario d'utilisation :**
L'utilisatrice de notre scénario souhaite voyager de **Lille à Sydney**. Elle choisit l'**AVION** comme moyen de transport imposé et le **PRIX** comme critère d'optimisation.

**Résultats obtenus :**
L'algorithme devait calculer les 4 meilleurs itinéraires. Cependant, dans notre réseau filtré uniquement pour l'avion, il n'existe que 3 chemins possibles pour relier Lille à Sydney. Conformément aux consignes, l'algorithme affiche donc tous les itinéraires possibles classés par prix :

1. Lille -> Jakarta -> Sydney [Coût total (PRIX) : 860.0 €]
2. Lille -> New York -> Sydney [Coût total (PRIX) : 1770.0 €]
3. Lille -> Jakarta -> New York -> Sydney [Coût total (PRIX) : 2320.0 €]

### 1.2 Modèle pour l'exemple
*(Insérer ici une description du schéma dessiné avec les valeurs de prix sur les flèches d'Avion)*

### 1.3 Modélisation pour la Version 1 dans le cas général
Pour résoudre ce problème de manière abstraite :
- **Le type de graphe :** Nous utilisons un multigraphe orienté et valué. Il est "multi" car deux villes peuvent être reliées par plusieurs moyens de transport (ex: Train et Bus entre Lille et Paris). Il est orienté car un trajet de A vers B n'implique pas forcément le même coût ou temps de B vers A.
- **Les sommets :** Ce sont les `Lieu` du réseau (ici, les objets `Ville`).
- **Les arêtes :** Ce sont les instances de `Connexion` (ici des `Trajet`). Une arête est ajoutée entre un sommet X et Y uniquement si le mode de transport correspond au choix de l'utilisateur. Le poids de cette arête correspond à la valeur numérique du critère d'optimisation choisi (ici le Prix).
- **L'algorithme :** Nous utilisons l'algorithme des k-plus courts chemins (KPCC) pour trouver les meilleurs itinéraires. Les arguments passés sont : le graphe généré, le sommet de départ, le sommet d'arrivée, et la valeur `k` (nombre d'itinéraires demandés).

### 1.4 Prototype pour la Version 1
La classe `GraphesV1` modélisant cet exemple est disponible sur notre dépôt GitLab :
*(Insérer le lien vers le commit de GraphesV1.java ici)*