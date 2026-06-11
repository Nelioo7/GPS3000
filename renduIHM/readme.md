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

## Autres infos

Nous avions beaucoup d'idées de choses à implémenter dans l'application, comme une carte par exemple.
L'utilisation d'OpenStreetMap est assez complexe, c'est pourquoi j'ai opté pour Leaflet.
Ce projet open source est utilisé dans beaucoup de pages web car il permet d'implémenter des cartes configurables.
Le problème principal est le manque de temps, implémenter cette carte m'a demandé de faire pas mal de recherches car cela dépasse ce que nous avions vu en cours.
Le CSS est issu de la doc de Leaflet, de même pour le script de lancement dans la page html, que j'ai dû adapter aux besoins du projet.
Encapsuler le tout dans notre appli n'était pas très compliqué néanmoins nous faisons face à un problème de rendu des tiles de la carte lorsqu'on la resize.
Par souci de temps et afin de finir d'autres fonctionnalités nous avons choisi de laisser la carte telle qu'elle est, malgré que cela ait demandé du temps pour en arriver à ce point.

## Vidéo