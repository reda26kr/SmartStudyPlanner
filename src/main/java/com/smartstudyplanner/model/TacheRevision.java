/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.model;

/**
 *
 * @author Aissaoui
 */
public class TacheRevision extends Tache {
    private int dureeEstimee; // en minutes

    public TacheRevision(int id, String titre, Matiere matiere, int progression, boolean terminee, int dureeEstimee) {
        super(id, titre, matiere, progression, terminee);
        setDureeEstimee(dureeEstimee);
    }

    public TacheRevision(String titre, Matiere matiere, int progression, int dureeEstimee) {
        super(titre, matiere, progression);
        setDureeEstimee(dureeEstimee);
    }

    public int getDureeEstimee() {
        return dureeEstimee;
    }

    public void setDureeEstimee(int dureeEstimee) {
        if (dureeEstimee > 0) {
            this.dureeEstimee = dureeEstimee;
        } else {
            this.dureeEstimee = 30;
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

        if (dureeEstimee <= 30) {
            priorite += 20;
        } else if (dureeEstimee <= 60) {
            priorite += 10;
        }

        return priorite;
    }

    @Override
    public void afficherDetails() {
        System.out.println("[Tâche de révision]");
        System.out.println("ID : " + id);
        System.out.println("Titre : " + titre);
        System.out.println("Matière : " + matiere.getNom());
        System.out.println("Difficulté : " + matiere.getDifficulte() + "/5");
        System.out.println("Progression : " + progression + "%");
        System.out.println("Durée estimée : " + dureeEstimee + " minutes");
        System.out.println("Terminée : " + terminee);
        System.out.println("Priorité : " + calculerPriorite());
    }
}