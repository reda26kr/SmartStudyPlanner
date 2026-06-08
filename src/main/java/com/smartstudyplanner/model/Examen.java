/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.model;

/**
 *
 * @author Aissaoui
 */
public class Examen extends Tache {
    private int joursRestants;
    private int coefficient; // entre 1 et 5

    public Examen(int id, String titre, Matiere matiere, int progression, boolean terminee, int joursRestants, int coefficient) {
        super(id, titre, matiere, progression, terminee);
        setJoursRestants(joursRestants);
        setCoefficient(coefficient);
    }

    public Examen(String titre, Matiere matiere, int progression, int joursRestants, int coefficient) {
        super(titre, matiere, progression);
        setJoursRestants(joursRestants);
        setCoefficient(coefficient);
    }

    public int getJoursRestants() {
        return joursRestants;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setJoursRestants(int joursRestants) {
        if (joursRestants >= 0) {
            this.joursRestants = joursRestants;
        } else {
            this.joursRestants = 0;
        }
    }

    public void setCoefficient(int coefficient) {
        if (coefficient >= 1 && coefficient <= 5) {
            this.coefficient = coefficient;
        } else {
            this.coefficient = 1;
        }
    }

    @Override
    public int calculerPriorite() {
        if (terminee) {
            return 0;
        }

        int priorite = 0;

        priorite += matiere.getDifficulte() * 20;
        priorite += 100 - progression;
        priorite += coefficient * 25;

        if (joursRestants <= 2) {
            priorite += 120;
        } else if (joursRestants <= 5) {
            priorite += 90;
        } else if (joursRestants <= 10) {
            priorite += 60;
        } else if (joursRestants <= 20) {
            priorite += 30;
        }

        return priorite;
    }

    public String calculerNiveauRisque() {
        int priorite = calculerPriorite();

        if (priorite >= 300) {
            return "Très élevé";
        } else if (priorite >= 220) {
            return "Élevé";
        } else if (priorite >= 150) {
            return "Moyen";
        } else {
            return "Faible";
        }
    }

    @Override
    public void afficherDetails() {
        System.out.println("[Examen]");
        System.out.println("ID : " + id);
        System.out.println("Titre : " + titre);
        System.out.println("Matière : " + matiere.getNom());
        System.out.println("Difficulté : " + matiere.getDifficulte() + "/5");
        System.out.println("Progression : " + progression + "%");
        System.out.println("Jours restants : " + joursRestants);
        System.out.println("Coefficient : " + coefficient);
        System.out.println("Terminée : " + terminee);
        System.out.println("Priorité : " + calculerPriorite());
        System.out.println("Risque : " + calculerNiveauRisque());
    }
}