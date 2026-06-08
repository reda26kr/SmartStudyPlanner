# SmartStudyPlanner

SmartStudyPlanner est une application Java console orientée objet qui aide les étudiants à organiser leurs révisions, suivre leur progression et prioriser leurs tâches grâce à un système de recommandation intelligente.

## Fonctionnalités

- Gestion des matières
- Gestion des tâches de révision
- Gestion des examens
- Calcul automatique de priorité
- Recommandation intelligente de la tâche la plus urgente
- Tableau de bord statistique
- Suivi des sessions de travail
- Temps total travaillé
- Temps travaillé par matière
- Score de risque par matière
- Export CSV du planning
- Sauvegarde locale avec SQLite

## Technologies utilisées

- Java
- Maven
- SQLite
- JDBC
- POO
- NetBeans

## Concepts Java utilisés

- Classes et objets
- Encapsulation
- Constructeurs
- Getters / Setters
- Héritage
- Polymorphisme
- Classes abstraites
- Interfaces
- ArrayList
- DAO
- Services
- JDBC
- Gestion de fichiers CSV

## Architecture du projet

```text
SmartStudyPlanner
├── model
│   ├── Matiere.java
│   ├── Tache.java
│   ├── TacheRevision.java
│   ├── Examen.java
│   ├── SessionTravail.java
│   └── Priorisable.java
│
├── dao
│   ├── DatabaseManager.java
│   ├── MatiereDAO.java
│   ├── TacheDAO.java
│   └── SessionDAO.java
│
├── service
│   ├── PlanningService.java
│   ├── RecommendationService.java
│   ├── StatisticsService.java
│   ├── SessionService.java
│   └── ExportService.java
│
├── ui
│   └── ConsoleMenu.java
│
└── SmartStudyPlanner.java