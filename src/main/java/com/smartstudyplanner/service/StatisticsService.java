/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.service;

/**
 *
 * @author Aissaoui
 */


import com.smartstudyplanner.dao.SessionDAO;
import com.smartstudyplanner.model.SessionTravail;
import com.smartstudyplanner.model.Tache;
import java.util.ArrayList;

public class StatisticsService {

    private SessionDAO sessionDAO;

    public StatisticsService() {
        this.sessionDAO = new SessionDAO();
    }

    public void afficherTableauDeBord(PlanningService planning) {
        ArrayList<Tache> taches = planning.getTaches();
        ArrayList<SessionTravail> sessions = sessionDAO.recupererToutesLesSessions(taches);

        System.out.println("===== TABLEAU DE BORD =====");

        if (taches.isEmpty()) {
            System.out.println("Aucune donnée disponible.");
            return;
        }

        System.out.println("Nombre total de tâches : " + taches.size());
        System.out.println("Tâches terminées : " + planning.compterTachesTerminees());
        System.out.println("Tâches non terminées : " + planning.compterTachesNonTerminees());
        System.out.println("Progression globale : " + planning.calculerProgressionGlobale() + "%");
        System.out.println("Priorité moyenne : " + calculerPrioriteMoyenne(taches));

        System.out.println("Nombre total de sessions : " + sessions.size());
        afficherTempsTotalTravaille();
        sessionDAO.afficherTempsParMatiere();

        Tache prioritaire = planning.trouverTachePrioritaire();

        if (prioritaire != null) {
            System.out.println("Tâche la plus urgente : " + prioritaire.getTitre());
            System.out.println("Score de priorité : " + prioritaire.calculerPriorite());
        }
    }

    public double calculerPrioriteMoyenne(ArrayList<Tache> taches) {
        if (taches.isEmpty()) {
            return 0;
        }

        int somme = 0;

        for (int i = 0; i < taches.size(); i++) {
            somme += taches.get(i).calculerPriorite();
        }

        return (double) somme / taches.size();
    }

    private void afficherTempsTotalTravaille() {
        int totalMinutes = sessionDAO.calculerTempsTotalTravail();

        int heures = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        System.out.println("Temps total travaillé : " + heures + "h " + minutes + "min");
    }
    
    public void afficherRisqueParMatiere(PlanningService planning) {
    ArrayList<Tache> taches = planning.getTaches();

    if (taches.isEmpty()) {
        System.out.println("Aucune tâche disponible pour calculer le risque.");
        return;
    }

    System.out.println("===== RISQUE PAR MATIÈRE =====");

    ArrayList<String> matieresTraitees = new ArrayList<>();

    for (int i = 0; i < taches.size(); i++) {
        String nomMatiere = taches.get(i).getMatiere().getNom();

        if (!matieresTraitees.contains(nomMatiere)) {
            matieresTraitees.add(nomMatiere);

            int difficulte = taches.get(i).getMatiere().getDifficulte();
            int sommeProgression = 0;
            int sommePriorite = 0;
            int compteur = 0;

            for (int j = 0; j < taches.size(); j++) {
                if (taches.get(j).getMatiere().getNom().equals(nomMatiere)) {
                    sommeProgression += taches.get(j).getProgression();
                    sommePriorite += taches.get(j).calculerPriorite();
                    compteur++;
                }
            }

            double progressionMoyenne = (double) sommeProgression / compteur;
            double prioriteMoyenne = (double) sommePriorite / compteur;

            int scoreRisque = calculerScoreRisque(difficulte, progressionMoyenne, prioriteMoyenne);
            String niveau = determinerNiveauRisque(scoreRisque);

            System.out.println(nomMatiere
                    + " | Score : " + scoreRisque
                    + " | Niveau : " + niveau
                    + " | Progression moyenne : " + progressionMoyenne + "%"
                    + " | Priorité moyenne : " + prioriteMoyenne);
        }
    }
}

private int calculerScoreRisque(int difficulte, double progressionMoyenne, double prioriteMoyenne) {
    int score = 0;

    score += difficulte * 15;
    score += (int) (100 - progressionMoyenne);
    score += (int) (prioriteMoyenne / 3);

    return score;
}

private String determinerNiveauRisque(int score) {
    if (score >= 180) {
        return "CRITIQUE";
    } else if (score >= 130) {
        return "ÉLEVÉ";
    } else if (score >= 90) {
        return "MOYEN";
    } else {
        return "FAIBLE";
    }
}
}