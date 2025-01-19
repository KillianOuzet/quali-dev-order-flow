# Contributing Guide


## Structure de l'équipe et des tâches
1. **Organisation en deux équipes** :
   - Les tâches sont partagées entre les deux équipes.
   - Chaque tâche est suivie dans une **issue GitHub**.
   - Les équipes travaillent sur des branches dédiées pour chaque tâche.

2. **Responsabilités principales** :
   - Chaque équipe est responsable du développement de ses tâches, mais la validation du code se fait via une **review de l'autre équipe** avant la fusion dans `develop`.

---

## Workflow GitFlow

Nous utilisons la méthode **GitFlow** pour organiser nos branches et garantir un processus de développement structuré.

### Types de branches
1. **Branches principales** :
   - `master` : Contient uniquement le code en production (versions stables).
   - `develop` : Contient le code en cours de développement pour la prochaine version stable.

2. **Branches de support** :
   - `feature/*` : Pour le développement de nouvelles fonctionnalités.
   - `bugfix/*` : Pour corriger des bugs.
   - `release/*` : Pour préparer une version avant de la fusionner dans `master`.
   - `hotfix/*` : Pour corriger des bugs critiques directement sur `master`.

### Règles de nommage des branches
Respectez le format suivant pour nommer vos branches, afin de garantir la lisibilité et la cohérence :
- **Feature branches** : `feature/<issue-id>-<courte-description>`  
  Exemple : `feature/123-gestion-evenements`
- **Bugfix branches** : `bugfix/<issue-id>-<courte-description>`  
  Exemple : `bugfix/456-correction-timeout`
- **Release branches** : `release/<version>`  
  Exemple : `release/1.3.0`
- **Hotfix branches** : `hotfix/<version>`  
  Exemple : `hotfix/1.2.1`

---

## Processus de travail

### Étape 1 : Créer une issue pour chaque tâche
- Chaque tâche doit commencer par une **issue** GitHub.
- L'issue doit contenir une description claire :
  - Résumé du problème ou de la fonctionnalité.
  - Objectifs ou critères d'acceptation.
  - Équipe responsable.

### Étape 2 : Créer une branche à partir de `develop`
- Créez une branche dédiée pour chaque tâche à partir de `develop`.
- Nommez la branche en suivant les conventions décrites ci-dessus.

### Étape 3 : Travailler sur la branche
- Assurez-vous que vos commits sont **petits, clairs et cohérents**.
- Respectez le format suivant pour vos messages de commit :
  ```
  <type>: <courte description>

  Corps du message (optionnel) : décrit plus en détail le contenu du commit.
  ```
  **Exemples :**
  - `feat: ajout de la gestion des flux d'événements`
  - `fix: correction du délai d'attente configurable`
  - `refactor: extraction des méthodes de gestion des événements`

### Étape 4 : Ouvrir une Pull Request (PR)
- Lorsque la tâche est terminée, ouvrez une **Pull Request** (PR) pour fusionner la branche dans `develop`.
- Dans la description de la PR :
  - Décrivez brièvement les changements apportés.

### Étape 5 : Revue de code par l'autre équipe
- Chaque PR doit être **revue par au moins un membre de l'autre équipe**.
- L'équipe qui fait la revue doit vérifier :
  - La qualité du code.
  - La conformité avec les conventions établies.
  - L'absence de bugs apparents.
- Une fois approuvée, la PR peut être fusionnée dans `develop`.

---

## Gestion des versions

### Numérotation des versions
Nous utilisons la **version sémantique** (**SemVer**) pour incrémenter les numéros de version :
```
MAJOR.MINOR.PATCH
```
- **MAJOR** : Changements incompatibles avec les versions précédentes.
- **MINOR** : Ajout de nouvelles fonctionnalités, compatible avec les versions précédentes.
- **PATCH** : Corrections de bugs ou petites améliorations.

**Exemple :**
- Nouvelle fonctionnalité : passe de `1.2.3` à `1.3.0`.
- Correction de bug : passe de `1.2.3` à `1.2.4`.

### Tagging des versions
- Une fois qu'une version est prête à être déployée, créez une **release branch** (`release/<version>`).
- Après validation, taguez la version dans Git :
  ```bash
  git tag -a v<version> -m "Release notes for version <version>"
  ```
  Exemple :
  ```bash
  git tag -a v1.3.0 -m "Ajout de la gestion des flux d'événements et correction de bugs mineurs"
  ```
- Poussez le tag :
  ```bash
  git push origin v<version>
  ```

---

## Communication entre équipes
1. **Revues de code** : Chaque équipe doit effectuer des revues de code pour les PR de l'autre équipe.
2. **Stand-up meetings (si applicable)** : Synchronisez-vous régulièrement pour discuter des progrès et résoudre les blocages.
3. **Canaux de communication** :
   - Utilisez un outil de messagerie (Slack, Discord, Teams, etc.) pour poser des questions rapides.
   - Documentez les décisions importantes dans GitHub ou un document partagé.

---

## Récapitulatif des bonnes pratiques
1. Créez une **issue** pour chaque tâche.
2. Respectez les conventions de nommage pour les branches et les commits.
3. Testez votre code avant d'ouvrir une PR.
4. Demandez une **review par l'autre équipe** avant de fusionner dans `develop`.
5. Suivez la **version sémantique** pour les numéros de version.
6. Taguez chaque version prête pour le déploiement.

---

