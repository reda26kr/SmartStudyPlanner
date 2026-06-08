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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SessionService {
    private SessionDAO sessionDAO;

    public SessionService() {
        this.sessionDAO = new SessionDAO();
    }

    public void ajouterSession(Tache tache, int dureeMinutes, String commentaire) {
        if (tache == null) {
            System.out.println("Erreur : tâche invalide.");
            return;
        }

        if (dureeMinutes <= 0) {
            System.out.println("Erreur : la durée doit être positive.");
            return;
        }

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        SessionTravail session = new SessionTravail(tache, dureeMinutes, commentaire, date);

        sessionDAO.ajouterSession(session);
    }

    public void afficherSessions(ArrayList<Tache> taches) {
        ArrayList<SessionTravail> sessions = sessionDAO.recupererToutesLesSessions(taches);

        if (sessions.isEmpty()) {
            System.out.println("Aucune session enregistrée.");
            return;
        }

        System.out.println("===== HISTORIQUE DES SESSIONS =====");

        for (int i = 0; i < sessions.size(); i++) {
            sessions.get(i).afficherInfos();
            System.out.println();
        }
    }

    public int calculerTempsTotalTravail() {
        return sessionDAO.calculerTempsTotalTravail();
    }

    public void afficherTempsTotal() {
        int totalMinutes = calculerTempsTotalTravail();

        int heures = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        System.out.println("Temps total travaillé : " + heures + "h " + minutes + "min");
    }
}