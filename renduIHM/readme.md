Erwann Besegher - Alaëddine Oudriss - Mehmel Amayas - Pardoue Jérôme

---

- [Application finale](#application-finale)
- [Mockup Figma](#mockup-figma)
- [Justification des choix de conception](#justification-des-choix-de-conception)
- [Contributions](#contributions)
- [Autres infos](#autres-infos)
- [Vidéo](#vidéo)

---

## Application finale
![Screen appli]()

## Mockup Figma

- Prototype fonctionnel Figma: *https://www.figma.com/proto/4IzpJgvGIfrHPEEoBLENXQ/Dev-C2?node-id=159-395&starting-point-node-id=159%3A395*
- Mockup Figma: https://www.figma.com/design/4IzpJgvGIfrHPEEoBLENXQ/Dev-C2?node-id=0-1&t=uid8jrfS4MkJzzIF-1

## Justification des choix de conception

Nous nous sommes basés sur les critères ergonomiques suivants pour la conception de notre application:

- **Charge de travail:**
  Notre interface réduit la charge mentale grâce à des icones permettant une compréhension directe des boutons grâce aux images. De plus, les trajets s'enregistrent dans l'historique permettant à l'utilisateur de ne pas avoir à retaper les lieux à chaque fois.
  Nous avons également choisi de regrouper les actions possibles dans la partie gauche de l'écran pour que toutes les icones soit accessibles rapidement afin de réduire le temps nécessaire pour réaliser des actions (KLM).

- **Contrôle explicite:**
  Les utilisateurs ont un choix total sur les critères qu'ils veulent sélectionner ou non et peuvent contrôler les trajets favoris ou les trajets dans leur historique de recherche.
  
- **Adaptabilité:**
  L'application permet de choisir les trajets en fonctions des préférences des utilisateurs en choisissant divers critères par exemple pour les utilisateurs pressés, la durée du trajet peut être le premier choix.
  
- **Homogénéité / Cohérence:**
  Chaque partie de l'application reste cohérente avec le reste avec un même style pour l'ensemble. et par exemple le bandeau d'avertissement qui reste le même partout.
  
- **Signifiance des codes:**
  Les choix visuels sont immédiatement compréhensibles avec des icones facilement identifiables comme le train, l'avion ou le bus. concernant les couleurs, nous sommes restés dans des couleurs neutres ne faisant aucune différence entre les utilisateurs et du vert pour l'aspect écologique concernant le conseil affiché en haut.
  
- **Compatibilité:**
  L'application utilise du vocabulaire simple que l'on retrouve dans la vie de tous les jours, et est compatible sur plusieurs appareils.
  
- **Guidage:**
  L'interface est simple de compréhension et contient notamment des exemples pour les barres de saisies ou un conseil pour inciter l'utilisateur à s'orienter vers un choix plus éco-responsable

## Contributions

- **Erwann Besegher**: Base de l'IHM + carte (sensée être intéractive)
  J'ai posé les bases de l'ihm avec l'implémentation de la structure générale, ainsi que les champs de recherche, le système d'onglets verticaux ainsi que la carte contenue dans l'onglet itinéraires.
  J'ai été chargé de réaliser cette partie car j'avais une vision d'ensemble de la structure à suivre afin de parvenir à l'interface que nous avions imaginé.

- **Alaeddine Oudriss**: Mockup/proto figma + certains boutons de l'interface
- **Jérôme Pardoue**: Mockup/proto figma + sélecteur de moyen de transport
  Alaeddine et Jérôme ont suivi nos dessins à la main afin de réaliser la maquette figma ainsi que le prototype basse fidélité puis celui de haute fidélité.
  Ils ont implémenté le maximum de fonctionnalités dans figma bien que certaines choses, comme le drag and drop des critères à optimiser n'était pas possible
  Ils ont également implémentés le bouton de mise en favori des itinéraires ainsi que le sélecteur de modalité de transport.

- **Amayas Mehmel**: CSS de l'IHM + corrections de bugs
  Amayas a corrigé certains bugs présents dans l'interface au niveau notamment de la barre des onglets pour leur permettre d'occuper tout l'espace disponible.
  C'est lui qui a écrit le CSS de l'application car il dispose de bonnes compétences en CSS.

Nous avons travaillé tous ensemble afin de lier notre interface avec le code fonctionnel.

## Autres infos

Nous avions beaucoup d'idées de choses à implémenter dans l'application, comme une carte par exemple.
L'utilisation d'OpenStreetMap est assez complexe, c'est pourquoi j'ai opté pour Leaflet.
Ce projet open source est utilisé dans beaucoup de pages web car il permet d'implémenter des cartes configurables.
Le problème principal est le manque de temps, implémenter cette carte m'a demandé de faire pas mal de recherches car cela dépasse ce que nous avions vu en cours.
Le CSS est issu de la doc de Leaflet, de même pour le script de lancement dans la page html, que j'ai dû adapter aux besoins du projet.
Encapsuler le tout dans notre appli n'était pas très compliqué néanmoins nous faisons face à un problème de rendu des tiles de la carte lorsqu'on la resize.
Par souci de temps et afin de finir d'autres fonctionnalités nous avons choisi de laisser la carte telle qu'elle est, malgré que cela ait demandé du temps pour en arriver à ce point.

## Vidéo