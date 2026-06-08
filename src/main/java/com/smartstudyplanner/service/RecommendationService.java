/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.service;

/**
 *
 * @author Aissaoui
 */
import com.smartstudyplanner.model.Examen;
import com.smartstudyplanner.model.Tache;
import com.smartstudyplanner.model.TacheRevision;

public class RecommendationService {

    public void recommanderTachePrioritaire(PlanningService planning) {
        Tache prioritaire = planning.trouverTachePrioritaire();

        if (prioritaire == null) {
            System.out.println("Aucune tâche disponible pour générer une recommandation.");
            return;
        }

        System.out.println("===== RECOMMANDATION INTELLIGENTE =====");
        System.out.println("Tâche recommandée : " + prioritaire.getTitre());
        System.out.println("Matière : " + prioritaire.getMatiere().getNom());
        System.out.println("Progression : " + prioritaire.getProgression() + "%");
        System.out.println("Priorité : " + prioritaire.calculerPriorite());
        System.out.println("Niveau d'urgence : " + determinerNiveauUrgence(prioritaire));
        System.out.println();

        expliquerPourquoi(prioritaire);
        System.out.println();

        donnerConseil(prioritaire);
    }

    public String determinerNiveauUrgence(Tache tache) {
        int priorite = tache.calculerPriorite();

        if (priorite >= 300) {
            return "URGENT";
        } else if (priorite >= 220) {
            return "TRÈS IMPORTANT";
        } else if (priorite >= 150) {
            return "IMPORTANT";
        } else if (priorite >= 80) {
            return "NORMAL";
        } else {
            return "FAIBLE";
        }
    }

    public void expliquerPourquoi(Tache tache) {
        System.out.println("Pourquoi cette tâche est recommandée ?");

        if (tache.getMatiere().getDifficulte() >= 4) {
            System.out.println("- La matière est difficile.");
        }

        if (tache.getProgression() < 50) {
            System.out.println("- Ta progression est encore faible.");
        }

        if (tache instanceof Examen) {
            Examen examen = (Examen) tache;

            if (examen.getJoursRestants() <= 5) {
                System.out.println("- L'examen est très proche.");
            } else if (examen.getJoursRestants() <= 10) {
                System.out.println("- L'examen approche.");
            }

            if (examen.getCoefficient() >= 4) {
                System.out.println("- Le coefficient est élevé.");
            }
        }

        if (tache instanceof TacheRevision) {
            TacheRevision revision = (TacheRevision) tache;

            if (revision.getDureeEstimee() <= 60) {
                System.out.println("- C'est une tâche assez courte, tu peux la faire rapidement.");
            }
        }
    }

    public void donnerConseil(Tache tache) {
        System.out.println("Conseil :");

        int priorite = tache.calculerPriorite();

        if (tache instanceof Examen) {
            Examen examen = (Examen) tache;

            if (priorite >= 300) {
                System.out.println("Travaille cette matière aujourd'hui au moins 2 heures.");
            } else if (priorite >= 220) {
                System.out.println("Prévois une session sérieuse de 1h30 aujourd'hui ou demain.");
            } else {
                System.out.println("Planifie une révision régulière avant l'examen.");
            }

            System.out.println("Jours restants avant l'examen : " + examen.getJoursRestants());
        } else if (tache instanceof TacheRevision) {
            TacheRevision revision = (TacheRevision) tache;

            if (revision.getDureeEstimee() <= 30) {
                System.out.println("Fais cette tâche aujourd'hui : elle est courte et utile.");
            } else if (revision.getDureeEstimee() <= 60) {
                System.out.println("Prévois environ 1 heure pour avancer dessus.");
            } else {
                System.out.println("Divise cette tâche en petites sessions de 30 à 45 minutes.");
            }
        } else {
            System.out.println("Ajoute cette tâche dans ton planning de révision.");
        }
    }
}