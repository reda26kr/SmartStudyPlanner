/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.model;

/**
 *
 * @author Aissaoui
 */

public class SessionTravail {
    private int id;
    private Tache tache;
    private int dureeMinutes;
    private String commentaire;
    private String dateSession;

    public SessionTravail(int id, Tache tache, int dureeMinutes, String commentaire, String dateSession) {
        this.id = id;
        this.tache = tache;
        setDureeMinutes(dureeMinutes);
        setCommentaire(commentaire);
        this.dateSession = dateSession;
    }

    public SessionTravail(Tache tache, int dureeMinutes, String commentaire, String dateSession) {
        this.tache = tache;
        setDureeMinutes(dureeMinutes);
        setCommentaire(commentaire);
        this.dateSession = dateSession;
    }

    public int getId() {
        return id;
    }

    public Tache getTache() {
        return tache;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getDateSession() {
        return dateSession;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public void setDureeMinutes(int dureeMinutes) {
        if (dureeMinutes > 0) {
            this.dureeMinutes = dureeMinutes;
        } else {
            this.dureeMinutes = 1;
        }
    }

    public void setCommentaire(String commentaire) {
        if (commentaire != null && !commentaire.trim().isEmpty()) {
            this.commentaire = commentaire;
        } else {
            this.commentaire = "Aucun commentaire";
        }
    }

    public void afficherInfos() {
        System.out.println("Session ID : " + id);
        System.out.println("Tâche : " + tache.getTitre());
        System.out.println("Matière : " + tache.getMatiere().getNom());
        System.out.println("Durée : " + dureeMinutes + " minutes");
        System.out.println("Commentaire : " + commentaire);
        System.out.println("Date : " + dateSession);
    }
}