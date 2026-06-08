/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.model;

/**
 *
 * @author Aissaoui
 */

public abstract class Tache implements Priorisable {
    protected int id;
    protected String titre;
    protected Matiere matiere;
    protected int progression; // entre 0 et 100
    protected boolean terminee;

    public Tache(int id, String titre, Matiere matiere, int progression, boolean terminee) {
        this.id = id;
        setTitre(titre);
        this.matiere = matiere;
        setProgression(progression);
        this.terminee = terminee;
    }

    public Tache(String titre, Matiere matiere, int progression) {
        setTitre(titre);
        this.matiere = matiere;
        setProgression(progression);
        this.terminee = false;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public int getProgression() {
        return progression;
    }

    public boolean isTerminee() {
        return terminee;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        if (titre != null && !titre.trim().isEmpty()) {
            this.titre = titre;
        } else {
            this.titre = "Tâche sans titre";
        }
    }

    public void setMatiere(Matiere matiere) {
        if (matiere != null) {
            this.matiere = matiere;
        } else {
            this.matiere = new Matiere("Matière inconnue", 3, "Aucun objectif");
        }
    }

    public void setProgression(int progression) {
        if (progression >= 0 && progression <= 100) {
            this.progression = progression;
        } else {
            this.progression = 0;
        }
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }

    public void marquerTerminee() {
        this.terminee = true;
        this.progression = 100;
    }

    public void afficherResume() {
        System.out.println(titre + " | " + matiere.getNom() + " | Progression : " + progression + "%");
    }

    public abstract void afficherDetails();
}