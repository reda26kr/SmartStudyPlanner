/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.dao;

/**
 *
 * @author Aissaoui
 */

import com.smartstudyplanner.model.SessionTravail;
import com.smartstudyplanner.model.Tache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SessionDAO {

    public void ajouterSession(SessionTravail session) {
        String sql = """
                INSERT INTO sessions(tache_id, duree_minutes, commentaire, date_session)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, session.getTache().getId());
            statement.setInt(2, session.getDureeMinutes());
            statement.setString(3, session.getCommentaire());
            statement.setString(4, session.getDateSession());

            statement.executeUpdate();

            System.out.println("Session de travail sauvegardée.");

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la session : " + e.getMessage());
        }
    }

    public ArrayList<SessionTravail> recupererToutesLesSessions(ArrayList<Tache> taches) {
        ArrayList<SessionTravail> sessions = new ArrayList<>();

        String sql = """
                SELECT id, tache_id, duree_minutes, commentaire, date_session
                FROM sessions
                ORDER BY date_session DESC
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int tacheId = resultSet.getInt("tache_id");
                int dureeMinutes = resultSet.getInt("duree_minutes");
                String commentaire = resultSet.getString("commentaire");
                String dateSession = resultSet.getString("date_session");

                Tache tache = trouverTacheParId(taches, tacheId);

                if (tache != null) {
                    SessionTravail session = new SessionTravail(
                            id,
                            tache,
                            dureeMinutes,
                            commentaire,
                            dateSession
                    );

                    sessions.add(session);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des sessions : " + e.getMessage());
        }

        return sessions;
    }

    public int calculerTempsTotalTravail() {
        String sql = "SELECT SUM(duree_minutes) AS total FROM sessions";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul du temps total : " + e.getMessage());
        }

        return 0;
    }

    private Tache trouverTacheParId(ArrayList<Tache> taches, int id) {
        for (int i = 0; i < taches.size(); i++) {
            if (taches.get(i).getId() == id) {
                return taches.get(i);
            }
        }

        return null;
    }
    
    public void afficherTempsParMatiere() {
    String sql = """
            SELECT m.nom AS matiere_nom, SUM(s.duree_minutes) AS total_minutes
            FROM sessions s
            JOIN taches t ON s.tache_id = t.id
            JOIN matieres m ON t.matiere_id = m.id
            GROUP BY m.nom
            ORDER BY total_minutes DESC
            """;

    try (Connection connection = DatabaseManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        System.out.println("===== TEMPS PAR MATIÈRE =====");

        boolean hasData = false;

        while (resultSet.next()) {
            hasData = true;

            String matiereNom = resultSet.getString("matiere_nom");
            int totalMinutes = resultSet.getInt("total_minutes");

            int heures = totalMinutes / 60;
            int minutes = totalMinutes % 60;

            System.out.println(matiereNom + " : " + heures + "h " + minutes + "min");
        }

        if (!hasData) {
            System.out.println("Aucune session enregistrée.");
        }

    } catch (SQLException e) {
        System.out.println("Erreur lors du calcul du temps par matière : " + e.getMessage());
    }
}
}