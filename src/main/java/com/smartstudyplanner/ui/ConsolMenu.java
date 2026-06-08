/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.ui;

/**
 *
 * @author Aissaoui
 */

import com.smartstudyplanner.dao.MatiereDAO;
import com.smartstudyplanner.model.Examen;
import com.smartstudyplanner.model.Matiere;
import com.smartstudyplanner.model.TacheRevision;
import com.smartstudyplanner.service.PlanningService;
import com.smartstudyplanner.service.RecommendationService;
import com.smartstudyplanner.service.StatisticsService;
import com.smartstudyplanner.service.ExportService;
import com.smartstudyplanner.model.Tache;
import com.smartstudyplanner.service.SessionService;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsolMenu {
    private Scanner scanner;
    private ArrayList<Matiere> matieres;
    private MatiereDAO matiereDAO;
    private PlanningService planningService;
    private RecommendationService recommendationService;
    private StatisticsService statisticsService;
    private ExportService exportService;
    private SessionService sessionService;

    public ConsolMenu() {
        this.scanner = new Scanner(System.in);
        this.matiereDAO = new MatiereDAO();
        this.matieres = new ArrayList<>();
        this.planningService = new PlanningService();
        this.recommendationService = new RecommendationService();
        this.statisticsService = new StatisticsService();
        this.exportService = new ExportService();
        this.sessionService = new SessionService();
    }

    public void demarrer() {
        int choix;

        do {
            afficherMenu();
            choix = lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    ajouterMatiere();
                    break;
                case 2:
                    ajouterTacheRevision();
                    break;
                case 3:
                    ajouterExamen();
                    break;
                case 4:
                    planningService.afficherToutesLesTaches();
                    break;
                case 5:
                    planningService.afficherTachesPrioritaires();
                    break;
                case 6:
                    marquerTacheTerminee();
                    break;
                case 7:
                    statisticsService.afficherTableauDeBord(planningService);
                    break;
                case 8:
                    recommendationService.recommanderTachePrioritaire(planningService);
                    break;
                case 9:
                    exporterCSV();
                    break;
                case 10:
                    ajouterSessionTravail();
                    break;
                case 11:
                    sessionService.afficherSessions(planningService.getTaches());
                    break;
                case 12:
                    sessionService.afficherTempsTotal();
                    break;
                case 13:
                    supprimerTache();
                    break;
                case 14:
                    supprimerMatiere();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

            System.out.println();

        } while (choix != 0);
    }

    private void afficherMenu() {
        System.out.println("===== SMART STUDY PLANNER =====");
        System.out.println("1. Ajouter une matière");
        System.out.println("2. Ajouter une tâche de révision");
        System.out.println("3. Ajouter un examen");
        System.out.println("4. Afficher toutes les tâches");
        System.out.println("5. Afficher les tâches par priorité");
        System.out.println("6. Marquer une tâche comme terminée");
        System.out.println("7. Afficher les statistiques");
        System.out.println("8. Recommandation intelligente");
        System.out.println("9. Exporter le planning en CSV");
        System.out.println("10. Ajouter une session de travail");
        System.out.println("11. Afficher les sessions de travail");
        System.out.println("12. Afficher le temps total travaillé");
        System.out.println("13. Supprimer une tâche");
        System.out.println("14. Supprimer une matière");
        System.out.println("0. Quitter");
    }

    private void ajouterMatiere() {
    System.out.println("===== AJOUTER UNE MATIÈRE =====");

    String nom = lireTexte("Nom de la matière : ").trim();
    int difficulte = lireEntier("Difficulté de 1 à 5 : ");
    String objectif = lireTexte("Objectif : ").trim();

    System.out.println("DEBUG nom saisi = [" + nom + "]");

    if (nom.isEmpty()) {
        System.out.println("Erreur : le nom de la matière ne peut pas être vide.");
        return;
    }

    if (matiereDAO.existeMatiereAvecNom(nom)) {
        System.out.println("Cette matière existe déjà. Ajout annulé.");
        return;
    }

    Matiere matiere = new Matiere(nom, difficulte, objectif);

    matiereDAO.ajouterMatiere(matiere);

    this.matieres = matiereDAO.recupererToutesLesMatieres();

    System.out.println("Matière ajoutée avec succès.");
}

    private void afficherMatieres() {
    if (matieres.isEmpty()) {
        System.out.println("Aucune matière enregistrée.");
        return;
    }

    System.out.println("===== LISTE DES MATIÈRES =====");

    for (int i = 0; i < matieres.size(); i++) {
        System.out.println((i + 1) + ". "
                + matieres.get(i).getNom()
                + " | ID : " + matieres.get(i).getId()
                + " | Difficulté : " + matieres.get(i).getDifficulte() + "/5");
    }
}

    private Matiere choisirMatiere() {
        if (matieres.isEmpty()) {
            System.out.println("Vous devez d'abord ajouter une matière.");
            return null;
        }

        afficherMatieres();

        int numero = lireEntier("Choisissez le numéro de la matière : ");
        int index = numero - 1;

        if (index >= 0 && index < matieres.size()) {
            return matieres.get(index);
        } else {
            System.out.println("Numéro de matière invalide.");
            return null;
        }
    }

    private void ajouterTacheRevision() {
        System.out.println("===== AJOUTER UNE TÂCHE DE RÉVISION =====");

        Matiere matiere = choisirMatiere();

        if (matiere == null) {
            return;
        }

        String titre = lireTexte("Titre de la tâche : ");
        int progression = lireEntier("Progression actuelle de 0 à 100 : ");
        int duree = lireEntier("Durée estimée en minutes : ");

        TacheRevision tache = new TacheRevision(titre, matiere, progression, duree);
        planningService.ajouterTache(tache);
    }

    private void ajouterExamen() {
        System.out.println("===== AJOUTER UN EXAMEN =====");

        Matiere matiere = choisirMatiere();

        if (matiere == null) {
            return;
        }

        String titre = lireTexte("Titre de l'examen : ");
        int progression = lireEntier("Progression actuelle de 0 à 100 : ");
        int joursRestants = lireEntier("Nombre de jours restants : ");
        int coefficient = lireEntier("Coefficient de 1 à 5 : ");

        Examen examen = new Examen(titre, matiere, progression, joursRestants, coefficient);
        planningService.ajouterTache(examen);
    }

    private void marquerTacheTerminee() {
        System.out.println("===== MARQUER UNE TÂCHE TERMINÉE =====");

        planningService.afficherToutesLesTaches();

        int numero = lireEntier("Numéro de la tâche à terminer : ");
        planningService.marquerTacheTerminee(numero);
    }
    
    private void exporterCSV() {
    System.out.println("===== EXPORT CSV =====");

    String nomFichier = lireTexte("Nom du fichier CSV : ").trim();

    if (nomFichier.isEmpty()) {
        nomFichier = "planning_export.csv";
    }

    if (!nomFichier.endsWith(".csv")) {
        nomFichier += ".csv";
    }

    exportService.exporterTachesCSV(planningService, nomFichier);
}
    
    private void ajouterSessionTravail() {
    System.out.println("===== AJOUTER UNE SESSION DE TRAVAIL =====");

    if (planningService.getTaches().isEmpty()) {
        System.out.println("Aucune tâche disponible.");
        return;
    }

    planningService.afficherToutesLesTaches();

    int numero = lireEntier("Numéro de la tâche travaillée : ");
    int index = numero - 1;

    if (index < 0 || index >= planningService.getTaches().size()) {
        System.out.println("Numéro de tâche invalide.");
        return;
    }

    Tache tache = planningService.getTaches().get(index);

    int duree = lireEntier("Durée travaillée en minutes : ");
    String commentaire = lireTexte("Commentaire : ");

    sessionService.ajouterSession(tache, duree, commentaire);

    System.out.println("Session ajoutée avec succès.");
}

    private int lireEntier(String message) {
        while (true) {
            try {
                System.out.print(message);
                int valeur = Integer.parseInt(scanner.nextLine());
                return valeur;
            } catch (NumberFormatException e) {
                System.out.println("Erreur : veuillez entrer un nombre valide.");
            }
        }
    }

    private String lireTexte(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
    
    private void supprimerTache() {
    System.out.println("===== SUPPRIMER UNE TÂCHE =====");

    if (planningService.getTaches().isEmpty()) {
        System.out.println("Aucune tâche disponible.");
        return;
    }

    planningService.afficherToutesLesTaches();

    int numero = lireEntier("Numéro de la tâche à supprimer : ");

    planningService.supprimerTache(numero);
}
    
    private void supprimerMatiere() {
    System.out.println("===== SUPPRIMER UNE MATIÈRE =====");

    if (matieres.isEmpty()) {
        System.out.println("Aucune matière disponible.");
        return;
    }

    afficherMatieres();

    int numero = lireEntier("Numéro de la matière à supprimer : ");
    int index = numero - 1;

    if (index < 0 || index >= matieres.size()) {
        System.out.println("Numéro de matière invalide.");
        return;
    }

    Matiere matiere = matieres.get(index);

    matiereDAO.supprimerMatiere(matiere.getId());

    this.matieres = matiereDAO.recupererToutesLesMatieres();
}
    
}
