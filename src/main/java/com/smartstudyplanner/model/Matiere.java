/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.model;

/**
 *
 * @author Aissaoui
 */

public class Matiere {
    private int id;
    private String nom;
    private int difficulte; // entre 1 et 5
    private String objectif;

    public Matiere(int id, String nom, int difficulte, String objectif) {
        this.id = id;
        setNom(nom);
        setDifficulte(difficulte);
        setObjectif(objectif);
    }

    public Matiere(String nom, int difficulte, String objectif) {
        setNom(nom);
        setDifficulte(difficulte);
        setObjectif(objectif);
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        if (nom != null && !nom.trim().isEmpty()) {
            this.nom = nom;
        } else {
            this.nom = "Matière inconnue";
        }
    }

    public void setDifficulte(int difficulte) {
        if (difficulte >= 1 && difficulte <= 5) {
            this.difficulte = difficulte;
        } else {
            this.difficulte = 3;
        }
    }

    public void setObjectif(String objectif) {
        if (objectif != null && !objectif.trim().isEmpty()) {
            this.objectif = objectif;
        } else {
            this.objectif = "Aucun objectif défini";
        }
    }

    public void afficherInfos() {
        System.out.println("ID : " + id);
        System.out.println("Matière : " + nom);
        System.out.println("Difficulté : " + difficulte + "/5");
        System.out.println("Objectif : " + objectif);
    }
}
