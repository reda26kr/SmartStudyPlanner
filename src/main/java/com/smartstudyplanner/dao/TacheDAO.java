/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.dao;

/**
 *
 * @author Aissaoui
 */

import com.smartstudyplanner.model.Examen;
import com.smartstudyplanner.model.Matiere;
import com.smartstudyplanner.model.Tache;
import com.smartstudyplanner.model.TacheRevision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TacheDAO {

    public void ajouterTache(Tache tache) {
        String sql = """
                INSERT INTO taches(
                    titre, matiere_id, type, progression, terminee,
                    duree_estimee, jours_restants, coefficient
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tache.getTitre());
            statement.setInt(2, tache.getMatiere().getId());

            statement.setInt(4, tache.getProgression());
            statement.setInt(5, tache.isTerminee() ? 1 : 0);

            if (tache instanceof TacheRevision) {
                TacheRevision revision = (TacheRevision) tache;

                statement.setString(3, "REVISION");
                statement.setInt(6, revision.getDureeEstimee());
                statement.setObject(7, null);
                statement.setObject(8, null);

            } else if (tache instanceof Examen) {
                Examen examen = (Examen) tache;

                statement.setString(3, "EXAMEN");
                statement.setObject(6, null);
                statement.setInt(7, examen.getJoursRestants());
                statement.setInt(8, examen.getCoefficient());

            } else {
                System.out.println("Type de tâche inconnu.");
                return;
            }

            statement.executeUpdate();
            System.out.println("Tâche sauvegardée dans la base.");

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la tâche : " + e.getMessage());
        }
    }

    public ArrayList<Tache> recupererToutesLesTaches() {
        ArrayList<Tache> taches = new ArrayList<>();

        String sql = """
                SELECT 
                    t.id AS tache_id,
                    t.titre,
                    t.type,
                    t.progression,
                    t.terminee,
                    t.duree_estimee,
                    t.jours_restants,
                    t.coefficient,
                    m.id AS matiere_id,
                    m.nom AS matiere_nom,
                    m.difficulte,
                    m.objectif
                FROM taches t
                JOIN matieres m ON t.matiere_id = m.id
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int matiereId = resultSet.getInt("matiere_id");
                String matiereNom = resultSet.getString("matiere_nom");
                int difficulte = resultSet.getInt("difficulte");
                String objectif = resultSet.getString("objectif");

                Matiere matiere = new Matiere(matiereId, matiereNom, difficulte, objectif);

                int id = resultSet.getInt("tache_id");
                String titre = resultSet.getString("titre");
                String type = resultSet.getString("type");
                int progression = resultSet.getInt("progression");
                boolean terminee = resultSet.getInt("terminee") == 1;

                if (type.equals("REVISION")) {
                    int dureeEstimee = resultSet.getInt("duree_estimee");

                    TacheRevision revision = new TacheRevision(
                            id,
                            titre,
                            matiere,
                            progression,
                            terminee,
                            dureeEstimee
                    );

                    taches.add(revision);

                } else if (type.equals("EXAMEN")) {
                    int joursRestants = resultSet.getInt("jours_restants");
                    int coefficient = resultSet.getInt("coefficient");

                    Examen examen = new Examen(
                            id,
                            titre,
                            matiere,
                            progression,
                            terminee,
                            joursRestants,
                            coefficient
                    );

                    taches.add(examen);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tâches : " + e.getMessage());
        }

        return taches;
    }

    public void marquerTacheTerminee(int id) {
        String sql = "UPDATE taches SET terminee = 1, progression = 100 WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int lignesModifiees = statement.executeUpdate();

            if (lignesModifiees > 0) {
                System.out.println("Tâche mise à jour dans la base.");
            } else {
                System.out.println("Aucune tâche trouvée avec cet ID.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la tâche : " + e.getMessage());
        }
    }

    public void supprimerTache(int id) {
        String sql = "DELETE FROM taches WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int lignesSupprimees = statement.executeUpdate();

            if (lignesSupprimees > 0) {
                System.out.println("Tâche supprimée.");
            } else {
                System.out.println("Aucune tâche trouvée avec cet ID.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la tâche : " + e.getMessage());
        }
    }
}